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

    Bitmap bitmap;

    Bomb(Context context, int x, int y, float damage, ProjectileType type) {
        super(context, x, y, damage, type);

        this.x = x;
        this.y = y;

        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipleft); // TODO: Replace bitmap res with new bomb bitmap
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int) (length), (int) (height),false);
    }

    public void update(long fps)
    {
        super.update(fps);
    }
}
