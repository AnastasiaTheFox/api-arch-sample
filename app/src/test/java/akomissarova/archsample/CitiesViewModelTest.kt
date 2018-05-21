package akomissarova.archsample

import akomissarova.archsample.model.City
import akomissarova.archsample.repository.BasicCitiesRepository
import akomissarova.archsample.utils.TestCities.getCities
import akomissarova.archsample.viewmodel.CitiesViewModel
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import junit.framework.Assert.assertNotSame
import junit.framework.Assert.assertSame
import kotlinx.coroutines.experimental.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`

@RunWith(JUnit4::class)
class CitiesViewModelTest {

    @Rule
    @JvmField
    var instantExecutor = InstantTaskExecutorRule()
    @Mock
    private lateinit var repository : BasicCitiesRepository
    private val defaultCities = getCities()
    private lateinit var viewModel: CitiesViewModel

    @Before
    fun setup() {
        repository = mock()
        viewModel = CitiesViewModel(repository)
    }

    @After
    fun teardown() {

    }

    @Test
    fun `getList() returns cities list liveData with correct cities when observer is added`() {
        val observer : Observer<List<City>> = mock()
        val citiesData = getMockCities()
        runBlocking {
            `when`(repository.getCitiesList()).thenReturn(citiesData)
            viewModel.getList().observeForever(observer)

            assertSame(viewModel.getList(), viewModel.getList())
            verify(repository).getCitiesList()
        }

        verify(observer).onChanged(defaultCities)
        assertNotSame(citiesData, viewModel.getList())
    }

    fun getMockCities(): LiveData<List<City>> {
        val citiesData = MutableLiveData<List<City>>()
        citiesData.value = defaultCities
        return citiesData
    }

}
