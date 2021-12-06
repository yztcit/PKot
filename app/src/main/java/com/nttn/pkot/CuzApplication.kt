package com.nttn.pkot

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import com.nttn.pkot.BuildConfig
import java.io.File

class CuzApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(CuzLifecycle())
        initLog()
        GlobalHelper.generateWatermark(getString(R.string.app_name))
    }

    private fun initLog() {
        LogUtils.getConfig()
            .setGlobalTag("PKotLog")
            .setLogSwitch(BuildConfig.DEBUG)
            .setLog2FileSwitch(BuildConfig.DEBUG)
            .setBorderSwitch(true)
            .setDir(File(PathUtils.getCachePathExternalFirst(), "log"))
        LogUtils.i(LogUtils.getConfig().dir)
    }


    inner class CuzLifecycle: ActivityLifecycleCallbacks {
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