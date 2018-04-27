package akomissarova.archsample.repository

import akomissarova.archsample.model.City
import android.arch.lifecycle.LiveData

interface BasicCitiesRepository {

    fun getCitiesList(): LiveData<List<City>>

}
