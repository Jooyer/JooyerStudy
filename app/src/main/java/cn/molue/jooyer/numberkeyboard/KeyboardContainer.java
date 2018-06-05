package cn.molue.jooyer.numberkeyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Jooyer on 2018/5/9
 * 键盘容器
 */

public class KeyboardContainer extends ConstraintLayout {
    private NumberKeyboardView mNumberKeyboardView;

    public KeyboardContainer(Context context) {
        this(context, null);
    }

    public KeyboardContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.key_board_container, this, true);
        mNumberKeyboardView = (NumberKeyboardView) view.findViewById(R.id.number_keyboard_view);
        mNumberKeyboardView.setEnabled(true);
        mNumberKeyboardView.setPreviewEnabled(false);
    }

    public void setOnKeyboardActionListener(KeyboardView.OnKeyboardActionListener listener) {
        mNumberKeyboardView.setOnKeyboardActionListener(listener);
    }

    public NumberKeyboardView getKeyboardView() {
        return mNumberKeyboardView;
    }


    public void setKeyboardType(KeyboardType type) {
        Keyboard keyboard = new Keyboard(getContext(), type == KeyboardType.NORMAL ?
                R.xml.keyboard_number : R.xml.keyboard_dot_number);
        mNumberKeyboardView.setTextSize(type == KeyboardType.NORMAL ? 24 : 18);
        mNumberKeyboardView.setKeyboard(keyboard);
    }

    public enum KeyboardType {
        NORMAL, DOT
    }

}
