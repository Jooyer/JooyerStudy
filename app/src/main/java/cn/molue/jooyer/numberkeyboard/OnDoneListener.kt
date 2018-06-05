package cn.molue.jooyer.numberkeyboard

import android.widget.EditText

/**
 * Created by Jooyer on 2018/6/4
 *
 */
interface OnDoneListener {

    fun onDone(text: String)

    // 主要方便在 Dialog 使用,其他情况一般用不到
    fun onHide(){

    }

    // 当 et 被点击后,需要操作可以在此处
    fun onTouchEditText(et: EditText) {

    }

}