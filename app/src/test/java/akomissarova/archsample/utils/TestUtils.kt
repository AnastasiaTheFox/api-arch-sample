package akomissarova.archsample.utils

import akomissarova.archsample.model.UrbanArea

object TestCities {
    fun getCities(): List<UrbanArea> {
        return listOf(
                UrbanArea("Madrid"),
                UrbanArea("Berlin"),
                UrbanArea("New York"),
                UrbanArea("Ottawa"),
                UrbanArea("Sydney"),
                UrbanArea("London")
        )
    }
}
