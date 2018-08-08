package cn.molue.jooyer.statemanager.state

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Desc: 视图管理布局控件
 * Author: Jooyer
 * Date: 2018-07-30
 * Time: 11:23
 */
class RootStatusLayout(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?) : this(context, null, 0)

    /**
     * loading 加载id
     */
    val LAYOUT_LOADING_ID = 1

    /**
     * 内容id
     */
    val LAYOUT_CONTENT_ID = 2

    /**
     * 异常id
     */
    val LAYOUT_ERROR_ID = 3

    /**
     * 网络异常id
     */
    val LAYOUT_NETWORK_ERROR_ID = 4

    /**
     * 空数据id
     */
    val LAYOUT_EMPTY_ID = 5

    /**
     * 存放布局集合
     */
    private val mLayoutViews = SparseArray<View>()

    /**
     * 视图管理器
     */
    private var mStatusLayoutManager: StatusManager? = null

    /**
     * 不同视图的切换
     */
//    private var onShowHideViewListener: OnShowOrHideViewListener? = null

    /**
     * 点击重试按钮回调
     */
    private var onRetryListener: OnRetryListener? = null


    fun setStatusManager(manager: StatusManager) {
        mStatusLayoutManager = manager
        addAllLayoutViewsToRoot()
    }

    private fun addAllLayoutViewsToRoot() {
        val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)

        if (0 != mStatusLayoutManager?.mContentLayoutResId) {
            addLayoutResId(mStatusLayoutManager?.mContentLayoutResId!!, LAYOUT_CONTENT_ID, params)
        } else if (null != mStatusLayoutManager?.mContentLayoutView) {
            addLayoutView(mStatusLayoutManager?.mContentLayoutView!!, LAYOUT_CONTENT_ID, params)
        }

        if (0 != mStatusLayoutManager?.mLoadingLayoutResId) {
            addLayoutResId(mStatusLayoutManager?.mLoadingLayoutResId!!, LAYOUT_LOADING_ID, params)
        }

        if (null != mStatusLayoutManager?.mEmptyDataVs) {
            addView(mStatusLayoutManager?.mEmptyDataVs, params)
        }

        if (null != mStatusLayoutManager?.mErrorVs) {
            addView(mStatusLayoutManager?.mErrorVs, params)
        }

        if (null != mStatusLayoutManager?.mNetworkErrorVs) {
            addView(mStatusLayoutManager?.mNetworkErrorVs, params)
        }

    }

    private fun addLayoutView(layoutView: View, layoutId: Int, param: ViewGroup.LayoutParams) {
        mLayoutViews.put(layoutId, layoutView)
        addView(layoutView, param)
    }

    private fun addLayoutResId(@LayoutRes layoutResId: Int, layoutId: Int, param: ViewGroup.LayoutParams) {
        val view: View = LayoutInflater.from(context)
                .inflate(layoutResId, null)
        mLayoutViews.put(layoutId, view)
        if (LAYOUT_LOADING_ID == layoutId) {
            view.visibility = View.GONE
        }
        addView(view, param)
    }

    /**
     * 显示loading
     */
    fun showLoading() {
        if (mLayoutViews.get(LAYOUT_LOADING_ID) != null)
            showHideViewById(LAYOUT_LOADING_ID)
    }

    /**
     * 显示内容
     */
    fun showContent() {
        if (mLayoutViews.get(LAYOUT_CONTENT_ID) != null)
            showHideViewById(LAYOUT_CONTENT_ID)
    }

    /**
     * 显示空数据
     */
    fun showEmptyData() {
        if (inflateLayout(LAYOUT_EMPTY_ID))
            showHideViewById(LAYOUT_EMPTY_ID)
    }

    /**
     * 显示网络异常
     */
    fun showNetWorkError() {
        if (inflateLayout(LAYOUT_NETWORK_ERROR_ID))
            showHideViewById(LAYOUT_NETWORK_ERROR_ID)
    }

    /**
     * 显示异常
     */
    fun showError() {
        if (inflateLayout(LAYOUT_ERROR_ID))
            showHideViewById(LAYOUT_ERROR_ID)
    }

    private fun showHideViewById(layoutId: Int) {
        for (i in 0 until mLayoutViews.size()) {
            val key = mLayoutViews.keyAt(i)
            val value = mLayoutViews[key]

            // 显示该 View
            if (layoutId == key) {
                value.visibility = View.VISIBLE
            } else {
                if (View.GONE != value.visibility) {
                    value.visibility = View.GONE
                }
            }
        }
    }

    fun setOnRetryListener(listener: OnRetryListener?) {
        onRetryListener = listener
    }

    /**
     * 加载 StubView
     */
    private fun inflateLayout(layoutId: Int): Boolean {
        var isShow = true
        when (layoutId) {
            LAYOUT_NETWORK_ERROR_ID -> {
                isShow = when {
                    null != mStatusLayoutManager?.mNetworkErrorView -> {
                        retryLoad(mStatusLayoutManager?.mNetworkErrorView!!, mStatusLayoutManager?.mNetWorkErrorRetryViewId!!)
                        mLayoutViews.put(layoutId, mStatusLayoutManager?.mNetworkErrorView!!)
                        return true
                    }
                    null != mStatusLayoutManager?.mNetworkErrorVs -> {
                        val view: View = mStatusLayoutManager?.mNetworkErrorVs!!.inflate()
                        mStatusLayoutManager?.mNetworkErrorView = view
                        retryLoad(view, mStatusLayoutManager?.mNetWorkErrorRetryViewId!!)
                        mLayoutViews.put(layoutId, view)
                        true
                    }
                    else -> false
                }
            }
            LAYOUT_ERROR_ID -> {
                isShow = when {
                    null != mStatusLayoutManager?.mErrorView -> {
                        retryLoad(mStatusLayoutManager?.mErrorView!!, mStatusLayoutManager?.mErrorRetryViewId!!)
                        mLayoutViews.put(layoutId, mStatusLayoutManager?.mErrorView!!)
                        return true
                    }
                    null != mStatusLayoutManager?.mErrorVs -> {
                        val view: View = mStatusLayoutManager?.mErrorVs!!.inflate()
                        mStatusLayoutManager?.mErrorView = view
                        retryLoad(view, mStatusLayoutManager?.mErrorRetryViewId!!)
                        mLayoutViews.put(layoutId, view)
                        true
                    }
                    else -> false
                }
            }
            LAYOUT_EMPTY_ID -> {
                isShow = when {
                    null != mStatusLayoutManager?.mEmptyDataView -> {
                        retryLoad(mStatusLayoutManager?.mEmptyDataView!!, mStatusLayoutManager?.mEmptyDataRetryViewId!!)
                        mLayoutViews.put(layoutId, mStatusLayoutManager?.mEmptyDataView!!)
                        return true
                    }
                    null != mStatusLayoutManager?.mEmptyDataVs -> {
                        val view: View = mStatusLayoutManager?.mEmptyDataVs!!.inflate()
                        mStatusLayoutManager?.mEmptyDataView = view
                        retryLoad(view, mStatusLayoutManager?.mEmptyDataRetryViewId!!)
                        mLayoutViews.put(layoutId, view)
                        true
                    }
                    else -> false
                }
            }
        }
        return isShow
    }

    /**
     *  加载重试按钮,并绑定监听
     */
    private fun retryLoad(view: View, layoutResId: Int) {
        val retryView: View? = view.findViewById(
                if (0 != mStatusLayoutManager?.mRetryViewId!!) {
                    mStatusLayoutManager?.mRetryViewId!!
                } else {
                    layoutResId
                }) ?: return
        retryView?.setOnClickListener {
            onRetryListener?.onRetry()
        }
    }


}
