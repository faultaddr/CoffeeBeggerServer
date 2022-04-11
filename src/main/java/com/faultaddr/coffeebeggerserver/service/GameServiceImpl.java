package com.faultaddr.coffeebeggerserver.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.faultaddr.coffeebeggerserver.dao.DaoFactory;
import com.faultaddr.coffeebeggerserver.entity.MGameEntity;
import com.faultaddr.coffeebeggerserver.entity.MUserEntity;
import com.faultaddr.coffeebeggerserver.utils.DbHelper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableAutoConfiguration
public class GameServiceImpl implements GameService {
    private static volatile GameServiceImpl gameService = null;

    public static GameServiceImpl getInstance() {
        if (gameService == null) {
            synchronized (GameServiceImpl.class) {
                if (gameService == null) {
                    gameService = new GameServiceImpl();
                }
            }
        }
        return gameService;
    }


    @Override
    public MGameEntity getGameById(String gameId) {
        DbHelper<MGameEntity> dbHelper = new DbHelper<>();
        List<MGameEntity> entityList = dbHelper.optionHelper(String.format("select * from game  where gameId=%s", gameId), new MGameEntity());
        if (entityList != null && !entityList.isEmpty()) {
            return entityList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public String getGameResultById(String gameId) {
        DbHelper<MGameEntity> dbHelper = new DbHelper<>();
        List<MGameEntity> entityList = dbHelper.optionHelper(String.format("select * from game  where gameId=%s", gameId), new MGameEntity());
        if (entityList != null && !entityList.isEmpty()) {
            return entityList.get(0).getResult();
        } else {
            return "";
        }
    }

    @Override
    public boolean createGame(String gameId, MUserEntity bean) {
        MGameEntity entity = new MGameEntity();
        entity.setGameId(gameId);
        entity.setResult("");
        List<MUserEntity> userList = new ArrayList<>();
        userList.add(bean);
        entity.setParticipant(JSONArray.toJSONString(userList));
        DaoFactory<MGameEntity> factory = new DaoFactory<>();
        factory.save(entity);
        return true;
    }

    @Override
    public boolean joinGame(String gameId, MUserEntity bean) {
        DbHelper<MGameEntity> dbHelper = new DbHelper<>();
        List<MGameEntity> entityList = dbHelper.optionHelper(String.format("select * from game  where gameId=%s", gameId), new MGameEntity());
        if (entityList != null && !entityList.isEmpty()) {
            String participant = entityList.get(0).getParticipant();
            List<MUserEntity> array = JSONArray.parseArray(participant, MUserEntity.class);
            if (bean != null) {
                array.add(bean);
            }
            String users = JSON.toJSONString(array);
            dbHelper.optionHelper(String.format("update game set participant=%s where gameId=%s", users, entityList.get(0).getGameId()), entityList.get(0));
            return true;
        }
        return false;
    }

    @Override
    public boolean updateGameResult(String gameId, String result) {
        DbHelper<MGameEntity> dbHelper = new DbHelper<>();
        dbHelper.optionHelper(String.format("update game set result=%s where gameId=%s", result, gameId), new MGameEntity());
        return true;
    }

    @Override
    public List<MUserEntity> getParticipantByGameId(String gameId) {
        DbHelper<MGameEntity> dbHelper = new DbHelper<>();
        List<MGameEntity> entityList = dbHelper.optionHelper(String.format("select * from game  where gameId=%s", gameId), new MGameEntity());
        if (entityList != null && !entityList.isEmpty()) {
            String participant = entityList.get(0).getParticipant();
            return JSONArray.parseArray(participant, MUserEntity.class);
        }
        return null;
    }


}
