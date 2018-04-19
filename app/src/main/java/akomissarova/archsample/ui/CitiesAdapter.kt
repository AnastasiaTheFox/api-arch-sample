package akomissarova.archsample.ui

import akomissarova.archsample.model.City
import akomissarova.archsample.R
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CitiesAdapter : RecyclerView.Adapter<CityViewHolder>() {

    private var items = listOf<City>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {

    }

    fun setList(cities: List<City>) {
        items = cities
        notifyDataSetChanged()
    }

}

class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}
