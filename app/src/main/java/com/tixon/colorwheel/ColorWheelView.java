package com.tixon.colorwheel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ColorWheelView extends View {
    public ColorWheelView(Context context) {
        super(context);
    }

    public ColorWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = measureWidth(widthMeasureSpec);
        int measuredHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        // Размер по умолчанию, если ограничения не были установлены.
        int result = 500;

        if (specMode == MeasureSpec.AT_MOST) {
            // Рассчитайте идеальный размер вашего
            // элемента в рамках максимальных значений.
            // Если ваш элемент заполняет все доступное
            // пространство, верните внешнюю границу.
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            // Если ваш элемент может поместиться внутри этих границ, верните это значение.
            result = specSize;
        }
        return result;
    }
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        // Размер по умолчанию, если ограничения не были установлены.
        int result = 500;
        if (specMode == MeasureSpec.AT_MOST) {
            // Рассчитайте идеальный размер вашего
            // элемента в рамках максимальных значений.
            // Если ваш элемент заполняет все доступное
            // пространство, верните внешнюю границу.
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            // Если ваш элемент может поместиться внутри этих границ, верните это значение.
            result = specSize;
        }
        return result;
    }

    Path path = new Path();
    Paint paint = new Paint();
    final RectF oval = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = 400f;
        float height = 240f;
        float radius = 100f;

        path.addCircle(width, height, radius, Path.Direction.CW);
        paint.setColor(Color.BLUE); // установим белый цвет
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL); // заливаем
        paint.setAntiAlias(true);

        float center_x, center_y;
        center_x = 240;
        center_y = 220;

        oval.set(center_x - radius, center_y - radius, center_x + radius,
                center_y + radius);
        //canvas.rotate(-25, 0, 0);
        canvas.drawArc(oval, 45, 270, true, paint); // рисуем пакмана

// рисуем большого пакмана без заливки
        paint.setStyle(Paint.Style.STROKE);
        oval.set(center_x - 200f, center_y - 200f, center_x + 200f,
                center_y + 200f);
        canvas.drawArc(oval, 0, 30, true, paint);
        paint.setColor(Color.RED);
        canvas.drawArc(oval, 30, 30, true, paint);
        canvas.drawArc(oval, 60, 30, true, paint);
        canvas.drawArc(oval, 90, 30, true, paint);
        canvas.drawArc(oval, 120, 30, true, paint);
        canvas.drawArc(oval, 150, 30, true, paint);

        paint.setStyle(Paint.Style.STROKE);


    }
}
