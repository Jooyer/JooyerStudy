package cn.molue.jooyer.numberkeyboard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // KeyboardType 一般类型是没有逗号的,另一种有逗号
        val keyboardUtil = KeyboardUtil(this,
                KeyboardContainer.KeyboardType.NORMAL);
        keyboardUtil.bindEditText(et_test)
        keyboardUtil.setOnDoneListener(object : OnDoneListener {
            override fun onDone(text: String) {
                Log.i("TEST", "21------------->$text ")
            }
            /**
             *  这里当 触摸了 EditText 会回调这,
             *  如果不需要此回调,可不用重写
             */
            override fun onTouchEditText(et: EditText) {
            }
        })
    }
}
