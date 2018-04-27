package akomissarova.archsample.repository

import akomissarova.archsample.model.City
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

class CitiesRepository: BasicCitiesRepository {

    override fun getCitiesList(): LiveData<List<City>> {
        val citiesData = MutableLiveData<List<City>>()
        citiesData.value = getCities()
        return citiesData
    }

    private fun getCities(): List<City> {
        return listOf<City>(
                City("Madrid"),
                City("Berlin"),
                City("New York"),
                City("Ottawa"),
                City("Sydney"),
                City("London")
        )
    }

}
