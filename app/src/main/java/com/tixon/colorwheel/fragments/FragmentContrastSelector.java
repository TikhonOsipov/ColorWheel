package com.tixon.colorwheel.fragments;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.tixon.colorwheel.R;
import com.tixon.colorwheel.drawingstrategy.ColorWheel;
import com.tixon.colorwheel.drawingstrategy.ContrastSelector;
import com.tixon.colorwheel.drawingstrategy.DrawingContext;

public class FragmentContrastSelector extends AbstractFragment {
    Matrix matrix;
    DrawingContext drawingContext;

    public int rotationStep;

    public int rotation = 0;
    public int ivWidth = 0, ivHeight = 0;
    public boolean isUpdated = false;

    public ImageView ivWheel, ivSelector, ivColor1, ivColor2;

    public String[] colors;

    public FragmentContrastSelector() {}

    public static Fragment newInstance() {
        FragmentContrastSelector fragment = new FragmentContrastSelector();
        Bundle b = new Bundle();
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contrast_selector, container, false);

        drawingContext = new DrawingContext();

        ivWheel = (ImageView) v.findViewById(R.id.ivWheelContrast);
        ivSelector = (ImageView) v.findViewById(R.id.ivSelectorContrast);
        ivColor1 = (ImageView) v.findViewById(R.id.ivColor1Contrast);
        ivColor2 = (ImageView) v.findViewById(R.id.ivColor2Contrast);
        ivWheel.setOnTouchListener(onImageTouched);

        ivWheel.setScaleType(ImageView.ScaleType.MATRIX);
        matrix = new Matrix();

        rotationStep = 0;
        colors  = getResources().getStringArray(R.array.colors);

        ViewTreeObserver viewTreeObserver = ivWheel.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                ivHeight = ivWheel.getMeasuredHeight();
                ivWidth = ivWheel.getMeasuredHeight();
                Log.d("myLogs", "onPreDraw: ivWidth = " + ivWidth + ", ivHeight = " +
                        ivHeight);
                if (!isUpdated) { //update rotation only one time
                    updateRotation(15, ivWidth, ivHeight, ivWheel, matrix);
                    isUpdated = true;
                }
                return true;
            }
        });

        drawingContext.setStrategy(new ColorWheel(getActivity()));
        drawingContext.draw(ivWheel);

        drawingContext.setStrategy(new ContrastSelector());
        drawingContext.draw(ivSelector);

        return v;
    }

    public View.OnTouchListener onImageTouched = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            double r, newR;
            int newRotation;

            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                r = Math.atan2(event.getX() - ivWheel.getWidth()/2, ivWheel.getHeight()/2 - event.getY());
                rotation = (int) Math.toDegrees(r);
                Log.d("myLogs", "touch: rotation = " + rotation);
                Log.d("myLogs", "X = " + event.getX() + ", Y = " + event.getY());
            }

            if(event.getAction() == MotionEvent.ACTION_MOVE) {
                newR = Math.atan2(event.getX() - ivWheel.getWidth()/2, ivWheel.getHeight()/2 - event.getY());
                newRotation = (int) Math.toDegrees(newR);
                int deltaR = newRotation - rotation;
                Log.d("myLogs", "move: rot = " + rotation + ", newRot = " + newRotation +
                        ", delta R = " + deltaR);
                if(deltaR < -180) deltaR += 360;
                if(deltaR > 180) deltaR -= 360;
                if(leftLimitReached(deltaR)) {
                    updateRotation(-30, ivWheel, matrix);
                    rotationStep--;
                    rotation = newRotation;
                    setColors(selectColors(rotationStep), ivColor1, ivColor2);
                }
                if(rightLimitReached(deltaR)) {
                    updateRotation(30, ivWheel, matrix);
                    rotationStep++;
                    rotation = newRotation;
                    setColors(selectColors(rotationStep), ivColor1, ivColor2);
                }
            }
            return true;
        }
    };

    @Override
    public int[] selectColors(int step) {
        int color1, color2;
        color1 = 12 - step;
        color2 = 12 - step + 6;

        if(color1 >= 12) color1 = color1 % 12;
        if(color2 >= 12) color2 = color2 % 12;

        if(color1 <= -12) color1 = color1 % 12;
        if(color2 <= -12) color2 = color2 % 12;

        if(color1 < 0) color1 += 12;
        if(color2 < 0) color2 += 12;
        Log.d("_color", "NEW _ step = " + step + ", color1 = " + color1 + ", color2 = " + color2);
        return new int[] {color1, color2};
    }
}
