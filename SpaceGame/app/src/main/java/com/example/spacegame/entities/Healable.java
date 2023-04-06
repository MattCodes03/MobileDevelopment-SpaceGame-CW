package com.example.spacegame.entities;

import android.content.Context;
import android.graphics.RectF;

import com.example.spacegame.SpaceGameView;

public class Healable extends Projectile{

    float damage;
    float x;
    float y;

    public Healable(Context context, float x, float y, float damage, ProjectileType type) {
        super(context, x, y, damage, type);

        this.x = x;
        this.y = y;
    }

    public void update(long fps)
    {
        super.update(fps);
    }
}
