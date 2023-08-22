package com.example.recyclervideo.api

import com.example.recyclervideo.utils.Constants.API_KEY
import com.example.recyclervideo.utils.Constants.BASEURL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/*
* Tạo cấu hình và cung cấp một đối tượng Retrofit để gửi yêu cầu tới API
*/
class ApiClient {
    private lateinit var retrofit: Retrofit

    // thêm tham số api_key vào các yêu cầu gửi đến API
    private val requestIntercreptor = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return@Interceptor chain.proceed(request)
    }

    // đối tượng OkHttpClient: thêm Interceptor và đặt timeout cho kết nối.
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestIntercreptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    // trả về một đối tượng Retrofit đã được cấu hình
    // Đối tượng Retrofit này sử dụng cơ sở URL từ BASEURL, OkHttpClient từ okHttpClient, và sử dụng
    // GsonConverterFactory để chuyển đổi dữ liệu từ JSON sang các đối tượng Kotlin.
    fun getClient(): Retrofit {
        retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

}