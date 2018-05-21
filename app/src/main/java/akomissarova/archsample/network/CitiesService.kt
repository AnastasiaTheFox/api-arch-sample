package akomissarova.archsample.network

import akomissarova.archsample.model.UrbanAreaResponse
import retrofit2.Call
import retrofit2.http.GET

interface CitiesService {

    @GET("urban_areas")
    fun getCities() : Call<UrbanAreaResponse>

}
