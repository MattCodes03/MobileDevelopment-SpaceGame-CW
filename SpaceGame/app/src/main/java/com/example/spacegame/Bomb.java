package com.example.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.view.SurfaceHolder;

public class Bomb extends Projectile{

    float damage;
    int x;
    int y;

    Bomb(Context context, int x, int y, float damage, ProjectileType type) {
        super(context, x, y, damage, type);

        this.x = x;
        this.y = y;
    }

    public void update(long fps)
    {
        super.update(fps);
    }
}
