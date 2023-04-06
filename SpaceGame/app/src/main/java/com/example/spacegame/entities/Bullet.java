package com.example.spacegame.entities;

import static com.example.spacegame.SpaceGameView.getPlayer;
import static com.example.spacegame.SpaceGameView.screenX;
import static com.example.spacegame.SpaceGameView.screenY;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
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

//    SpaceShip.movingState bulletHeading;

    float bulletSpeed;
    private double bulletHeading;

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

        this.checkForCollisionWithScreenEdges();

       if(getStatus())
       {
           double angleDirection = bulletHeading;
           double co=(angleDirection%90)/90;

           if (angleDirection>=0 && angleDirection<90){
//               shooting_x = (float) ((1-co)*bulletSpeed);
//               shooting_y = (float) (co*bulletSpeed);

               this.shooting_x = this.shooting_x + this.bulletSpeed / fps;

               this.rect.left+=shooting_x;
               this.rect.right+=shooting_x;
               this.rect.top+=shooting_y;
               this.rect.bottom+=shooting_y;
           }
           else if (angleDirection>=90 && angleDirection<180){
//               shooting_x= (float) ((1-co)*bulletSpeed);
//             shooting_y= (float) (co*bulletSpeed);

               this.shooting_x = this.shooting_x - this.bulletSpeed / fps;

               this.rect.left-=shooting_x;
               this.rect.right-=shooting_x;
               this.rect.top+=shooting_y;
               this.rect.bottom+=shooting_y;
           }
           else if (angleDirection>=180 && angleDirection<270){
//             shooting_x= (float) ((1-co)*bulletSpeed);
//             shooting_y= (float) (co*bulletSpeed);

               this.shooting_y = this.shooting_y - this.bulletSpeed / fps;

               this.rect.left-=shooting_x;
               this.rect.right-=shooting_x;
               this.rect.top-=shooting_y;
               this.rect.bottom-=shooting_y;
           }
           else { // angleDirection>=270 && angleDirection<360
//               shooting_x= (float) ((1-co)*bulletSpeed);
//               shooting_y= (float) (co*bulletSpeed);

               this.shooting_y = this.shooting_y + this.bulletSpeed / fps;

               this.rect.left+=shooting_x;
               this.rect.right+=shooting_x;
               this.rect.top-=shooting_y;
               this.rect.bottom-=shooting_y;
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

    public void fireBullet(long fps, double angleDirection) { //SpaceShip.movingState direction
        if(!this.getStatus()) {
            this.setActive();

            this.bulletHeading = angleDirection;

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
