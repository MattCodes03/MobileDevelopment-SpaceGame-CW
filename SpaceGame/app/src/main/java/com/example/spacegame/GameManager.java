package com.example.spacegame;

import kotlin.NotImplementedError;

public class GameManager {
    enum GameState
    {
        EnemySwarmPhase,
        BossBattlePhase,
        GameEnd
    }

    public GameState state = GameState.EnemySwarmPhase;

    public void updateGameState(GameState state)
    {
        switch (state)
        {
            case EnemySwarmPhase:
                break;
            case BossBattlePhase:
                startBossBattlePhase();
                break;
            case GameEnd:
                startEndGamePhase();
                break;
            default:
                break;

        }
    }
    private void startBossBattlePhase()
    {
        throw new NotImplementedError();
    }

    private void startEndGamePhase()
    {
        throw new NotImplementedError();
    }
}
