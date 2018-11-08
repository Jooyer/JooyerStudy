package cn.molue.jooyer.limitedittext

import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import android.widget.EditText
import java.util.regex.Pattern

/**
 * Desc: 通过重写  onCreateInputConnection 实现文本过滤
 * Author: Jooyer
 * Date: 2018-08-27
 * Time: 10:35
 */
class LimitEditText(context: Context, attrs: AttributeSet, defStyleAttr: Int)
    : EditText(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    /**
     *  输入法
     */
    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection {
        return InnerInputConnection(super.onCreateInputConnection(outAttrs), false)
    }

}

class InnerInputConnection(target: InputConnection, mutable: Boolean)
    : InputConnectionWrapper(target, mutable) {
    // 数字,字母
    private val pattern = Pattern.compile("^[0-9A-Za-z_]\$")
    // 标点
    private val patternChar = Pattern.compile("[^\\w\\s]+")
    // EmoJi
    private val patternEmoJi = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE or Pattern.CASE_INSENSITIVE)
    // 英文标点
    private val patternEn = Pattern.compile("^[`~!@#\$%^&*()_\\-+=<>?:\"{},.\\\\/;'\\[\\]]\$")
    // 中文标点
    private val patternCn = Pattern.compile("^[·！#￥（——）：；“”‘、，|《。》？、【】\\[\\]]\$")


    // 对输入拦截
    override fun commitText(text: CharSequence?, newCursorPosition: Int): Boolean {
        if (patternEmoJi.matcher(text).find()){
            return false
        }

        if (pattern.matcher(text).matches() || patternChar.matcher(text).matches()) {
            return super.commitText(text, newCursorPosition)
        }
        return false
    }

}
