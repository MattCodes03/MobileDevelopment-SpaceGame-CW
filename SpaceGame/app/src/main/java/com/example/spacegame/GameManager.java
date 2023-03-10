package com.example.spacegame;

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
                break;
            case GameEnd:
                break;
            default:
                break;

        }
    }
}
