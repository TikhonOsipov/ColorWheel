package com.tixon.colorwheel.fragments;

import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.tixon.colorwheel.R;

//Here are defined methods that are used in each selector fragment
public abstract class AbstractFragment extends Fragment {

    public boolean rightLimitReached(int value) {
        boolean result = false;
        if(value > 0) {
            if(value >= 30) result = true;
        }
        return result;
    }

    public boolean leftLimitReached(int value) {
        boolean result = false;
        if(value < 0) {
            if(value < -180) {
                if((360 - value) >= 30) result = true;
            }
            else {
                if(Math.abs(value) >= 30) result = true;
            }
        }
        return result;
    }

    public void updateRotation(int rotation, ImageView imageView, Matrix matrix) {
        matrix.postRotate((float) rotation, imageView.getWidth() / 2, imageView.getHeight() / 2);
        imageView.setImageMatrix(matrix);
    }

    public void updateRotation(int rotation, int width, int height, ImageView imageView, Matrix matrix) {
        matrix.postRotate((float) rotation, width/2, height/2);
        Log.d("myLogs", "updateRotation: ivWidth = " + width + ", ivHeight = " +
                height);
        imageView.setImageMatrix(matrix);
    }

    public void setColors(int[] colors, ImageView... imageViews) {
        for(int i = 0; i < colors.length; i++) {
            imageViews[i].setBackgroundColor(Color.parseColor(getResources()
                    .getStringArray(R.array.colors)[colors[i]]));
        }
    }

    public abstract int[] selectColors(int step);
}
