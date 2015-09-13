package com.tixon.colorwheel.drawingstrategy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.ImageView;

import com.tixon.colorwheel.Drawer;

public class ComplementarySelector extends Drawer implements Strategy{
    @Override
    public void draw(ImageView imageView) {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = Bitmap.createBitmap(500, 500, conf);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#323232"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);

        final RectF oval1 = new RectF();
        final RectF oval2 = new RectF();
        final RectF oval3 = new RectF();

        float[] center1 = calculateCoordinates(0, new float[] {0, 160}, 250, 250);
        float[] center2 = calculateCoordinates(150, new float[] {0, 160}, 250, 250);
        float[] center3 = calculateCoordinates(210, new float[] {0, 160}, 250, 250);

        oval1.set(center1[0] - 20, center1[1] - 20, center1[0] + 20, center1[1] + 20);
        oval2.set(center2[0] - 20, center2[1] - 20, center2[0] + 20, center2[1] + 20);
        oval3.set(center3[0] - 20, center3[1] - 20, center3[0] + 20, center3[1] + 20);

        canvas.drawOval(oval1, paint);
        canvas.drawOval(oval2, paint);
        canvas.drawOval(oval3, paint);

        imageView.setImageBitmap(bitmap);
    }
}
