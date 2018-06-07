package akomissarova.archsample.viewmodel

import akomissarova.archsample.FetchError
import akomissarova.archsample.model.UrbanArea
import akomissarova.archsample.repository.BasicCitiesRepository
import akomissarova.archsample.utils.monads.Either
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel

class CitiesViewModel(private val repository: BasicCitiesRepository) : ViewModel() {

    private var citiesDataMonad: LiveData<Either<FetchError, List<UrbanArea>>>? = null

    fun getListMonad(): LiveData<Either<FetchError, List<UrbanArea>>> {
        if (citiesDataMonad == null) {
            citiesDataMonad = Transformations.map(getCitiesMonad(), { cities ->
                cities
            })
        }
        return citiesDataMonad!!
    }

    private fun getCitiesMonad(): LiveData<Either<FetchError, List<UrbanArea>>> {
        return repository.getCitiesListMonad()
    }

}
