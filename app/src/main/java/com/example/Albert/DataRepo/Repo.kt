package com.example.Albert.DataRepo

import com.example.Albert.Models.BookItem
import com.example.Albert.Network.ApiClients
import com.example.Albert.Network.RestApiInterface
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import java.net.HttpURLConnection

class Repo : IRepo {
    val libraryApi:RestApiInterface = ApiClients.getLibraryClient()

    override fun getBookSearch(searchFor:String): Single<ArrayList<BookItem>> {
        return Single.create<ArrayList<BookItem>>( object: SingleOnSubscribe<ArrayList<BookItem>> {
            override fun subscribe(emitter: SingleEmitter<ArrayList<BookItem>>) {
                var call = libraryApi.getSearchResults(searchFor).execute()
                var statusCode = call.code()
                when(statusCode) {
                    HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_CREATED, HttpURLConnection.HTTP_ACCEPTED -> {
                        var body = call.body()
                        body?.let { it ->
                            var results = it.BookItems.map { bookDoc -> BookItem(
                                bookDoc.CoverImageId.toString(),
                                bookDoc.Title,
                                if (bookDoc.Authors.isEmpty()) "" else bookDoc.Authors[0],
                                ""
                                )
                            } as ArrayList<BookItem>
                            emitter.onSuccess(results)
                        }
                    }
                }
            }
        })
    }

    override fun getBookSearch(searchFor:String, page:Int): Single<ArrayList<BookItem>> {
        return Single.create<ArrayList<BookItem>> { emitter ->
            val call = libraryApi.getSearchResults(searchFor, page).execute()
            val statusCode = call.code()
            when(statusCode) {
                HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_CREATED, HttpURLConnection.HTTP_ACCEPTED -> {
                    var body = call.body()
                    body?.let { it ->
                        var results = it.BookItems.map { bookDoc -> BookItem(
                            bookDoc.CoverImageId.toString(),
                            bookDoc.Title,
                            if (bookDoc.Authors.isEmpty()) "" else bookDoc.Authors[0],
                            "")
                        }
                        emitter.onSuccess(results as ArrayList<BookItem>)
                    }
                }
                else -> emitter.tryOnError(Throwable("Network error"))
            }
        }
//        return Single.create<ArrayList<BookItem>>( object: SingleOnSubscribe<ArrayList<BookItem>> {
//            override fun subscribe(emitter: SingleEmitter<ArrayList<BookItem>>) {
//                val call = libraryApi.getSearchResults(searchFor, page).execute()
//                val statusCode = call.code()
//                when(statusCode) {
//                    HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_CREATED, HttpURLConnection.HTTP_ACCEPTED -> {
//                        var body = call.body()
//                        body?.let { it ->
//                            var results = it.BookItems.map { bookDoc -> BookItem(
//                                bookDoc.CoverImageId.toString(),
//                                bookDoc.Title,
//                                bookDoc.Authors[0])
//                            } as ArrayList<BookItem>
//                            emitter.onSuccess(results)
//                        }
//                    }
//                    else -> emitter.tryOnError(Throwable("Network error"))
//                }
//            }
//        })
    }

    companion object {
        val INSTANCE = Repo()
        fun getInstance(): Repo {
            return INSTANCE
        }
    }
}