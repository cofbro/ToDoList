package com.example.todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Matters::class,Info::class],
    version = 1,
    exportSchema = false
)
abstract class ModelDataBase: RoomDatabase() {
    //获取一系列数据库访问的接口实现类
    abstract fun modelDao(): ModelDao

    companion object {
        @Volatile
        private var INSTANCE: ModelDataBase? = null
        fun getInstance(context: Context): ModelDataBase {
            if (INSTANCE != null) {
                return INSTANCE!!
            }
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        ModelDataBase::class.java,
                        "album_db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}