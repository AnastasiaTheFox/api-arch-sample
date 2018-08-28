package akomissarova.archsample.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "urbanArea")
data class UrbanArea(@PrimaryKey val name: String) {

}
