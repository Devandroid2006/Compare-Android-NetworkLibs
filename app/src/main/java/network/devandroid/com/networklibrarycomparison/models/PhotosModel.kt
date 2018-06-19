package network.devandroid.com.networklibrarycomparison.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PhotosModel {

    @SerializedName("albumId")
    @Expose
    var albumId: Int = 0

    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("thumbnailUrl")
    @Expose
    var thumbnailUrl: String? = null

}