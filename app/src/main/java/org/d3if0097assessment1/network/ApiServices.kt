package org.d3if0097assessment1.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.d3if0097assessment1.model.Book
import org.d3if0097assessment1.model.OpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

private const val BASE_URL = "http://20.2.250.135/"

val logging = HttpLoggingInterceptor().apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
}

val httpClient = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()

private val moshi = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit
    .Builder()
    .client(httpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface LogDayApiService {
    @GET("file-upload/{upload_by}")
    suspend fun getLogDay(@Path("upload_by") uploadBy: String): OpStatus<List<Book>>

    @Multipart
    @POST("file-upload")
    suspend fun postLogDay(
        @Part file: MultipartBody.Part,
        @Part("upload_by") uploadBy: RequestBody,
        @Part("description") description: RequestBody,
    ): OpStatus<Book>

    @DELETE("file-upload/{id}")
    suspend fun deleteLogDay(@Path("id") id: Int): OpStatus<Book>
}

object LogDayApi {
    val service: LogDayApiService by lazy {
        retrofit.create(LogDayApiService::class.java)
    }
}

enum class ApiStatus { LOADING, SUCCESS, FAILED }