package akomissarova.archsample.model

import com.google.gson.annotations.SerializedName


class UrbanAreaResponse {
    @SerializedName("_links")
    val links: Links? = null
}

class Links {
    @SerializedName("ua:item")
    val list: List<UrbanArea>? = null
}
