package com.example.spacegame.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.spacegame.R;
import com.example.spacegame.SpaceGameView;

public class Enemy extends AngleMovingObject {
    boolean status = false;//true-visible and active on screen false-not

    float health;
    public Enemy(Context context, SpaceGameView spaceGameView, int screenX, int screenY) {
        super(context,spaceGameView,screenX,screenY,true,ScreenObjTypeEnum.Enemy);
        Bitmap scaledBitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.enemyship);
        scaledBitmap = Bitmap.createScaledBitmap(scaledBitmap, (int) (this.length * 1.25), (int) (this.height * 0.75), false); //cross the left screen limit
        initialise(scaledBitmap);
        this.setStatus(true);
        this.start();

        this.health = 100;
    }

    public void kill(){
        super.kill();
//        this.spaceGameView.destroyEnemy(this);
    }
    public void takeDamage(float damage)
    {
        this.health -= damage;
    }

    public float getHealth()
    {
        return this.health;
    }

//    public boolean checkCollisions(){
//        if (!super.checkCollisions()){
//            if (this.getStatus() && this.spaceGameView.checkBulletCollision(this)){
//                System.out.println("Bullet kill something");
//                this.kill();
//            }
//            return true;
//        }
//        return false;
//    }

}
