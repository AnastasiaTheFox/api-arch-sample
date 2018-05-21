package akomissarova.archsample.utils

import akomissarova.archsample.model.City

object TestCities {
    public fun getCities(): List<City> {
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
