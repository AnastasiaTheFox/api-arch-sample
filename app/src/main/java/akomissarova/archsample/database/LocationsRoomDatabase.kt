package akomissarova.archsample.database

import akomissarova.archsample.model.UrbanArea
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(UrbanArea::class), version = 1)
abstract class LocationsDatabase: RoomDatabase() {
    abstract fun urbanAreaDao(): UrbanAreaDao

    companion object {
        private var INSTANCE: LocationsDatabase? = null

        fun getInstance(context: Context): LocationsDatabase? {
            if (INSTANCE == null) {
                synchronized(LocationsDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocationsDatabase::class.java, "locations.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}