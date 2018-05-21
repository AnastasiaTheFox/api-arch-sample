package akomissarova.archsample.model

import com.google.gson.annotations.SerializedName


class UrbanAreaResponse {
    @SerializedName("_links")
    public val links: Links? = null
}

class Links {
    @SerializedName("ua:item")
    public val list: List<City>? = null
}
