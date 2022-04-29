package com.nttn.pkot.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.blankj.utilcode.util.LogUtils
import com.nttn.baselib.BaseApplication

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(DateConvert::class)
abstract class PkotDatabase : RoomDatabase() {

    companion object {
        fun initDatabase(dbName: String = "pkot_data"): PkotDatabase =
            //如果databaseBuilder改为inMemoryDatabaseBuilder则创建一个内存数据库（进程销毁后，数据丢失）
            Room.databaseBuilder(
                BaseApplication.sApplication,
                PkotDatabase::class.java,
                dbName
            )
                //是否允许在主线程进行查询
                //.allowMainThreadQueries()
                //数据库创建和打开后的回调，可以重写其中的方法
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        LogUtils.d("create room db")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        LogUtils.d("open room db")
                    }

                    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                        super.onDestructiveMigration(db)
                        LogUtils.d("migration room db")
                    }
                })
                //数据库升级异常之后的回滚
                .fallbackToDestructiveMigration()
                .build()

    }

    abstract fun noteDao(): NoteDao
}