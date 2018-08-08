package cn.molue.jooyer.statemanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import cn.molue.jooyer.statemanager.state.OnRetryListener
import cn.molue.jooyer.statemanager.state.StatusManager

/**
 * Desc: Activity 基类
 * Author: Jooyer
 * Date: 2018-08-04
 * Time: 21:22
 */
abstract class BaseActivity :AppCompatActivity(), OnRetryListener {
    /**
     * 请求网络异常等界面管理
     */
    var mStatusManager: StatusManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (0 != getLayoutId()) {
            setContentView(initStatusManager(savedInstanceState))
        }
    }

    abstract fun getLayoutId(): Int

    /**
     * 初始化 View
     */
    abstract fun initializedViews(savedInstanceState: Bundle?, contentView: View)


    private fun initStatusManager(savedInstanceState: Bundle?): View {
        if (0 != getLayoutId()) {
            val contentView = LayoutInflater.from(this)
                    .inflate(getLayoutId(), null)
            initializedViews(savedInstanceState, contentView)
            return if (useStatusManager()) {
                initialized(contentView)
            } else {
                contentView.visibility = View.VISIBLE
                contentView
            }
        }
        throw IllegalStateException("getLayoutId() 必须调用,且返回正常的布局ID")
    }


    private fun initialized(contentView: View): View {
        mStatusManager = StatusManager.newBuilder(this)
                .contentView(contentView)
                .loadingView(R.layout.widget_progress_bar)
                .emptyDataView(R.layout.widget_empty_page)
                .netWorkErrorView(R.layout.widget_nonetwork_page)
                .errorView(R.layout.widget_error_page)
                .retryViewId(R.id.tv_retry_status)  // 注意以上布局中如果有重试ID,则必须一样,ID名称随意,记得这里填写正确
                .onRetryListener(this)
                .build()
        mStatusManager?.showLoading()
        return mStatusManager?.getRootLayout()!!
    }


    /**
     *  是否使用视图布局管理器,默认不使用
     */
    open fun useStatusManager(): Boolean {
        return false
    }

    /**
     * 点击视图中重试按钮
     */
    override fun onRetry() {
        Toast.makeText(this,"如果需要点击重试,则重写 onRetry() 方法",
                Toast.LENGTH_SHORT).show()
    }

}