package akomissarova.archsample.viewmodel

import akomissarova.archsample.model.City
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class CitiesViewModel: ViewModel() {

    val citiesData = MutableLiveData<List<City>>()

    fun getList(): LiveData<List<City>> {
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
