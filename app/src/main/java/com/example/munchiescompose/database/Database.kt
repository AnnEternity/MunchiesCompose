package com.example.munchiescompose.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fooddelivery.network.RestaurantFilterCrossRef

@Database(
    entities = [
        RestaurantEntity::class,
        FilterEntity::class,
        RestaurantFilterCrossRef::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao

    companion object {
        fun getDatabase(applicationContext: Context): AppDatabase {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "AppDatabase"
            )
                .fallbackToDestructiveMigration()
                .build()
            return db
        }
    }

}
