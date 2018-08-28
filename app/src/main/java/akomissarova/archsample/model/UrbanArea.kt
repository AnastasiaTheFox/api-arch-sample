package akomissarova.archsample.model

import android.arch.persistence.room.Entity

@Entity(tableName = "urbanArea")
data class UrbanArea(val name: String) {

}
