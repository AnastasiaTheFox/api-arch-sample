package akomissarova.archsample.repository

import akomissarova.archsample.FetchError
import akomissarova.archsample.database.UrbanAreaDao
import akomissarova.archsample.model.UrbanArea
import akomissarova.archsample.network.CitiesService
import akomissarova.archsample.utils.monads.Either
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking

class CitiesRepository(private val service: CitiesService,
                       private val citiesDao: UrbanAreaDao) : BasicCitiesRepository {

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

    override fun getCitiesListMonad(): LiveData<Either<FetchError, List<UrbanArea>>> {
        if (citiesDataMonad == null) {
            citiesDataMonad = initCitiesDataMonad()
        }
        return citiesDataMonad!!
    }

    private fun getCitiesAsyncMonad(): Either<FetchError, List<UrbanArea>> {
        try {
            val response = service.getCities().execute()
            response?.body()?.links?.list?.let {
                citiesDao.saveCities(it)
                return EitherRight(it)
            }
            response?.errorBody()?.let {
                return EitherLeft(FetchError())
            }
        } catch (e: Exception) {
        }
        return EitherLeft(FetchError())
    }
}

typealias EitherRight = Either.Right<FetchError, List<UrbanArea>>
typealias EitherLeft = Either.Left<FetchError, List<UrbanArea>>
