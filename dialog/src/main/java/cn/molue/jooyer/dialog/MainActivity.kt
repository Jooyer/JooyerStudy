package cn.molue.jooyer.dialog

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import cn.molue.jooyer.dialog.dialog.JAlertDialog
import cn.molue.jooyer.dialog.dialog.OnJAlertDialogClickListener
import kotlinx.android.synthetic.main.activity_main.*

/** 参考: https://www.jianshu.com/p/87288925ee1f
 *  搭建自己的Dialog
 */
class MainActivity : AppCompatActivity() {
    private lateinit var mBottomDialog: JAlertDialog;
    private lateinit var mUpdateDialog: JAlertDialog;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBottomDialog()
        initUpdateDialog()
        tv_center.setOnClickListener {
            mBottomDialog.show()
        }


        tv_updpate.setOnClickListener {
            mUpdateDialog.show()
        }

    }

    /**
     *  底部弹出
     */
    private fun initBottomDialog() {
        mBottomDialog = JAlertDialog.Builder(this)
                .setContentView(R.layout.dialog) // 设置布局,可以是 View
                .setCancelable(false)
                .setHasAnimation(true) //是否拥有动画,默认true,
//                .setAnimation() // 设置动画,会覆盖默认动画
                .setText(R.id.btn_left, "test1") // 设置空间文本,如果有多个,则调用多次即可
                .setFromBottom() // 是否是从底部弹出,具体效果可以自己试试,感受更明显
                .setFullWidth() // 宽度铺满屏幕
//                .setWidthAndHeight() // 可以指定宽高(如果升级APP提示弹框等...)
                .setOnClick(R.id.btn_left) //第一个点击的 View
                .setOnClick(R.id.btn_right) // 第二个点击的 View
                // 如果设置了点击 View,则需要下面这个方法回调, 注意 position 是从 0 开始的
                .setOnJAlertDialogCLickListener(OnJAlertDialogClickListener { view, position ->
                    if (mBottomDialog.isShowing) {
                        mBottomDialog.dismiss()
                    }
                    when (position) {
                    // 这个顺序,和上面添加点击 View 是一直的
                        0 -> Toast.makeText(this@MainActivity, "点击左边按钮", Toast.LENGTH_SHORT).show()
                        1 -> Toast.makeText(this@MainActivity, "点击右边按钮", Toast.LENGTH_SHORT).show()
                    }
                }).create()


    }


    /**
     * 中间弹出,类似升级APP提示框
     */
    private fun initUpdateDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_update_app, null);
        // 彩蛋一枚,自定义 ArcBackgroundDrawable 解决底部 TextViev 设置背景没有圆角问题
        // 当然直接写xml也可以,只是本例中多了一个圆弧效果,看起来更 cool, 哈哈,不喜欢,你来打我啊!@!
        val tv_bottom_update = view.findViewById<TextView>(R.id.tv_bottom_update);
        tv_bottom_update.background = ArcBackgroundDrawable()
        mUpdateDialog = JAlertDialog.Builder(this)
                .setAnimation(R.style.UpdateAnimation)
                .setCancelable(false)
                .setContentView(view)
                .setOnClick(R.id.iv_close_update)
                .setOnClick(R.id.tv_bottom_update)
                .setOnJAlertDialogCLickListener { view, position ->

                    when (position) {
                        0 -> { // 关闭
                            mUpdateDialog.dismiss()
                        }
                        1 -> { // 开始下载
                            mUpdateDialog.dismiss()
                            // TODO
                        }

                    }
                }
                .create()
    }

}
