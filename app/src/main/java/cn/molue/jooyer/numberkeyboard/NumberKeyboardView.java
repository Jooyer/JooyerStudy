package cn.molue.jooyer.numberkeyboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.IntegerRes;
import android.util.AttributeSet;
import android.util.TypedValue;

import java.util.List;


/**
 * Created by Jooyer on 2018/5/9
 * 数字小键盘
 */

public class NumberKeyboardView extends KeyboardView {
    private static final String TAG = NumberKeyboardView.class.getSimpleName();
    private Drawable rKeyBackground;
    private Paint mPaint;
    private int rLabelTextSize;
    private int rKeyTextSize;
    private int rKeyTextColor;
    private float rShadowRadius;
    private int rShadowColor;

    public NumberKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public NumberKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray arr = context.obtainStyledAttributes(attrs,R.styleable.NumberKeyboardView);
        rKeyBackground = arr.getDrawable(R.styleable.NumberKeyboardView_keyBackground);
        if (null  == rKeyBackground){
            rKeyBackground = context.getResources().getDrawable(R.drawable.key_number_bg);
        }
        rLabelTextSize = arr.getDimensionPixelSize(R.styleable.NumberKeyboardView_labelTextSize,18);
        rKeyTextSize = arr.getDimensionPixelSize(R.styleable.NumberKeyboardView_keyTextSize,18);
        rKeyTextColor = arr.getColor(R.styleable.NumberKeyboardView_keyTextColor,0xFF000000);
        rShadowColor = arr.getColor(R.styleable.NumberKeyboardView_shadowColor,0);
        rShadowRadius = arr.getFloat(R.styleable.NumberKeyboardView_shadowRadius,0f);
        arr.recycle();


        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(rKeyTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAlpha(255);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onRefreshKey(canvas);
    }

    /**
     * onRefreshKey是对父类的private void onBufferDraw()进行的重写.
     * 只是在对key的绘制过程中进行了重新设置.
     */
    private void onRefreshKey(Canvas canvas) {
        final int kbdPaddingLeft = getPaddingLeft();
        final int kbdPaddingTop = getPaddingTop();
        Drawable keyBackground = null;
        mPaint.setColor(rKeyTextColor);
        List<Keyboard.Key> keys = getKeyboard().getKeys();
        for (Keyboard.Key key : keys) {
            keyBackground = key.iconPreview;
            if (null == keyBackground) {
                keyBackground = rKeyBackground;
            }
            int[] drawableState = key.getCurrentDrawableState();
            keyBackground.setState(drawableState);
            CharSequence keyLabel = key.label;
            String label = keyLabel == null ? null : adjustCase(keyLabel).toString();
            final Rect bounds = keyBackground.getBounds();
            if (key.width != bounds.right ||
                    key.height != bounds.bottom) {
                keyBackground.setBounds(0, 0, key.width, key.height);
            }
            canvas.translate(key.x + kbdPaddingLeft, key.y + kbdPaddingTop);
            keyBackground.draw(canvas);
            if (label != null) {
                mPaint.setColor(rKeyTextColor);
                if (key.codes[0] == getKeyCode(R.integer.action_done)) {
                    mPaint.setTextSize(dp2px(rLabelTextSize));
                    mPaint.setTypeface(Typeface.DEFAULT);
                    mPaint.setColor(Color.WHITE);
                } else if (key.codes[0] == getKeyCode(R.integer.line_feed)) {
                    mPaint.setTextSize(dp2px(16));
                    mPaint.setTypeface(Typeface.DEFAULT);
                } else if (label.length() > 1 && key.codes.length < 2) {
                    mPaint.setTextSize(dp2px(rLabelTextSize));
                    mPaint.setTypeface(Typeface.DEFAULT);
                } else {
                    mPaint.setTextSize(dp2px(rKeyTextSize));
                    mPaint.setTypeface(Typeface.DEFAULT);
                }

                // Draw a drop shadow for the text
                mPaint.setShadowLayer(rShadowRadius, 0, 0, rShadowColor);
                // Draw the text
                canvas.drawText(label,
                        key.width/ 2,
                        key.height / 2  + (mPaint.getTextSize() - mPaint.descent()) / 2,
                        mPaint);
                // Turn off drop shadow
                mPaint.setShadowLayer(0, 0, 0, 0);
            } else if (key.icon != null) {
                final int drawableX = (key.width
                        - key.icon.getIntrinsicWidth()) / 2 ;
                final int drawableY = (key.height
                        - key.icon.getIntrinsicHeight()) / 2 ;
                canvas.translate(drawableX, drawableY);
                key.icon.setBounds(0, 0,
                        key.icon.getIntrinsicWidth(), key.icon.getIntrinsicHeight());
                key.icon.draw(canvas);
                canvas.translate(-drawableX, -drawableY);
            }
            canvas.translate(-key.x - kbdPaddingLeft, -key.y - kbdPaddingTop);
        }
    }


    private int getKeyCode(@IntegerRes int redId) {
        return getContext().getResources().getInteger(redId);
    }

    private int dp2px(int def) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, def, getResources().getDisplayMetrics());
    }


    private CharSequence adjustCase(CharSequence label) {
        if (getKeyboard().isShifted() && label != null && label.length() < 3
                && Character.isLowerCase(label.charAt(0))) {
            label = label.toString().toUpperCase();
        }
        return label;
    }

    public void setTextSize(int textSize) {
        rLabelTextSize = textSize;
    }
}


