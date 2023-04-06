package com.example.spacegame.entities;

import static com.example.spacegame.SpaceGameView.getPlayer;
import static com.example.spacegame.SpaceGameView.screenX;
import static com.example.spacegame.SpaceGameView.screenY;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;

import com.example.spacegame.R;

public class Bullet extends Projectile{

    float damage;
    float x;
    float y;

    float shooting_x;
    float shooting_y;

    boolean hasBeenFired;

    SpaceShip.movingState bulletHeading;

    float bulletSpeed;

    public Bullet(Context context, float x, float y, float damage, ProjectileType type) {
        super(context, x, y, damage, type);

        this.x = player.getX();
        this.y = player.getY();

        this.bulletSpeed = 2000f;

        shooting_x = player.getX();
        shooting_y = player.getY();
    }

    public void update(long fps)
    {
        this.x = player.getX();
        this.y = player.getY();

        this.checkForCollisionWithScreenEdges();

       if(getStatus())
       {
           switch(bulletHeading){
               case LEFT:
                   this.shooting_x = this.shooting_x - this.bulletSpeed / fps;
                   break;
               case RIGHT:
                   this.shooting_x = this.shooting_x + this.bulletSpeed / fps;
                   break;
               case UP:
                   this.shooting_y = this.shooting_y - this.bulletSpeed / fps;
                   break;
               case DOWN:
                   this.shooting_y = this.shooting_y + this.bulletSpeed / fps;
                   break;
               default:
                   break;
           }
       }
       super.update(fps);
    }

    @Override
    public void checkForCollisionWithScreenEdges()
    {
            if (this.shooting_x > screenX - this.getLength()) {
                this.setInactive();
            }
            if (this.shooting_x < 0 - this.getLength()) {
                this.setInactive();
            }

            if (this.shooting_y > screenY - this.getHeight()) {
                this.setInactive();
            }
            if (this.shooting_y < 0 + this.getHeight()) {
                this.setInactive();
            }
    }

    public void fireBullet(long fps, SpaceShip.movingState direction) {
        if(!this.getStatus()) {
            this.setActive();
            this.bulletHeading = direction;

            shooting_x = player.getX();
            shooting_y = player.getY();
        }
    }

    public Bitmap getBitmap() {
        Bitmap bulletBitmap = BitmapFactory.decodeResource(super.context.getResources(), R.drawable.bullet);
        bulletBitmap = Bitmap.createScaledBitmap(bulletBitmap, (int) (length), (int) (height),false);

        return bulletBitmap;
    }

    public float getShootingX(){return shooting_x;}
    public float getShootingY(){return shooting_y;}
}
