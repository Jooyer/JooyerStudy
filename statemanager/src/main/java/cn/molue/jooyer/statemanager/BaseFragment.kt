package cn.molue.jooyer.statemanager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.molue.jooyer.statemanager.state.OnRetryListener
import cn.molue.jooyer.statemanager.state.StatusManager

/**
 * Desc: Fragment 基类
 * Author: Jooyer
 * Date: 2018-08-04
 * Time: 21:23
 */
abstract class BaseFragment: Fragment(), OnRetryListener {

    /**
     * 请求网络异常等界面管理
     */
    var mStatusManager: StatusManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initStatusManager(inflater, container, savedInstanceState)
    }


    abstract fun getLayoutId(): Int

    abstract fun initializedViews(savedInstanceState: Bundle?, contentView: View)

    /**
     * 此函数开始数据加载的操作，且仅调用一次
     * 主要是加载动画,初始化展示数据的布局
     */
    private fun initStatusManager(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (0 != getLayoutId()) {
            val contentView = inflater.inflate(getLayoutId(), container, false)
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
        mStatusManager = StatusManager.newBuilder(contentView.context)
                .contentView(contentView)
                .loadingView(R.layout.widget_progress_bar)
                .emptyDataView(R.layout.widget_empty_page)
                .netWorkErrorView(R.layout.widget_nonetwork_page)
                .errorView(R.layout.widget_error_page)
                .retryViewId(R.id.tv_retry_status)
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

    }

}