package com.example.spacegame.entities;

import android.content.Context;

public class Bomb extends Projectile{

    float damage;
    int x;
    int y;

    public Bomb(Context context, int x, int y, float damage, ProjectileType type) {
        super(context, x, y, damage, type);

        this.x = x;
        this.y = y;
    }

    public void update(long fps)
    {
        super.update(fps);
    }
}
