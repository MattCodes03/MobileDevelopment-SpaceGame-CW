package com.example.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Projectile {
    RectF rect;

    float height;
    float length;

    int x;
    int y;
    float damage;

    private boolean isActive;

    SpaceShip player;

    ProjectileType type;

    enum ProjectileType
    {
        Bomb,
        Bullet
    }

    Projectile(Context context, int x, int y, float damage, ProjectileType type)
    {

        this.rect = new RectF();

        this.x = x;
        this.y = y;

        this.height = 50;
        this.length = 50;

        this.damage = damage;

        this.type = type;

        player = SpaceGameView.getPlayer();
    }

    public void update(long fps)
    {
        if(this.type == ProjectileType.Bomb)
        {
            checkForCollisionWithPlayer();
        }else if(this.type == ProjectileType.Bullet)
        {
            checkForCollisionWithEnemy();
        }

        this.rect.left = x;
        this.rect.right = x + length;
        this.rect.top = y;
        this.rect.bottom = y + height;
    }

    private void checkForCollisionWithEnemy()
    {
    }

    public void checkForCollisionWithPlayer()
    {
        /* Write function so that if the projectile this.rect collides with player Rect substitute this.damage from player health */

        if(this.rect.intersect(player.getRect()) && this.isActive)
        {
                this.isActive = false;
                player.setHealth(-this.damage);
        }


    }

    public boolean getStatus()
    {
        return this.isActive;
    }

    public void setActive() {
        this.isActive = true;
    }
}
