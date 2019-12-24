package com.example.Albert.DataRepo

import android.content.Context
import androidx.room.Room
import com.example.Albert.Models.BookItem
import com.example.Albert.Network.ApiClients
import com.example.Albert.Network.RestApiInterface
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import java.net.HttpURLConnection

class Repo : IRepo {
    private val libraryApi:RestApiInterface = ApiClients.getLibraryClient()
    private var wishlistApi:BookItemDao? = null
        get() = wishListApiInst


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
                                bookDoc.Key,
                                bookDoc.CoverImageId.toString(),
                                bookDoc.Title,
                                if (bookDoc.Authors.isEmpty()) "" else bookDoc.Authors[0],
                                ""
                                )
                            } as ArrayList<BookItem>
                            emitter.onSuccess(results)
                        }
                    }
                    else -> emitter.tryOnError(Throwable("Network error"))
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
                            bookDoc.Key,
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
    }

    override fun getAllWishlist() : Single<MutableList<BookItem>> {
        return Single.create<MutableList<BookItem>> { emitter ->
            wishlistApi?.let {
                val list = it.getAll()
                emitter.onSuccess(list)
            }

        }
    }

    override fun deleteFromWishlist(bookItem: BookItem) : Single<Boolean> {
        return Single.create<Boolean> { emitter ->
            wishlistApi?.let {
                it.deleteBook(bookItem)
                emitter.onSuccess(true)
            }

        }
    }

    override fun addToWishlist(bookItem: BookItem) : Single<Boolean> {
        return Single.create<Boolean> { emitter ->
            wishlistApi?.let {
                it.insertBook(bookItem)
                emitter.onSuccess(true)
            }

        }
    }

    companion object {
        val INSTANCE = Repo()
        private var wishListApiInst:BookItemDao? = null

        fun getInstance(): Repo {
            return INSTANCE
        }

        var appContext: Context? = null
        fun setApplicationContext(context:Context) {
            appContext = context
            wishListApiInst = Room.databaseBuilder(
                context,
                BookItemWishListDatabase::class.java, "wishlist"
            ).build().bookItemDao()
        }
    }
}