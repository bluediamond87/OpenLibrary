package com.example.Albert.Network.Models

import com.google.gson.annotations.SerializedName

class BookListingResponse {

    @SerializedName("start")
    var StartingElementPosInList = 0

    @SerializedName("num_found")
    var NumberOfBooksFound = 0

    @SerializedName("docs")
    var BookItems = ArrayList<BookItemResponse>()
}