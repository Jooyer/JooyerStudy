package cn.molue.jooyer.toolbar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ct_tool_bar1.setLeftImageListener(View.OnClickListener {
            Toast.makeText(this@MainActivity,"点击了左侧图片",Toast.LENGTH_SHORT).show()
        })

        ct_tool_bar1.setRightTextListener(View.OnClickListener {
            Toast.makeText(this@MainActivity,"点击了右侧文字",Toast.LENGTH_SHORT).show()
        })

        ct_tool_bar2.setRightImageListener(View.OnClickListener {
            Toast.makeText(this@MainActivity,"点击了右侧图片",Toast.LENGTH_SHORT).show()
        })
    }
}
