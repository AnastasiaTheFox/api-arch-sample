package akomissarova.archsample

import akomissarova.archsample.ui.CitiesListFragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment()
    }

    private fun addFragment() {
        if (supportFragmentManager.findFragmentByTag(CitiesListFragment.TAG) == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, CitiesListFragment.newInstance(), CitiesListFragment.TAG)
                    .commit()
        }
    }

    override fun onResume() {
        super.onResume()
    }
}
