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
import com.tixon.colorwheel.drawingstrategy.DoubleContrastSelector;
import com.tixon.colorwheel.drawingstrategy.DrawingContext;

public class FragmentDoubleContrastSelector extends AbstractFragment {
    Matrix matrix;
    DrawingContext drawingContext;

    public int rotationStep;

    public int rotation = 0;
    public int ivWidth = 0, ivHeight = 0;
    public boolean isUpdated = false;

    public ImageView ivWheel, ivSelector, ivColor1, ivColor2, ivColor3, ivColor4;

    public String[] colors;

    public FragmentDoubleContrastSelector() {}

    public static Fragment newInstance() {
        FragmentDoubleContrastSelector fragment = new FragmentDoubleContrastSelector();
        Bundle b = new Bundle();
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_double_contrast_selector, container, false);

        drawingContext = new DrawingContext();

        ivWheel = (ImageView) v.findViewById(R.id.ivWheelDoubleContrast);
        ivSelector = (ImageView) v.findViewById(R.id.ivSelectorDoubleContrast);
        ivColor1 = (ImageView) v.findViewById(R.id.ivColor1DoubleContrast);
        ivColor2 = (ImageView) v.findViewById(R.id.ivColor2DoubleContrast);
        ivColor3 = (ImageView) v.findViewById(R.id.ivColor3DoubleContrast);
        ivColor4 = (ImageView) v.findViewById(R.id.ivColor4DoubleContrast);
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

        drawingContext.setStrategy(new DoubleContrastSelector());
        drawingContext.draw(ivSelector);

        return v;
    }

    @Override
    public int[] selectColors(int step) {
        int color1, color2, color3, color4;
        color1 = 12 - step - 1;
        color2 = 12 - step + 1;
        color3 = 12 - step + 7;
        color4 = 12 - step + 5;

        if(color1 >= 12) color1 = color1 % 12;
        if(color2 >= 12) color2 = color2 % 12;
        if(color3 >= 12) color3 = color3 % 12;
        if(color4 >= 12) color4 = color4 % 12;

        if(color1 <= -12) color1 = color1 % 12;
        if(color2 <= -12) color2 = color2 % 12;
        if(color3 <= -12) color3 = color3 % 12;
        if(color4 >= 12) color4 = color4 % 12;

        if(color1 < 0) color1 += 12;
        if(color2 < 0) color2 += 12;
        if(color3 < 0) color3 += 12;
        if(color4 < 0) color4 += 12;
        Log.d("_color", "NEW _ step = " + step + ", color1 = " + color1 + ", color2 = " + color2 +
                ", color3 = " + color3 + ", color4 = " + color4);
        return new int[] {color1, color2, color3, color4};
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
                    setColors(selectColors(rotationStep), ivColor1, ivColor2, ivColor3, ivColor4);
                }
                if(rightLimitReached(deltaR)) {
                    updateRotation(30, ivWheel, matrix);
                    rotationStep++;
                    rotation = newRotation;
                    setColors(selectColors(rotationStep), ivColor1, ivColor2, ivColor3, ivColor4);
                }
            }
            return true;
        }
    };
}
