package com.example.spacegame.entities;

import android.content.Context;

public class Bomb extends Projectile{

    float damage;
    float x;
    float y;

    public Bomb(Context context, float x, float y, float damage, ProjectileType type) {
        super(context, x, y, damage, type);

        this.x = x;
        this.y = y;
    }

    public void update(long fps)
    {
        super.update(fps);
    }
}
