package com.faultaddr.coffeebeggerserver.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.faultaddr.coffeebeggerserver.entity.MGameEntity;
import com.faultaddr.coffeebeggerserver.entity.MUserEntity;
import com.faultaddr.coffeebeggerserver.repository.GameRepository;
import com.faultaddr.coffeebeggerserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@Transactional
@EnableAutoConfiguration
public class GameServiceImpl implements GameService {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public MGameEntity getGameById(String gameId) {
        MGameEntity entity = gameRepository.findMGameEntityByGameId(gameId);
        return entity;
    }

    @Override
    public String getGameResultById(String gameId) {
        MGameEntity entity = gameRepository.findMGameEntityByGameId(gameId);
        return entity.getResult();
    }

    @Override
    public boolean createGame(String gameId, MUserEntity userEntity) {
        MGameEntity entity = new MGameEntity();
        entity.setGameId(gameId);
        entity.setResult("");
        List<MUserEntity> userList = new ArrayList<>();
        userList.add(userEntity);
        entity.setParticipant(JSONArray.toJSONString(userList));
        gameRepository.save(entity);
        MUserEntity user = userRepository.findMUserEntityByAvatar(userEntity.getAvatar());
        if (user == null) {
            userRepository.save(userEntity);
        }
        return true;
    }

    @Override
    public boolean joinGame(String gameId, MUserEntity userEntity) {
        MGameEntity gameEntity = gameRepository.findMGameEntityByGameId(gameId);
        if (gameEntity != null) {
            List<MUserEntity> array = JSONArray.parseArray(gameEntity.getParticipant(), MUserEntity.class);
            if (userEntity != null) {
                array.add(userEntity);
            }
            String users = JSON.toJSONString(array);
            gameRepository.updateMGameEntityParticipantByGameId(users, gameId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateGameResult(String gameId, String result) {
        gameRepository.updateMGameEntityResultByGameId(result, gameId);
        return true;
    }

    @Override
    public List<MUserEntity> getParticipantByGameId(String gameId) {
        Logger.getGlobal().info(String.format("select * from game where gameId='%s'", gameId));
        MGameEntity gameEntity = gameRepository.findMGameEntityByGameId(gameId);
        if (gameEntity != null) {
            String participant = gameEntity.getParticipant();
            return JSONArray.parseArray(participant, MUserEntity.class);
        } else {
            return null;
        }
    }


}
