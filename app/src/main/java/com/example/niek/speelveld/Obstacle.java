package com.example.niek.speelveld;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Niek on 16-8-2017.
 */

public class Obstacle extends GameObject{
    private int score;
    private int speed;
    private Bitmap spritesheet;

    public Obstacle(Bitmap res, int x, int y, int w, int h, int s){
        super.x = x;
        super.y = y;

        width = w;
        height = h;
        score = s;

        speed = 7 + score/30;

        spritesheet = res;
    }
    public void update() {
        x-=speed;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(spritesheet, x , y, null);
    }
}
