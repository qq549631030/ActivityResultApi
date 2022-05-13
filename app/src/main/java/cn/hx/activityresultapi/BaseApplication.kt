package cn.hx.activityresultapi

import android.app.Application
import cn.hx.ara.ActivityResultManager

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ActivityResultManager.init(this)
    }
}