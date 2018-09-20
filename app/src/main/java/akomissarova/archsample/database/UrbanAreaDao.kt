package akomissarova.archsample.database

import akomissarova.archsample.model.UrbanArea
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface UrbanAreaDao {
    @Insert
    fun saveCities(cities: List<UrbanArea>)

    @Query("SELECT * from urbanArea ORDER BY name ASC")
    fun getCities(): LiveData<List<UrbanArea>>

    @Query("DELETE from urbanArea")
    fun clear()
}
