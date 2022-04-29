package com.nttn.pkot

import com.nttn.baselib.BaseApplication
import com.nttn.pkot.data.room.PkotDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class CuzApplication : BaseApplication() {

    companion object {
        private lateinit var sDataBase: PkotDatabase
        suspend fun obtainDB(): PkotDatabase {
            return if (this::sDataBase.isInitialized) {
                sDataBase
            } else GlobalScope.async {
                sDataBase = PkotDatabase.initDatabase()
                return@async sDataBase
            }.await()
        }
    }

    override fun watermarkStr(): String {
        return getString(R.string.app_name)
    }
}