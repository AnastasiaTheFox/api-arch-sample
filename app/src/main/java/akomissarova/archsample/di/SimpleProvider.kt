package akomissarova.archsample.di

import akomissarova.archsample.repository.BasicCitiesRepository
import akomissarova.archsample.repository.CitiesRepository
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider


object SimpleProvider {

    fun createCitiesViewModelFactory(): ViewModelProvider.Factory {
        return CitiesViewModelFactory(getCitiesRepository())
    }

    private fun getCitiesRepository(): BasicCitiesRepository {
        return CitiesRepository()
    }

}
