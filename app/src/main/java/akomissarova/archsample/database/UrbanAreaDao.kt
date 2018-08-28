package akomissarova.archsample.database

import akomissarova.archsample.model.UrbanArea
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert

@Dao
interface UrbanAreaDao {
    @Insert
    fun saveCities(cities: List<UrbanArea>)
}
