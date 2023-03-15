package com.example.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;

public class Bomb extends Projectile{

    float damage;
    int x;
    int y;

    Bomb(Context context, int x, int y, int screenX, int screenY, Bitmap bitmap, float damage) {
        super(context, x, y, screenX, screenY, bitmap, damage);

        this.x = x;
        this.y = y;
    }

    public void draw()
    {
        super.draw(this.x, this.y);
    }
}
