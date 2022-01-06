package com.nttn.pkot

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.room.Room
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import com.nttn.pkot.data.room.PkotDatabase
import java.io.File
import kotlin.concurrent.thread

class CuzApplication: Application() {
    companion object {
        lateinit var sDataBase: PkotDatabase
    }

    override fun onCreate() {
        super.onCreate()
        initDataBase()
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

    private fun initDataBase() {
        thread {
            sDataBase = Room.databaseBuilder(applicationContext, PkotDatabase::class.java, "pkot_data").build()
        }
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