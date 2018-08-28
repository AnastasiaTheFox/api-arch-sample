package akomissarova.archsample.di

import akomissarova.archsample.database.LocationsDatabase
import akomissarova.archsample.network.CitiesService
import akomissarova.archsample.repository.BasicCitiesRepository
import akomissarova.archsample.repository.CitiesRepository
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object SimpleProvider {

    private val API_URL = "https://api.teleport.org/api/"
    private val retrofit by lazy {
        buildRetrofit()
    }

    fun createCitiesViewModelFactory(context: Context): ViewModelProvider.Factory {
        return CitiesViewModelFactory(getCitiesRepository(context))
    }

    private fun getCitiesRepository(context: Context): BasicCitiesRepository {
        return CitiesRepository(createCitiesService(), LocationsDatabase.getInstance(context).urbanAreaDao())
    }

    private fun createCitiesService(): CitiesService {
        return retrofit.create(CitiesService::class.java)
    }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}
