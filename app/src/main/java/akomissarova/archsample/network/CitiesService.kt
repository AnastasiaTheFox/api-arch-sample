package akomissarova.archsample.network

import akomissarova.archsample.model.City
import retrofit2.Call

interface CitiesService {

    fun getCities() : Call<List<City>>

}
