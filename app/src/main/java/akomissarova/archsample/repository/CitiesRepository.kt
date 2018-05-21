package akomissarova.archsample.repository

import akomissarova.archsample.model.City
import akomissarova.archsample.network.CitiesService
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking

class CitiesRepository(private val service: CitiesService) : BasicCitiesRepository {

    private var citiesData: MutableLiveData<List<City>>? = null

    private fun initCitiesData(): MutableLiveData<List<City>> {
        val data = MutableLiveData<List<City>>()
        runBlocking {
            async {
                getCitiesAsync()
            }.await()
                    .let {
                        data.value = it
                    }
        }
        return data
    }

    override fun getCitiesList(): LiveData<List<City>> {
        if (citiesData == null) {
            citiesData = initCitiesData()
        }
        return citiesData!!
    }

    fun getCitiesAsync(): List<City>? {
        val response = service.getCities().execute()
        return response.body()
    }

}
