package akomissarova.archsample.viewmodel

import akomissarova.archsample.model.City
import akomissarova.archsample.repository.BasicCitiesRepository
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel

class CitiesViewModel(private val repository: BasicCitiesRepository) : ViewModel() {

    private var citiesData: LiveData<List<City>>? = null

    fun getList(): LiveData<List<City>> {
        if (citiesData == null) {
            citiesData = Transformations.map(getCities(), { cities ->
                cities
            })
        }
        return citiesData!!
    }

    private fun getCities(): LiveData<List<City>> {
        return repository.getCitiesList()
    }

}
