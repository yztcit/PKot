package com.nttn.pkot

import com.nttn.baselib.BaseApplication
import com.nttn.pkot.data.room.PkotDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CuzApplication : BaseApplication() {

    companion object {
        private lateinit var sDataBase: PkotDatabase
        suspend fun obtainDB(): PkotDatabase {
            return if (this::sDataBase.isInitialized) {
                sDataBase
            } else withContext(Dispatchers.Default) {
                sDataBase = PkotDatabase.initDatabase()
                return@withContext sDataBase
            }
        }
    }

    override fun watermarkStr(): String {
        return getString(R.string.app_name)
    }
}