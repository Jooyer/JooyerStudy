package cn.molue.jooyer.limitedittext

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // demo 中默认 LimitEditText 只能输入字母数字和标点符号

       // 延时主要是更方便观察
        window.decorView.postDelayed({
            // 注意,获得焦点需要自己再处理下,其实很简单,如下:
            let_main.isFocusable = true
            let_main.isFocusableInTouchMode = true
            let_main.requestFocus()

        },1000)


    }
}
