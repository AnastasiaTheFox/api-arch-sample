package akomissarova.archsample.ui

import akomissarova.archsample.model.UrbanArea
import akomissarova.archsample.R
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CitiesAdapter : RecyclerView.Adapter<CityViewHolder>() {

    private var items = listOf<UrbanArea>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): UrbanArea {
        return items.get(position)
    }

    fun setList(cities: List<UrbanArea>) {
        items = cities
        notifyDataSetChanged()
    }

}

class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var nameField: TextView

    init {
        nameField = itemView.findViewById(R.id.cityName)
    }

    fun bind(item: UrbanArea) {
        nameField.text = item.name
    }

}
