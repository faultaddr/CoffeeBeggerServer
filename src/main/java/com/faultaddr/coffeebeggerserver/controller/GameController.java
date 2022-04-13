package com.faultaddr.coffeebeggerserver.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.faultaddr.coffeebeggerserver.bean.APIResult;
import com.faultaddr.coffeebeggerserver.bean.InputMessage;
import com.faultaddr.coffeebeggerserver.bean.Message;
import com.faultaddr.coffeebeggerserver.bean.OutputMessage;
import com.faultaddr.coffeebeggerserver.entity.MUserEntity;
import com.faultaddr.coffeebeggerserver.form.JoinGameForm;
import com.faultaddr.coffeebeggerserver.service.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
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
    @Autowired
    GameServiceImpl gameService;

    @RequestMapping(value = "/POST/game/createGame")
    @ResponseBody
    public APIResult createGame(@RequestBody MUserEntity userEntity) {
        String generatedGameId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        Logger.getGlobal().info(JSON.toJSONString(userEntity));
        gameService.createGame(generatedGameId, userEntity);
        return APIResult.createSuccessMessage(generatedGameId);
    }

    @RequestMapping(value = "/POST/game/joinGame")
    @ResponseBody
    public APIResult joinGame(@RequestBody JoinGameForm form) {
        if (form.checkForm()) {
            boolean result = gameService.joinGame(form.getGameId(), form.getUserBean());
            return APIResult.createSuccessMessage("" + result);
        } else {
            return APIResult.createErrorMessage("form is invalid");
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
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        List<MUserEntity> userList = gameService.getParticipantByGameId(gameId);
        if (userList != null) {
            int userLength = userList.size();
            int selectedIndex = new Random().nextInt(userLength);
            String selectedUserId = userList.get(selectedIndex).getAvatar();
            gameService.updateGameResult(gameId, selectedUserId);
            return APIResult.createSuccessMessage(selectedUserId);
        } else {
            return APIResult.createErrorMessage("without participant");
        }
    }

    @RequestMapping(value = "/game.syncResult")
    @SendTo("/game/public")
    public OutputMessage send(@Payload InputMessage message) {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        String gameId = message.getGameId();
        String result = gameService.getGameResultById(gameId);
        return new OutputMessage(message.getGameId(), result, Message.MessageType.RESULT, time);
    }

    @RequestMapping(value = "/game.addUser")
    @SendTo("/game/public")
    public OutputMessage addUser(@Payload InputMessage message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("gameId", message.getGameId());
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getGameId(), "", Message.MessageType.JOIN, time);
    }

}
