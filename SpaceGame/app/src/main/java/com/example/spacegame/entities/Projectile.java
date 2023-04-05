package com.example.spacegame.entities;

import android.content.Context;
import android.graphics.RectF;

import com.example.spacegame.SpaceGameView;

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

    public enum ProjectileType
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

        this.isActive = false;

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
                player.takeDamage(this.damage);

                if(this.type == ProjectileType.Bomb)
                {
                    SpaceGameView.updateBombsDetonatedCount();
                }
        }
    }

    public boolean getStatus()
    {
        return this.isActive;
    }

    public void setActive() {
        this.isActive = true;
    }

    public void setInactive(){ this.isActive = false;}

    public RectF getRect()
    {
        return this.rect;
    }
}
