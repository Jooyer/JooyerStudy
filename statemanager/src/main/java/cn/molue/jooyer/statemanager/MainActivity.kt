package cn.molue.jooyer.statemanager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initializedViews(savedInstanceState: Bundle?, contentView: View) {
        val tv_main_activity = contentView.findViewById<TextView>(R.id.tv_main_activity)
    }

    // 如果使用 StateManager 必须重写下面方法,且返回 true
    // 如果返回 true 则会显示加载的 loading
    override fun useStatusManager(): Boolean {
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.state_menu, menu)
        return true
    }

    // 下面展示了显示各个状态的方式
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_empty_page -> {
                mStatusManager?.showEmptyData()
                return true
            }
            R.id.option_error_page -> {
                mStatusManager?.showError()
                return true
            }
            R.id.option_loading_page -> {
                mStatusManager?.showLoading()
                return true
            }
            R.id.option_network_page -> {
                mStatusManager?.showNetWorkError()
                return true
            }
            R.id.option_content_page -> {
                mStatusManager?.showContent()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
                .add(R.id.fl_container,MainFragment())
                .commit()
    }


}
