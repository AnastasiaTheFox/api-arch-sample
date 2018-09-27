package akomissarova.archsample

import akomissarova.archsample.model.UrbanArea
import akomissarova.archsample.repository.BasicCitiesRepository
import akomissarova.archsample.utils.monads.Either
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
    fun `getList() returns cities list liveData with correct cities`() {
        val observer : Observer<Either<FetchError, List<UrbanArea>>> = mock()
        val citiesData = getMockLiveDataWithDefaultValue()
        runBlocking {
            `when`(repository.getCitiesListMonad()).thenReturn(citiesData)
            viewModel.getListMonad().observeForever(observer)

            assertSame(viewModel.getListMonad(), viewModel.getListMonad())
            verify(repository).getCitiesListMonad()
        }

        verify(observer).onChanged(citiesData.value)
        //live data from repository and from view model should be different
        assertNotSame(citiesData, viewModel.getListMonad())
    }

    fun getMockLiveDataWithDefaultValue(): LiveData<Either<FetchError, List<UrbanArea>>> {
        val citiesData = MutableLiveData<Either<FetchError, List<UrbanArea>>>()
        citiesData.value = Either.Right(defaultCities)
        return citiesData
    }

}
