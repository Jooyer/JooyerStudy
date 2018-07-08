package cn.molue.jooyer.dialog;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;


/**
 * 圆弧效果
 */

public class ArcBackgroundDrawable extends Drawable {

    private Path mPath;
    private Paint mPaint;

    public ArcBackgroundDrawable() {
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#ff4c4f"));
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        // canvas 抗锯齿 , 没有效果
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG));

        Rect bounds = getBounds();

        mPath.moveTo(bounds.left, bounds.top + (bounds.bottom - bounds.top) / 5);
        // quadTo(float x1, float y1, float x2, float y2)
        // 其中，x1，y1为控制点的坐标值，x2，y2为终点的坐标值；
        mPath.quadTo(bounds.left + (bounds.right - bounds.left) / 2,
                bounds.top - (bounds.bottom - bounds.top) / 5,
                bounds.right,
                bounds.top + (bounds.bottom - bounds.top) / 5);

        mPath.lineTo(bounds.right, bounds.bottom - dp2px(20));

        mPath.arcTo(new RectF(bounds.right - dp2px(20), bounds.bottom - dp2px(20),
                bounds.right, bounds.bottom), 0, 90);
        mPath.lineTo(bounds.left + dp2px(20), bounds.bottom);
        mPath.arcTo(new RectF(bounds.left, bounds.bottom - dp2px(20),
                bounds.left + dp2px(20), bounds.bottom), 90, 90);

        mPath.close();
//        canvas.clipPath(mPath); 直接绘制没有抗锯齿效果
        canvas.drawPath(mPath, mPaint);

        mPaint.setXfermode(null);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
    
    private int dp2px(int defVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,defVal, Resources.getSystem().getDisplayMetrics());
    }
    
    
}
