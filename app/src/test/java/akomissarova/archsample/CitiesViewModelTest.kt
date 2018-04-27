package akomissarova.archsample

import akomissarova.archsample.model.City
import akomissarova.archsample.repository.BasicCitiesRepository
import akomissarova.archsample.viewmodel.CitiesViewModel
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import junit.framework.Assert.assertNotSame
import junit.framework.Assert.assertSame
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`


class CitiesViewModelTest {

    @Rule
    @JvmField
    var instantExecutor = InstantTaskExecutorRule()
    @Mock
    private lateinit var repository : BasicCitiesRepository
    private val defaultCities = getCities()
    lateinit var viewModel: CitiesViewModel

    @Before
    fun setup() {
        repository = mock()
        viewModel = CitiesViewModel(repository)
    }

    @After
    fun teardown() {

    }

    @Test
    fun returnsCitiesListLiveData_onObserver() {
        val observer : Observer<List<City>> = mock()
        val citiesData = getMockCities()
        `when`(repository.getCitiesList()).thenReturn(citiesData)
        viewModel.getList().observeForever(observer)

        assertSame(viewModel.getList(), viewModel.getList())
        verify(repository).getCitiesList()
        verify(observer).onChanged(defaultCities)
        assertNotSame(citiesData, viewModel.getList())
    }

    fun getMockCities(): LiveData<List<City>> {
        val citiesData = MutableLiveData<List<City>>()
        citiesData.value = defaultCities
        return citiesData
    }

    private fun getCities(): List<City> {
        return listOf(
                City("Madrid"),
                City("Berlin"),
                City("New York"),
                City("Ottawa"),
                City("Sydney"),
                City("London")
        )
    }
}
