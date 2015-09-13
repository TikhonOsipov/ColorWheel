package com.tixon.colorwheel.drawingstrategy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.widget.ImageView;

import com.tixon.colorwheel.R;

public class ColorWheel implements Strategy {
    Context context;

    public ColorWheel(Context context) {
        this.context = context;
    }

    @Override
    public void draw(ImageView imageView) {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bitmap = Bitmap.createBitmap(500, 500, conf); // this creates a MUTABLE bitmap
        Canvas canvas = new Canvas(bitmap);
        Path path = new Path();
        Paint paint = new Paint();
        final RectF oval = new RectF();

        float radius = 100f;

        path.addCircle(imageView.getWidth() / 2, imageView.getHeight() / 2, radius, Path.Direction.CW);
        paint.setColor(Color.BLUE); // установим белый цвет
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL); // заливаем
        paint.setAntiAlias(true);

        float center_x, center_y;
        center_x = 250;
        center_y = 250;

        oval.set(center_x - radius, center_y - radius, center_x + radius,
                center_y + radius);
        canvas.drawArc(oval, 45, 270, true, paint); // рисуем пакмана

        // рисуем большого пакмана без заливки
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(3);
        oval.set(center_x - 200f, center_y - 200f, center_x + 200f,
                center_y + 200f);

        paint.setColor(context.getResources().getColor(R.color.red));
        canvas.drawArc(oval, 120, 30, true, paint);
        paint.setColor(context.getResources().getColor(R.color.redOrange));
        canvas.drawArc(oval, 150, 30, true, paint);
        paint.setColor(context.getResources().getColor(R.color.orange));
        canvas.drawArc(oval, 180, 30, true, paint);
        paint.setColor(context.getResources().getColor(R.color.orangeYellow));
        canvas.drawArc(oval, 210, 30, true, paint);

        paint.setColor(context.getResources().getColor(R.color.yellow));
        canvas.drawArc(oval, 240, 30, true, paint);
        paint.setColor(context.getResources().getColor(R.color.yellowGreen));
        canvas.drawArc(oval, 270, 30, true, paint);
        paint.setColor(context.getResources().getColor(R.color.green));
        canvas.drawArc(oval, 300, 30, true, paint);
        paint.setColor(context.getResources().getColor(R.color.greenBlue));
        canvas.drawArc(oval, 330, 30, true, paint);
        paint.setColor(context.getResources().getColor(R.color.blue));
        canvas.drawArc(oval, 0, 30, true, paint);
        paint.setColor(context.getResources().getColor(R.color.bluePurple));
        canvas.drawArc(oval, 30, 30, true, paint);
        paint.setColor(context.getResources().getColor(R.color.purple));
        canvas.drawArc(oval, 60, 30, true, paint);
        paint.setColor(context.getResources().getColor(R.color.purpleRed));
        canvas.drawArc(oval, 90, 30, true, paint);

        paint.setColor(Color.parseColor("#cecece"));
        final RectF ovalSmall = new RectF();
        float radiusSmall = 50f;
        ovalSmall.set(center_x - radiusSmall, center_y - radiusSmall, center_x + radiusSmall,
                center_y + radiusSmall);
        canvas.drawOval(ovalSmall, paint);

        imageView.setImageBitmap(bitmap);

        Log.d("myLogs", "drawColorWheel: ivWidth = " + imageView.getWidth() + ", ivHeight = " +
                imageView.getHeight());
    }
}
