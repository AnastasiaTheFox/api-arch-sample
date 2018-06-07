package akomissarova.archsample

import akomissarova.archsample.model.Links
import akomissarova.archsample.model.UrbanArea
import akomissarova.archsample.model.UrbanAreaResponse
import akomissarova.archsample.network.CitiesService
import akomissarova.archsample.repository.CitiesRepository
import akomissarova.archsample.repository.EitherLeft
import akomissarova.archsample.repository.EitherRight
import akomissarova.archsample.utils.TestCities.getCities
import akomissarova.archsample.utils.monads.Either
import android.arch.core.executor.testing.InstantTaskExecutorRule
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
import retrofit2.Response
import retrofit2.mock.Calls

@RunWith(JUnit4::class)
class CitiesRepositoryTest {

    @Rule
    @JvmField
    var instantExecutor = InstantTaskExecutorRule()
    private lateinit var repository : CitiesRepository
    @Mock
    private lateinit var service : CitiesService

    @Before
    fun setup() {
        service = mock()
        repository = CitiesRepository(service)
    }

    @After
    fun teardown() {

    }

    @Test
    fun `getCities() returns empty list when error is received`() {

        val observer : Observer<List<UrbanArea>> = mock()
        whenever(service.getCities()).
                thenReturn(Calls.response(Response.error(404, ResponseBody.create(null, ""))))

        repository.getCitiesList().observeForever(observer)

        assertEquals(repository.getCitiesList().value, emptyList<UrbanArea>())
        verify(service).getCities()
    }

    @Test
    fun `getCities() returns list from service`() {

        val observer : Observer<Either<FetchError, List<UrbanArea>>> = mock()
        val content : Links = mock {
            on { list } doReturn getCities()
        }
        val response : UrbanAreaResponse = mock {
            on { links } doReturn content
        }

        whenever(service.getCities()).
                thenReturn(Calls.response(response))

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
        assertEquals(result, getCities())
        verify(service).getCities()
    }
}