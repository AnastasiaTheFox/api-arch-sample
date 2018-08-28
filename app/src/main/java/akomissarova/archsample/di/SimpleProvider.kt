package akomissarova.archsample.di

import akomissarova.archsample.database.UrbanAreaDao
import akomissarova.archsample.model.UrbanArea
import akomissarova.archsample.network.CitiesService
import akomissarova.archsample.repository.BasicCitiesRepository
import akomissarova.archsample.repository.CitiesRepository
import android.arch.lifecycle.ViewModelProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object SimpleProvider {

    private val API_URL = "https://api.teleport.org/api/"
    private val retrofit by lazy {
        buildRetrofit()
    }

    fun createCitiesViewModelFactory(): ViewModelProvider.Factory {
        return CitiesViewModelFactory(getCitiesRepository())
    }

    private fun getCitiesRepository(): BasicCitiesRepository {
        return CitiesRepository(createCitiesService(), object: UrbanAreaDao {
            override fun saveCities(cities: List<UrbanArea>) {
                //todo temporary stub
            }
        })
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
