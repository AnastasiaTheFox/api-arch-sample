package akomissarova.archsample.viewmodel

import akomissarova.archsample.model.City
import akomissarova.archsample.repository.BasicCitiesRepository
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel

class CitiesViewModel(private val repository: BasicCitiesRepository) : ViewModel() {

    val citiesData: LiveData<List<City>> by lazy {
        Transformations.map(getCities(), { cities ->
            cities
        })
    }

    fun getList(): LiveData<List<City>> {
        return citiesData
    }

    private fun getCities(): LiveData<List<City>> {
        return repository.getCitiesList()
    }

}
