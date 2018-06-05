package cn.molue.jooyer.numberkeyboard

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.EditText


/**
 * Dialog使用方式:
 * https://blog.csdn.net/fightfightfight/article/details/51169616
 *
 *
 * Created by Jooyer on 2018/6/4
 * 基于 DialogFragment 实现的键盘面板
 */
class NumberDialog : DialogFragment() {
    private var mKeyboardUtil: KeyboardUtil? = null
    private var mType: KeyboardContainer.KeyboardType = KeyboardContainer.KeyboardType.NORMAL
    private var mEditText: EditText? = null
    private var mOnDoneListener: OnDoneListener? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mKeyboardUtil = KeyboardUtil(activity, mType)
        mKeyboardUtil!!.bindEditText(mEditText)
        mKeyboardUtil!!.setOnDoneListener(object : OnDoneListener {
            override fun onDone(text: String) {
                mOnDoneListener!!.onDone(text)
            }

            override fun onHide() {
                hideSoftKeyboard()
            }

        })
        Log.i("NumberDialog", "30-------------> ")
        return AlertDialog.Builder(activity)
                .setView(mKeyboardUtil!!.keyboardContainer)
                .create()
    }

    override fun onStart() {
        super.onStart()
        val window = dialog.window
        val params = window.attributes
        params.gravity = Gravity.BOTTOM
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.dimAmount = 0f // 不变暗
        window.attributes = params

        // 这里用透明颜色替换掉系统自带背景
        val color = ContextCompat.getColor(activity, android.R.color.transparent)
        window.setBackgroundDrawable(ColorDrawable(color))
    }


    fun setType(type: KeyboardContainer.KeyboardType): NumberDialog {
        mType = type
        return this
    }

    fun bindEditText(editText: EditText): NumberDialog {
        mEditText = editText;
        return this
    }

    fun setOnDoneListener(onDoneListener: OnDoneListener): NumberDialog {
        mOnDoneListener = onDoneListener;
        return this
    }


    fun hideSoftKeyboard() {
        mKeyboardUtil!!.hideSoftKeyboard()
    }

    companion object {
        fun newInstance(): NumberDialog {
            return NumberDialog()
        }
    }



    // 使用方式二
//    val numberDialog = NumberDialog.newInstance()
//    numberDialog.bindEditText(et_test)
//    .setOnDoneListener(object : OnDoneListener {
//        override fun onDone(text: String) {
//            Log.i("TEST", "40------------->$text ")
//            numberDialog.hideSoftKeyboard()
//        }
//    })
//    et_test.setOnFocusChangeListener { v, hasFocus ->
//        if (hasFocus){
//            numberDialog.show(supportFragmentManager, "Number")
//        }
//    }


}
