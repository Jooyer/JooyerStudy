package cn.molue.jooyer.dialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import cn.molue.jooyer.dialog.R;

/**
 * Created by Jooyer on 2018/07/02
 */

public class JAlertDialog extends Dialog {

    private JAlertController mAlert;

    public JAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new JAlertController(this, getWindow());
    }


    public static class Builder {
        private final JAlertController.AlertParams mAlertParams;


        public Builder(Context context) {
            this(context, R.style.JDialogStyle);
        }

        public Builder(Context context, @StyleRes int themeRes) {
            mAlertParams = new JAlertController.AlertParams(context, themeRes);
        }

        public Builder setContentView(View view) {
            mAlertParams.mView = view;
            mAlertParams.mViewLayoutResId = 0;
            return this;
        }

        public Builder setContentView(int layoutResId) {
            mAlertParams.mView = null;
            mAlertParams.mViewLayoutResId = layoutResId;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mAlertParams.mCancelable = cancelable;
            return this;
        }


        public Builder setText(@IdRes int viewId, CharSequence text) {
            mAlertParams.mTextArr.put(viewId,text);
            return this;
        }


        public Builder setFromBottom() {
            mAlertParams.mGravity = Gravity.BOTTOM;
            return this;
        }

        public Builder setAnimation(@StyleRes int styleAnim) {
            mAlertParams.mAnimation = styleAnim;
            return this;
        }

        public Builder setHasAnimation(boolean hasAnimation) {
            mAlertParams.mHasAnimation = hasAnimation;
            return this;
        }

        public Builder setFullWidth() {
            mAlertParams.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        public Builder setWidthAndHeight(int width,int height) {
            mAlertParams.mWidth = width;
            mAlertParams.mHeight = height;
            return this;
        }

        public Builder setOnClick(@IdRes int viewId) {
            mAlertParams.mClickArr.put(mAlertParams.mClickArr.size(),viewId);
            return this;
        }

        public Builder setOnJAlertDialogCLickListener(OnJAlertDialogClickListener onJAlertDialogClickListener) {
            mAlertParams.mOnJAlertDialogClickListener = onJAlertDialogClickListener;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            mAlertParams.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnOnDismissListener(OnDismissListener onDismissListener) {
            mAlertParams.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            mAlertParams.mOnKeyListener = onKeyListener;
            return this;
        }


        public JAlertDialog create() {
            final JAlertDialog dialog = new JAlertDialog(mAlertParams.mContext, mAlertParams.mThemeRes);
            mAlertParams.apply(dialog.mAlert);
            dialog.setCancelable(mAlertParams.mCancelable);
            if (mAlertParams.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(mAlertParams.mOnCancelListener);
            dialog.setOnDismissListener(mAlertParams.mOnDismissListener);
            if (mAlertParams.mOnKeyListener != null) {
                dialog.setOnKeyListener(mAlertParams.mOnKeyListener);
            }
            return dialog;
        }

        public JAlertDialog show() {
            JAlertDialog dialog = create();
            dialog.show();
            return dialog;
        }


    }

}
