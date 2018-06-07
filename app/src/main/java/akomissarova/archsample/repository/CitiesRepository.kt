package akomissarova.archsample.repository

import akomissarova.archsample.FetchError
import akomissarova.archsample.model.UrbanArea
import akomissarova.archsample.network.CitiesService
import akomissarova.archsample.utils.monads.Either
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking

class CitiesRepository(private val service: CitiesService) : BasicCitiesRepository {

    private var citiesData: MutableLiveData<List<UrbanArea>>? = null
    private var citiesDataMonad: MutableLiveData<Either<FetchError, List<UrbanArea>>>? = null

    private fun initCitiesDataMonad(): MutableLiveData<Either<FetchError, List<UrbanArea>>> {
        val data = MutableLiveData<Either<FetchError, List<UrbanArea>>>()
        runBlocking {
            async {
                getCitiesAsyncMonad()
            }.await()
                    .let {
                        data.value = it
                    }
        }
        return data
    }

    private fun initCitiesData(): MutableLiveData<List<UrbanArea>> {
        val data = MutableLiveData<List<UrbanArea>>()
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

    override fun getCitiesList(): LiveData<List<UrbanArea>> {
        if (citiesData == null) {
            citiesData = initCitiesData()
        }
        return citiesData!!
    }

    override fun getCitiesListMonad(): LiveData<Either<FetchError, List<UrbanArea>>> {
        if (citiesDataMonad == null) {
            citiesDataMonad = initCitiesDataMonad()
        }
        return citiesDataMonad!!
    }

    fun getCitiesAsync(): List<UrbanArea> {
        val response = service.getCities().execute()
        return response.body()?.links?.list ?: emptyList<UrbanArea>()
    }

    private fun getCitiesAsyncMonad(): Either<FetchError, List<UrbanArea>> {
        val response = service.getCities().execute()
        response.body()?.links?.list?.let {
            return EitherRight(it)
        }
        response.errorBody()?.let {
            return EitherLeft(FetchError())
        }
        return EitherLeft(FetchError())
    }
}

typealias EitherRight = Either.Right<FetchError, List<UrbanArea>>
typealias EitherLeft = Either.Left<FetchError, List<UrbanArea>>
