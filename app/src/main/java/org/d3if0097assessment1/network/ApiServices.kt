package org.d3if0097assessment1.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if0097assessment1.model.Book
import org.d3if0097assessment1.model.OpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

private const val BASE_URL = "https://api.telutizen.my.id/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiServices {
    @GET("buku")
    suspend fun getApi(
        @Header("Authorization") userId: String
    ): List<Book>

    @Multipart
    @POST("buku")
    suspend fun postBuku(
        @Header("Authorization") userId: String,
        @Part("judul") judul: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part gambar: MultipartBody.Part,
    ): OpStatus
}

object Api {
    val service: ApiServices by lazy {
        retrofit.create(ApiServices::class.java)
    }

    fun getApiUrl(imageId: String): String {
        return "$BASE_URL$imageId"
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }