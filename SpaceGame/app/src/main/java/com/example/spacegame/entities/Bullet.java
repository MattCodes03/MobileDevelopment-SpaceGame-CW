package com.example.spacegame.entities;

import static com.example.spacegame.SpaceGameView.getPlayer;

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

    float bulletSpeed;

    public Bullet(Context context, float x, float y, float damage, ProjectileType type) {
        super(context, x, y, damage, type);

        this.x = player.getX();
        this.y = player.getY();

        this.bulletSpeed = 400f;
        this.hasBeenFired = false;
        shooting_x = player.getX();
        shooting_y = player.getY();
    }

    public void update(long fps)
    {
        this.x = player.getX();
        this.y = player.getY();

        if(getStatus())
        {
            fireBullet(fps);
        }

        super.update(fps);
    }

    private void fireBullet(long fps) {
        Log.d("Bull X val",""+this.x);
        Log.d("Ship X val",""+getPlayer().getX());
        switch (getPlayer().spaceShipMoving)
        {
            case LEFT:
                this.shooting_x = this.shooting_x - this.bulletSpeed / fps;
                Log.d("Left", "Pew");
                break;
            case RIGHT:
                this.shooting_x = this.shooting_x + this.bulletSpeed / fps;
                Log.d("Right", "Pew");
                break;
            case UP:
                this.shooting_y = this.shooting_y - this.bulletSpeed / fps;
                Log.d("Up", "Pew");
                break;
            case DOWN:
                this.shooting_y = this.shooting_y + this.bulletSpeed / fps;
                Log.d("Down", "Pew");
                break;
            default:
                break;
        }
        Log.d("X now ",""+this.x);
    }

    public Bitmap getBitmap() {
        Bitmap bulletBitmap = BitmapFactory.decodeResource(super.context.getResources(), R.drawable.bullet);
        bulletBitmap = Bitmap.createScaledBitmap(bulletBitmap, (int) (length), (int) (height),false);

        return bulletBitmap;
    }

    public float getShootingX(){return shooting_x;}
    public float getShootingY(){return shooting_y;}
}
