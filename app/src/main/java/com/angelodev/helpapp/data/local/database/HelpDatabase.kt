package com.angelodev.helpapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.angelodev.helpapp.data.local.dao.CircuitDao
import com.angelodev.helpapp.data.local.entity.CircuitEntity

@Database(entities = [CircuitEntity::class], version = 2, exportSchema = false)
abstract class HelpDatabase : RoomDatabase() {

    abstract fun circuitDao(): CircuitDao

    companion object {
        @Volatile
        private var INSTANCE: HelpDatabase? = null

        fun getInstance(context: Context): HelpDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HelpDatabase::class.java,
                    "HelpDatabase"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
