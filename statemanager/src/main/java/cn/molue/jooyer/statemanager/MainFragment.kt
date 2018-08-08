package cn.molue.jooyer.statemanager

import android.os.Bundle
import android.view.View
import android.widget.TextView

/**
 * Desc:
 * Author: Jooyer
 * Date: 2018-08-05
 * Time: 21:14
 */
class MainFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initializedViews(savedInstanceState: Bundle?, contentView: View) {
        // 仅仅演示如何通过 findViewById 找到控件,其实 Kotlin 不用这么麻烦
        val tv_fragment = contentView.findViewById<TextView>(R.id.tv_fragment);
    }




}