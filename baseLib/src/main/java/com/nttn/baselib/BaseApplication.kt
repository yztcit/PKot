package com.nttn.baselib

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import java.io.File

open class BaseApplication: Application() {

    open fun configTag() : String = "PKotLog"

    open fun watermarkStr() : String = "PKot"

    companion object {
        lateinit var sApplication: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this
        registerActivityLifecycleCallbacks(CuzLifecycle())
        initLog()
        GlobalHelper.generateWatermark(watermarkStr())
    }

    private fun initLog() {
        LogUtils.getConfig()
            .setGlobalTag(configTag())
            .setLogSwitch(AppUtils.isAppDebug())
            .setLog2FileSwitch(AppUtils.isAppDebug())
            .setBorderSwitch(true)
            .setDir(File(PathUtils.getCachePathExternalFirst(), "log"))
        LogUtils.i(LogUtils.getConfig().dir)
    }

    inner class CuzLifecycle : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            LogUtils.d(activity)
        }

        override fun onActivityStarted(activity: Activity) {
            LogUtils.d(activity)
        }

        override fun onActivityResumed(activity: Activity) {
            LogUtils.d(activity)
        }

        override fun onActivityPaused(activity: Activity) {
            LogUtils.d(activity)
        }

        override fun onActivityStopped(activity: Activity) {
            LogUtils.d(activity)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            LogUtils.d(activity)
        }

        override fun onActivityDestroyed(activity: Activity) {
            LogUtils.d(activity)
        }
    }
}