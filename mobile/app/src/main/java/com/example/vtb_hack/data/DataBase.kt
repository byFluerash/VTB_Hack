package com.example.vtb_hack.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CarDB::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DataBase : RoomDatabase() {

    abstract fun carDAO(): CarDAO

    companion object {
        private var instance: DataBase? = null

        fun initDataBase(context: Context) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                DataBase::class.java,
                "car"
            ).build()

            instance = db
        }

        fun getDataBase() = instance!!
    }
}