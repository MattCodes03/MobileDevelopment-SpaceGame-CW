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

    Projectile(Context context, int x, int y, int screenX, int screenY, float damage)
    {


        this.x = x;
        this.y = y;

        this.damage = damage;
    }

    public void draw(int x, int y, Bitmap bitmap)
    {
        /* Draw the passed bitmap to canvas at position x and position y */
    }

    public void collideWithPlayer()
    {
        /* Write function so that if the projectile this.rect collides with player Rect substitute this.damage from player health */
    }
}
