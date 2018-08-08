package cn.molue.jooyer.statemanager.state

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewStub


/**
 * Desc: 视图管理器
 * Author: Jooyer
 * Date: 2018-07-30
 * Time: 11:46
 */
class StatusManager(builder: Builder) {

    var mContext: Context
    var mNetworkErrorVs: ViewStub? = null
    var mNetworkErrorView:View? = null
    var mNetWorkErrorRetryViewId: Int = 0
    var mEmptyDataVs: ViewStub? = null
    var mEmptyDataView:View? = null
    var mEmptyDataRetryViewId: Int = 0
    var mErrorVs: ViewStub? = null
    var mErrorView:View? = null
    var mErrorRetryViewId: Int = 0
    var mLoadingLayoutResId: Int = 0
    var mContentLayoutResId: Int = 0
    var mRetryViewId: Int = 0
    var mContentLayoutView: View? = null
    var mRootFrameLayout: RootStatusLayout? = null


    /**
     * 显示loading
     */
    fun showLoading() {
        mRootFrameLayout?.showLoading()
    }

    /**
     * 显示内容
     */
    fun showContent() {
        mRootFrameLayout?.showContent()
    }

    /**
     * 显示空数据
     */
    fun showEmptyData() {
        mRootFrameLayout?.showEmptyData()
    }

    /**
     * 显示网络异常
     */
    fun showNetWorkError() {
        mRootFrameLayout?.showNetWorkError()
    }

    /**
     * 显示异常
     */
    fun showError() {
        mRootFrameLayout?.showError()
    }

    /**
     * 得到root 布局
     */
    fun getRootLayout(): View {
        return mRootFrameLayout!!
    }


    class Builder(val context: Context) {
        var loadingLayoutResId: Int = 0
        var contentLayoutResId: Int = 0
        var contentLayoutView: View? = null
        var netWorkErrorVs: ViewStub? = null
        var netWorkErrorRetryViewId: Int = 0
        var emptyDataVs: ViewStub? = null
        var emptyDataRetryViewId: Int = 0
        var errorVs: ViewStub? = null
        var errorRetryViewId: Int = 0
        var retryViewId: Int = 0
     //   var onShowHideViewListener: OnShowOrHideViewListener? = null
        var onRetryListener: OnRetryListener? = null

        fun loadingView(@LayoutRes loadingLayoutResId: Int): Builder {
            this.loadingLayoutResId = loadingLayoutResId
            return this
        }

        fun netWorkErrorView(@LayoutRes newWorkErrorId: Int): Builder {
            netWorkErrorVs = ViewStub(context)
            netWorkErrorVs!!.layoutResource = newWorkErrorId
            return this
        }

        fun emptyDataView(@LayoutRes noDataViewId: Int): Builder {
            emptyDataVs = ViewStub(context)
            emptyDataVs!!.layoutResource = noDataViewId
            return this
        }

        fun errorView(@LayoutRes errorViewId: Int): Builder {
            errorVs = ViewStub(context)
            errorVs!!.layoutResource = errorViewId
            return this
        }

        fun contentView(contentLayoutView: View): Builder {
            this.contentLayoutView = contentLayoutView
            return this
        }

        fun contentViewResId(@LayoutRes contentLayoutResId: Int): Builder {
            this.contentLayoutResId = contentLayoutResId
            return this
        }

        fun netWorkErrorRetryViewId(netWorkErrorRetryViewId: Int): Builder {
            this.netWorkErrorRetryViewId = netWorkErrorRetryViewId
            return this
        }

        fun emptyDataRetryViewId(emptyDataRetryViewId: Int): Builder {
            this.emptyDataRetryViewId = emptyDataRetryViewId
            return this
        }

        fun errorRetryViewId(errorRetryViewId: Int): Builder {
            this.errorRetryViewId = errorRetryViewId
            return this
        }

        fun retryViewId(retryViewId: Int): Builder {
            this.retryViewId = retryViewId
            return this
        }

//        fun onShowHideViewListener(onShowHideViewListener: OnShowOrHideViewListener): Builder {
//            this.onShowHideViewListener = onShowHideViewListener
//            return this
//        }

        fun onRetryListener(onRetryListener: OnRetryListener): Builder {
            this.onRetryListener = onRetryListener
            return this
        }

        fun build(): StatusManager {
            return StatusManager(this)
        }
    }


    companion object {
        fun newBuilder(context: Context): Builder {
            return Builder(context)
        }
    }

    init {
        mContext = builder.context
        mLoadingLayoutResId = builder.loadingLayoutResId
        mNetworkErrorVs = builder.netWorkErrorVs
        mNetWorkErrorRetryViewId = builder.netWorkErrorRetryViewId
        mEmptyDataVs = builder.emptyDataVs
        mEmptyDataRetryViewId = builder.emptyDataRetryViewId
        mErrorVs = builder.errorVs
        mErrorRetryViewId = builder.errorRetryViewId
        mContentLayoutResId = builder.contentLayoutResId
        mRetryViewId = builder.retryViewId
        mContentLayoutView = builder.contentLayoutView
        mRootFrameLayout = RootStatusLayout(mContext)
//        mRootFrameLayout!!.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT)
        mRootFrameLayout!!.setStatusManager(this)
        mRootFrameLayout!!.setOnRetryListener(builder.onRetryListener)
    }


}
