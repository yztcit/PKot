package com.nttn.pkot

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PathUtils
import com.nttn.pkot.data.room.PkotDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.File

class CuzApplication : Application() {

    companion object {
        lateinit var sApplication: CuzApplication
        private lateinit var sDataBase: PkotDatabase
        suspend fun obtainDB(): PkotDatabase {
            return if (this::sDataBase.isInitialized) {
                sDataBase
            } else GlobalScope.async {
                sDataBase = PkotDatabase.aDatabase
                return@async sDataBase
            }.await()
        }
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this
        registerActivityLifecycleCallbacks(CuzLifecycle())
        initLog()
        GlobalHelper.generateWatermark(getString(R.string.app_name))
    }

    private fun initLog() {
        LogUtils.getConfig()
            .setGlobalTag("PKotLog")
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