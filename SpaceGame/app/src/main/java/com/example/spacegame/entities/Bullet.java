package com.example.spacegame.entities;

import static com.example.spacegame.SpaceGameView.getPlayer;

import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;

public class Bullet extends Projectile{

    float damage;
    float x;
    float y;

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
            fireBullet(fps);
        }

        super.update(fps);
    }

    private void fireBullet(long fps) {
        // TODO: Implement functionality for bullet to move along screen if fired
        this.x = getPlayer().getX();
        this.y= getPlayer().getY();
        Log.d("Bull X val",""+this.x);
        Log.d("Ship X val",""+getPlayer().getX());
        switch (getPlayer().spaceShipMoving)
        {
            case LEFT:
                this.x = this.x - this.bulletSpeed / fps;
                Log.d("Left", "Pew");
                break;
            case RIGHT:
                this.x = this.x + this.bulletSpeed / fps;
                Log.d("Right", "Pew");
                break;
            case UP:
                this.y = this.y - this.bulletSpeed / fps;
                Log.d("Up", "Pew");
                break;
            case DOWN:
                this.y = this.y + this.bulletSpeed / fps;
                Log.d("Down", "Pew");
                break;
            default:
                break;
        }
        Log.d("X now ",""+this.x);
    }
}
