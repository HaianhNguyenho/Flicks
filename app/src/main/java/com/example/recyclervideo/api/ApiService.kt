package com.example.recyclervideo.api

import android.graphics.pdf.PdfDocument.Page
import com.example.recyclervideo.response.DetailesMovieResponse
import com.example.recyclervideo.response.MovieListResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*
* Xác định các request mà app sẽ gửi đến máy chủ thông qua Retrofit
* Xác định các phương thức để gửi yêu cầu đến API
* Annoation được cung cấp bởi retrofit để xác định HTTP mà sử dụng
* mô tả các yêu cầu cần gửi đến API sử dụng Retrofi
* kèm theo các tham số và kiểu trả về tương ứng.
*/
interface ApiService {
    @GET("movie/popular")
    //xử lý Khai báo 1 yêu cầu để lấy và hiển thị danh sách phim
    fun getPopularMovie(@Query("page") page: Int): Call<MovieListResponse>

    @GET("movie/{movie_id}")
    //xử lý khai báo 1 yêu cầu để lấy và hiển thị danh sách chi tiết phim
    fun getMovieDetaies(@Path("movie_id") id: Int): Call<DetailesMovieResponse>
}