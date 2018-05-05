package akomissarova.archsample.di

import akomissarova.archsample.repository.BasicCitiesRepository
import akomissarova.archsample.viewmodel.CitiesViewModel
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class CitiesViewModelFactory(val repository: BasicCitiesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CitiesViewModel(repository) as T
    }

}
