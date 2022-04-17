package com.faultaddr.coffeebeggerserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.faultaddr.coffeebeggerserver.Constants;
import com.faultaddr.coffeebeggerserver.entity.MGameEntity;
import com.faultaddr.coffeebeggerserver.entity.MUserEntity;
import com.faultaddr.coffeebeggerserver.form.JoinGameForm;
import com.faultaddr.coffeebeggerserver.service.GameServiceImpl;
import com.faultaddr.coffeebeggerserver.service.SenderService;
import com.faultaddr.coffeebeggerserver.utils.APIResult;
import com.faultaddr.coffeebeggerserver.utils.Message;
import com.faultaddr.coffeebeggerserver.utils.OutputMessage;
import com.faultaddr.coffeebeggerserver.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
@EnableAutoConfiguration
public class GameController {
  @Autowired GameServiceImpl gameService;
  @Autowired SenderService senderService;

  @RequestMapping(value = "/POST/game/createGame")
  @ResponseBody
  public APIResult createGame(@RequestBody MUserEntity userEntity) {
    String generatedGameId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
    int generatedInvitationCode = new Random().nextInt(999999);
    MGameEntity gameEntity =
        gameService.createGame(generatedGameId, generatedInvitationCode, userEntity);
    return APIResult.createSuccessMessage(gameEntity);
  }

  @RequestMapping(value = "/POST/game/joinGame")
  @ResponseBody
  public APIResult joinGame(@RequestBody JoinGameForm form) {
    String time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date());
    if (!Utils.checkForm(form)) {
      return APIResult.createErrorMessage("form is invalid");
    }
    MUserEntity userEntity =
        new MUserEntity(
            form.getAvatar(),
            form.getCity(),
            form.getGender(),
            form.getProvince(),
            form.getCountryCode(),
            form.getNickName());
    // get gameId from invitationCode
    if (form.getId().length() == 6) {
      MGameEntity gameEntity = gameService.getGameByInvitationId(form.getId());
      if (gameEntity == null) {
        return APIResult.createErrorMessage("game is not exist");
      }
      List<MUserEntity> participant =
          JSONArray.parseArray(gameEntity.getParticipant(), MUserEntity.class);
      boolean matchResult =
          participant.stream()
              .anyMatch(mUserEntity -> mUserEntity.getAvatar().equals(userEntity.getAvatar()));
      if (matchResult) {

        return APIResult.createSuccessMessage(
            JSON.toJSONString(
                new OutputMessage(gameEntity.getGameId(), "true", Message.MessageType.JOIN, time)));
      } else {
        participant.add(userEntity);
        String participantStr = JSON.toJSONString(participant);
        boolean result = gameService.updateGameParticipant(gameEntity.getGameId(), participantStr);
        APIResult apiResult = APIResult.createSuccessMessage(participant);
        apiResult.setCode(Constants.PARTICIPANT);
        senderService.send(userEntity.getAvatar(), JSON.toJSONString(apiResult));
        participant.stream()
            .forEach(
                mUserEntity -> {
                  Logger.getGlobal().info("SocketSender joinGame: ->" + mUserEntity.getAvatar());
                  senderService.send(mUserEntity.getAvatar(), JSON.toJSONString(apiResult));
                });
        return APIResult.createSuccessMessage(
            JSON.toJSONString(
                new OutputMessage(gameEntity.getGameId(), "true", Message.MessageType.JOIN, time)));
      }
    } else {
      boolean result = gameService.joinGame(form.getId(), userEntity);
      return APIResult.createSuccessMessage("" + result);
    }
  }

  @RequestMapping(value = "/GET/game/getParticipant")
  @ResponseBody
  public APIResult getParticipant(@RequestParam("gameId") String gameId) {
    List<MUserEntity> userList = gameService.getParticipantByGameId(gameId);
    JSONArray array = JSONArray.parseArray(JSON.toJSONString(userList));
    return APIResult.createSuccessMessage(array);
  }

  @RequestMapping(value = "/GET/game/startGame")
  @ResponseBody
  public APIResult startGame(@RequestParam("gameId") String gameId) {

    List<MUserEntity> userList = gameService.getParticipantByGameId(gameId);
    if (userList != null) {
      int userLength = userList.size();
      int selectedIndex = new Random().nextInt(userLength);
      String selectedUserId = userList.get(selectedIndex).getAvatar();
      gameService.updateGameResult(gameId, selectedUserId);
      APIResult result = APIResult.createSuccessMessage(selectedUserId);
      result.setCode(Constants.RESULT);
      userList.stream()
          .forEach(
              mUserEntity -> {
                Logger.getGlobal().info("SocketSender startGame: ->" + mUserEntity.getAvatar());
                senderService.send(mUserEntity.getAvatar(), JSON.toJSONString(result));
              });

      return result;
    } else {
      return APIResult.createErrorMessage("without participant");
    }
  }
}
