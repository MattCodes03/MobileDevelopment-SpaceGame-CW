package com.example.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;

public class Projectile {
    RectF rect;

    float height;
    float length;

    int x;
    int y;

    Bitmap bitmap;

    float damage;

    Projectile(Context context, int x, int y, int screenX, int screenY, Bitmap bitmap, float damage)
    {


        this.x = x;
        this.y = y;


        this.bitmap = bitmap;
        this.damage = damage;
    }

    public void draw(int x, int y)
    {

    }
}
