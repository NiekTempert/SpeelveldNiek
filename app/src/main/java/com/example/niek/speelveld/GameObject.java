package com.example.niek.speelveld;

import android.graphics.Rect;

/**
 * Created by Niek on 15-8-2017.
 */

public abstract class GameObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public Rect getRectangle(){
        return new Rect(x, y, x+width, y+height);
    }
}
