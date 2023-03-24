package com.example.spacegame.entities;

import android.content.Context;
import android.os.Debug;
import android.util.Log;

public class Bullet extends Projectile{

    float damage;
    int x;
    int y;

    boolean hasBeenFired;

    float bulletSpeed;

    public Bullet(Context context, int x, int y, float damage, ProjectileType type) {
        super(context, x, y, damage, type);

        this.x = x;
        this.y = y;

        this.bulletSpeed = 400f;
        this.hasBeenFired = false;
    }

    public void update(long fps)
    {
        if(getStatus())
        {
            fireBullet();
        }

        super.update(fps);
    }

    private void fireBullet() {
        // TODO: Implement functionality for bullet to move along screen if fired

    }
}
