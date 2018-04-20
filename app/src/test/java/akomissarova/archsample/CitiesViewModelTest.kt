package akomissarova.archsample

import akomissarova.archsample.model.City
import akomissarova.archsample.viewmodel.CitiesViewModel
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyListOf


class CitiesViewModelTest {

    @Rule
    @JvmField
    public var instantExecutor = InstantTaskExecutorRule()
    lateinit var viewModel: CitiesViewModel

    @Before
    fun setup() {
        viewModel = CitiesViewModel()
    }

    @After
    fun teardown() {

    }

    @Test
    fun returnsCitiesListLiveData_onObserver() {
        val observer : Observer<List<City>> = mock()
        viewModel.getList().observeForever(observer)

        verify(observer).onChanged(anyList())
    }
}
