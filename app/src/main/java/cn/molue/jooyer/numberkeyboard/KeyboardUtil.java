package cn.molue.jooyer.numberkeyboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.support.annotation.IntegerRes;
import android.text.Editable;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;

/**
 * 参考:
 * https://juejin.im/post/5ae0ff7ff265da0b9671e835
 * https://www.jianshu.com/p/b1973de976e4
 * Created by Jooyer on 2018/5/9
 * 自定义键盘工具类
 */

public class KeyboardUtil {
    private static final String TAG = KeyboardUtil.class.getSimpleName();

    private KeyboardView mKeyboardView;

    private EditText mEditText;
    private ViewGroup mRootView;
    private Rect mRect = new Rect();

    private KeyboardContainer mKeyboardContainer;
    private FrameLayout.LayoutParams mKeyboardContainerParams;

    private int dp2px(Context context, int def) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, def, context.getResources().getDisplayMetrics());
    }

    public KeyboardUtil(final Activity context, KeyboardContainer.KeyboardType type) {
        mRootView = (ViewGroup) context.getWindow().getDecorView().findViewById(android.R.id.content);
        mKeyboardContainer = new KeyboardContainer(context);
        mKeyboardContainer.setOnKeyboardActionListener(mKeyboardActionListener);
        mKeyboardContainerParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mKeyboardContainerParams.gravity = Gravity.BOTTOM;
        mKeyboardContainer.setKeyboardType(type);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void bindEditText(EditText editText) {
        mEditText = editText;
        mEditText.setTag(0);
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (0 == ((int) v.getTag())) {
                    if (null != mOnDoneListener) {
                        mOnDoneListener.onTouchEditText(mEditText);
                    }
                    showSoftKeyboard();
                }
                if (mEditText.getText().length() > 0
                        && mEditText.getSelectionStart() !=  mEditText.getText().length()) {
                    mEditText.setSelection(mEditText.getText().length());
                }
                return false;
            }
        });
        mKeyboardContainer.setOnKeyboardActionListener(mKeyboardActionListener);
        hideSystemSoftKeyboard();
    }


    private void showSoftKeyboard() {
        mEditText.setTag(1);
        mRootView.addOnLayoutChangeListener(mOnLayoutChangeListener);

        mKeyboardContainer.setPadding(
                dp2px(mEditText.getContext(), 0),
                dp2px(mEditText.getContext(), -1),
                dp2px(mEditText.getContext(), 0),
                dp2px(mEditText.getContext(), 0));
        if (mRootView.indexOfChild(mKeyboardContainer) == -1) { // 这个思路不错哦
            if (null != mKeyboardContainer.getParent()) {
                ((ViewGroup) mKeyboardContainer.getParent()).removeView(mKeyboardContainer);
            }
            mRootView.addView(mKeyboardContainer, mRootView.getChildCount(), mKeyboardContainerParams);
        } else {
            mKeyboardContainer.setVisibility(View.VISIBLE);
        }

        mKeyboardContainer.setAnimation(AnimationUtils.loadAnimation(mEditText.getContext(),
                R.anim.anim_bottom_in));
    }

    public void hideSoftKeyboard() {
        if (null != mEditText && -1 != mRootView.indexOfChild(mKeyboardContainer)) {
            mEditText.setTag(0);
            mKeyboardContainer.startAnimation(AnimationUtils.loadAnimation(mEditText.getContext(),
                    R.anim.anim_bottom_out));

            mKeyboardContainer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRootView.removeView(mKeyboardContainer);
                }
            }, 400);
        }
    }


    private void hideSystemSoftKeyboard() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mEditText.setShowSoftInputOnFocus(false);
        } else {
            mEditText.setInputType(InputType.TYPE_NULL);
        }
    }


    private int getKeyCode(Context context, @IntegerRes int redId) {
        return context.getResources().getInteger(redId);
    }


    private final KeyboardView.OnKeyboardActionListener mKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {
            // 按下 key 时执行
        }

        @Override
        public void onRelease(int primaryCode) {
            // 释放 key 时执行
        }

        // 点击  key 时执行
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = mEditText.getText();
            int start = mEditText.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_CANCEL
                    || primaryCode == getKeyCode(mEditText.getContext(), R.integer.hide_keyboard)) {
                hideSoftKeyboard();
                mOnDoneListener.onHide();
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) { // 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == getKeyCode(mEditText.getContext(), R.integer.action_done)) { // 确定
                if (null != mOnDoneListener) {
                    mOnDoneListener.onDone(mEditText.getText().toString());
                }
            } else if (primaryCode == getKeyCode(mEditText.getContext(), R.integer.line_feed)) { // 换行
                editable.insert(start, "\n");
            } else { // 输入键盘值
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };

    private final View.OnLayoutChangeListener mOnLayoutChangeListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                   int oldLeft, int oldTop, int oldRight, int oldBottom) {
            int hasMoved = 0;
            Object rootViewTag = mRootView.getTag(R.id.root_view);
            if (rootViewTag != null) {
                hasMoved = (int) rootViewTag;
            }
            if (mKeyboardContainer.getVisibility() == View.GONE) {
                mRootView.removeOnLayoutChangeListener(mOnLayoutChangeListener);
                if (hasMoved > 0) {
                    mRootView.getChildAt(0).scrollBy(0, -1 * hasMoved);
                    mRootView.setTag(R.id.root_view, 0);
                }
            } else {
                mRect.setEmpty();
                mRootView.getWindowVisibleDisplayFrame(mRect);
                int[] etLocation = new int[2];
                mEditText.getLocationOnScreen(etLocation);

                int keyboardTop = etLocation[1] + mEditText.getHeight()
                        + mEditText.getPaddingTop() + mEditText.getPaddingBottom() + 1;   //1px is a divider

                Object anchorTag = mEditText.getTag(R.id.anchor_view);
                View mShowAnchorView = null;
                if (anchorTag != null && anchorTag instanceof View) {
                    mShowAnchorView = (View) anchorTag;
                }
                if (mShowAnchorView != null) {
                    int[] saLocation = new int[2];
                    mShowAnchorView.getLocationOnScreen(saLocation);
                    keyboardTop = saLocation[1] + mShowAnchorView.getHeight() + mShowAnchorView.getPaddingTop() + mShowAnchorView   //1px is a divider
                            .getPaddingBottom() + 1;
                }
                int moveHeight = keyboardTop + mKeyboardContainer.getHeight() - mRect.bottom;
                //height > 0  , rootView 需要继续上滑
                if (moveHeight > 0) {
                    mRootView.getChildAt(0).scrollBy(0, moveHeight);
                    mRootView.setTag(R.id.root_view, hasMoved + moveHeight);
                } else {
                    int moveBackHeight = Math.min(hasMoved, Math.abs(moveHeight));
                    if (moveBackHeight > 0) {
                        mRootView.getChildAt(0).scrollBy(0, -1 * moveBackHeight);
                        mRootView.setTag(R.id.root_view, hasMoved - moveBackHeight);
                    }
                }

            }
        }
    };

    public KeyboardContainer getKeyboardContainer() {
        return mKeyboardContainer;
    }

    private OnDoneListener mOnDoneListener;

    public void setOnDoneListener(OnDoneListener onDoneListener) {
        mOnDoneListener = onDoneListener;
    }


}

