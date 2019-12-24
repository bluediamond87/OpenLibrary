package com.example.Albert.Network.Models

import com.google.gson.annotations.SerializedName

class BookItemResponse {

    @SerializedName("key")
    var Key:String = ""

    @SerializedName("cover_i")
    var CoverImageId:Int = 0

    @SerializedName("title")
    var Title:String = ""

    @SerializedName("author_name")
    var Authors = ArrayList<String>()

}