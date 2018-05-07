package akomissarova.archsample

import akomissarova.archsample.model.City
import akomissarova.archsample.network.CitiesService
import akomissarova.archsample.repository.CitiesRepository
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
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
    fun getCitiesReturnsListFromService() {

        val observer : Observer<List<City>> = mock()
        whenever(service.getCities()).
                thenReturn(Calls.response(getCities()))

        repository.getCitiesList().observeForever(observer)

        assertEquals(repository.getCitiesList().value, getCities())
        verify(service).getCities()
    }

    private fun getCities(): List<City> {
        return listOf(
                City("Madrid"),
                City("Berlin"),
                City("New York"),
                City("Ottawa"),
                City("London")
        )
    }
}