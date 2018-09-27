package akomissarova.archsample

import akomissarova.archsample.database.UrbanAreaDao
import akomissarova.archsample.model.Links
import akomissarova.archsample.model.UrbanArea
import akomissarova.archsample.model.UrbanAreaResponse
import akomissarova.archsample.network.CitiesService
import akomissarova.archsample.repository.CitiesRepository
import akomissarova.archsample.repository.EitherRight
import akomissarova.archsample.utils.TestCities.getCities
import akomissarova.archsample.utils.TestCities.getNewCities
import akomissarova.archsample.utils.monads.Either
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.*
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response
import retrofit2.mock.Calls
import java.net.SocketException

@RunWith(JUnit4::class)
class CitiesRepositoryTest {

    @Rule
    @JvmField
    var instantExecutor = InstantTaskExecutorRule()
    private lateinit var repository : CitiesRepository
    @Mock
    private lateinit var service : CitiesService
    @Mock
    private lateinit var dao: UrbanAreaDao
    @Mock
    private lateinit var observer: Observer<Either<FetchError, List<UrbanArea>>>

    @Before
    fun setup() {
        service = mock()
        dao = mock()
        repository = CitiesRepository(service, dao)
        observer = mock()
    }

    @After
    fun teardown() {

    }

    @Test
    fun `getCities() returns error when error is received`() {

        service.mockReturnError(404)

        val daoLiveData = dao.getMockListLiveData()

        daoLiveData.value = null
        repository.observeList(observer)

        repository.verifyListResultError()

        verify(service).getCities()
    }

    @Test
    fun `getCities() returns an error when exception is thrown`() {
        val mockCall : Call<UrbanAreaResponse> = mock()

        whenever(service.getCities()).
                thenReturn(mockCall)

        val daoLiveData = dao.getMockListLiveData()
        daoLiveData.value = null

        whenever(mockCall.execute()).
                thenThrow(SocketException())

        repository.observeList(observer)
        repository.verifyListResultError()

        verify(service).getCities()
    }

    @Test
    fun `getCities() returns list from db updated from service if db empty`() {

        val allFinalResults = mutableListOf<Either<FetchError, List<UrbanArea>>>()
        val realObserver : Observer<Either<FetchError, List<UrbanArea>>> = Observer {
            allFinalResults.add(it!!)
        }
        val emptylocalCities = listOf<UrbanArea>()
        val daoLiveData = dao.getMockListLiveData()
        val cities = getCities()

        service.returnsMockCitieList(cities)

        daoLiveData.value = emptylocalCities

        repository.observeList(realObserver)
        //todo this is an assumtion about how the framework works
        daoLiveData.value = getCities()

        repository.verifyListResult(getCities())

        verify(service).getCities()
        dao.verifyRewriteCitiesList(cities)

        assertTrue(allFinalResults.size == 1)
    }

    @Test
    fun `getCities() returns list from db not updated from service if db not empty`() {

        val cities = getCities()
        val localCities = getNewCities()

        val daoLiveData = dao.getMockListLiveData()

        service.returnsMockCitieList(cities)

        daoLiveData.value = localCities

        repository.observeList(observer)
        repository.verifyListResult(getNewCities())

        verify(service, never()).getCities()
        dao.verifyNeverUpdatesList()
    }
}

private fun UrbanAreaDao.verifyNeverUpdatesList() {
    verify(this, never()).clear()
    verify(this, never()).saveCities(any())
    verify(this).getCities()
}

private fun UrbanAreaDao.verifyRewriteCitiesList(cities: List<UrbanArea>) {
    verify(this).clear()
    verify(this).saveCities(cities)
    verify(this).getCities()
}

private fun CitiesRepository.verifyListResult(cities: List<UrbanArea>) {
    assertTrue(getCitiesListMonad().value is EitherRight)
    var result: List<UrbanArea>? = null
    var error: FetchError? = null
    getCitiesListMonad().value!!.fold({
        error = it
    }, {
        result = it
    })

    assertNull(error)
    assertEquals(result, cities)
}

private fun CitiesService.returnsMockCitieList(cities: List<UrbanArea>) {
    val content : Links = mock {
        on { list } doReturn cities
    }
    val response : UrbanAreaResponse = mock {
        on { links } doReturn content
    }
    whenever(getCities()).
            thenReturn(Calls.response(response))
}

private fun CitiesRepository.verifyListResultError() {
    var result: List<UrbanArea>? = null
    var error: FetchError? = null
    getCitiesListMonad().value!!.fold({
        error = it
    }, {
        result = it
    })

    assertNull(result)
    assertNotNull(error)
}

private fun CitiesRepository.observeList(observer: Observer<Either<FetchError, List<UrbanArea>>>) {
    getCitiesListMonad().observeForever(observer)
}

private fun UrbanAreaDao.getMockListLiveData(): MutableLiveData<List<UrbanArea>> {
    val daoLiveData = MutableLiveData<List<UrbanArea>>()
    whenever(getCities()).
            thenReturn(daoLiveData)
    return daoLiveData
}

private fun CitiesService.mockReturnError(errorCode: Int) {
    whenever(getCities()).
            thenReturn(Calls.response(Response.error(errorCode, ResponseBody.create(null, ""))))

}
