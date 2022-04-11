package com.faultaddr.coffeebeggerserver.service;

import com.faultaddr.coffeebeggerserver.entity.MGameEntity;
import com.faultaddr.coffeebeggerserver.entity.MUserEntity;

import java.util.List;

public interface GameService {
    MGameEntity getGameById(String id);

    String getGameResultById(String id);

    boolean createGame(String gameId, MUserEntity bean);

    boolean joinGame(String gameId, MUserEntity bean);

    boolean updateGameResult(String gameId, String result);

    List<MUserEntity> getParticipantByGameId(String id);
}
