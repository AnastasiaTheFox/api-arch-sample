package akomissarova.archsample.repository

import akomissarova.archsample.FetchError
import akomissarova.archsample.model.UrbanArea
import akomissarova.archsample.utils.monads.Either
import android.arch.lifecycle.LiveData

interface BasicCitiesRepository {

    fun getCitiesList(): LiveData<List<UrbanArea>>
    fun getCitiesListMonad(): LiveData<Either<FetchError, List<UrbanArea>>>
}
