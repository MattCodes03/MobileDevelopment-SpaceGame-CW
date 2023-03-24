package com.example.spacegame.entities;

import android.content.Context;
import android.graphics.RectF;

import com.example.spacegame.SpaceGameView;

public class Healable{

    RectF rect;

    int height;
    int length;

    int x;
    int y;

    SpaceShip player;

    private boolean isActive;

    public Healable(Context context, int x, int y) {

        this.rect = new RectF();

        this.x = x;
        this.y = y;

        this.height = 50;
        this.length = 50;

        player = SpaceGameView.getPlayer();
    }

    public void update(long fps)
    {
        checkForCollisionWithPlayer();

        this.rect.left = x;
        this.rect.right = x + length;
        this.rect.top = y;
        this.rect.bottom = y + height;
    }

    public void checkForCollisionWithPlayer()
    {
        /* Write function so that if the projectile this.rect collides with player Rect substitute this.damage from player health */

        if(this.rect.intersect(player.getRect()) && this.isActive)
        {
            this.isActive = false;
            player.setHealth(110);
        }
    }

    public boolean getStatus()
    {
        return this.isActive;
    }

    public void setActive() {
        this.isActive = true;
    }

    public RectF getRect()
    {
        return this.rect;
    }
}
