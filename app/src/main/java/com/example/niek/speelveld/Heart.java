package com.example.niek.speelveld;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Niek on 21-8-2017.
 */

public class Heart extends GameObject{
    private int score;
    private int speed;
    private Bitmap spritesheet;

    public Heart(Bitmap res, int x, int y, int w, int h, int s){
        super.x = x;
        super.y = y;

        width = w;
        height = h;
        score = s;

        speed = 7 + score/30;

        spritesheet = res;
    }
    public void update(){
        x-= speed;
    }
    public void draw(Canvas canvas){
        Paint myPaint = new Paint();
        myPaint.setStrokeWidth(1);
        canvas.drawBitmap(spritesheet, x , y, null);
    }
}
