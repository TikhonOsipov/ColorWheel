package com.tixon.colorwheel.drawingstrategy;

import android.widget.ImageView;

public class DrawingContext {
    private Strategy strategy;

    public DrawingContext() {}

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void draw(ImageView imageView) {
        strategy.draw(imageView);
    }
}
