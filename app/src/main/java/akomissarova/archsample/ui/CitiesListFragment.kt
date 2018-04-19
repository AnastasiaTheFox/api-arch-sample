package akomissarova.archsample.ui


import akomissarova.archsample.model.City
import akomissarova.archsample.R
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_cities_list.*


class CitiesListFragment : Fragment() {

    private val adapter = CitiesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cities_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        citiesList.adapter = adapter
        adapter.setList(getCities())
    }

    private fun getCities(): List<City> {
        return listOf<City>(
                City("Madrid"),
                City("Berlin"),
                City("New York"),
                City("Ottawa")
        )
    }

    companion object {
        val TAG = CitiesListFragment::class.toString()
        fun newInstance(): CitiesListFragment {
            val fragment = CitiesListFragment()
            return fragment
        }
    }

}
