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

    @Before
    fun setup() {
        service = mock()
        dao = mock()
        repository = CitiesRepository(service, dao)
    }

    @After
    fun teardown() {

    }

    @Test
    fun `getCities() returns error when error is received`() {

        val observer : Observer<Either<FetchError, List<UrbanArea>>> = mock()
        whenever(service.getCities()).
                thenReturn(Calls.response(Response.error(404, ResponseBody.create(null, ""))))

        repository.getCitiesListMonad().observeForever(observer)

        var result: List<UrbanArea>? = null
        var error: FetchError? = null
        repository.getCitiesListMonad().value!!.fold({
            error = it
        }, {
            result = it
        })

        assertNull(result)
        assertNotNull(error)
        verify(service).getCities()
    }

    @Test
    fun `getCities() returns an error when exception is thrown`() {
        val observer : Observer<Either<FetchError, List<UrbanArea>>> = mock()
        val mockCall : Call<UrbanAreaResponse> = mock()
        whenever(service.getCities()).
                thenReturn(mockCall)

        whenever(mockCall.execute()).
                thenThrow(SocketException())

        repository.getCitiesListMonad().observeForever(observer)

        var result: List<UrbanArea>? = null
        var error: FetchError? = null
        repository.getCitiesListMonad().value!!.fold({
            error = it
        }, {
            result = it
        })

        assertNull(result)
        assertNotNull(error)
        verify(service).getCities()
    }

    @Test
    fun `getCities() returns list from db updated from service if db empty`() {

        val observer : Observer<Either<FetchError, List<UrbanArea>>> = mock()
        val cities = getCities()
        val localSetCities = listOf<UrbanArea>()
        val content : Links = mock {
            on { list } doReturn cities
        }
        val response : UrbanAreaResponse = mock {
            on { links } doReturn content
        }
        val daoLiveData = MutableLiveData<List<UrbanArea>>()

        whenever(service.getCities()).
                thenReturn(Calls.response(response))
        whenever(dao.getCities()).
                thenReturn(localSetCities)
        daoLiveData.value = localSetCities

        repository.getCitiesListMonad().observeForever(observer)

        assertTrue(repository.getCitiesListMonad().value is EitherRight)

        var result: List<UrbanArea>? = null
        var error: FetchError? = null
        repository.getCitiesListMonad().value!!.fold({
            error = it
        }, {
            result = it
        })

        assertNull(error)
        verify(service).getCities()
        verify(dao).clear()
        verify(dao).saveCities(cities)
        verify(dao, times(2)).getCities()
    }

    @Test
    fun `getCities() returns list from db not updated from service if db not empty`() {

        val observer : Observer<Either<FetchError, List<UrbanArea>>> = mock()
        val cities = getCities()
        val localSetCities = getNewCities()
        val content : Links = mock {
            on { list } doReturn cities
        }
        val response : UrbanAreaResponse = mock {
            on { links } doReturn content
        }
        val daoLiveData = MutableLiveData<List<UrbanArea>>()

        whenever(service.getCities()).
                thenReturn(Calls.response(response))
        whenever(dao.getCities()).
                thenReturn(localSetCities)
        daoLiveData.value = localSetCities

        repository.getCitiesListMonad().observeForever(observer)

        assertTrue(repository.getCitiesListMonad().value is EitherRight)

        var result: List<UrbanArea>? = null
        var error: FetchError? = null
        repository.getCitiesListMonad().value!!.fold({
            error = it
        }, {
            result = it
        })

        assertNull(error)
        assertEquals(result, getNewCities())
        verify(service, never()).getCities()
        verify(dao, never()).clear()
        verify(dao, never()).saveCities(cities)
        verify(dao).getCities()
    }
}