package com.example.recyclervideo.utils

object Constants {
    /*
    * Tạo 1 đối tượng để định nghĩa, lưu trữ
    * để thực hiện các yêu cầu tới API hoặc xây dựng các URL liên quan đến hình ảnh poster của phim.
    * Lấy danh sách phim và để có danh sách phim thì phải có request
    * API_KEY: để gửi yêu cầu tới server và nhận dữ liệu
    * BASE URL: đường dẫn URL của API mà app se gửi yêu cầu tới
    * POSTER_BASEURL: đường dẫn để tải hình ảnh của phim
    */
    const val API_KEY = "6bb87e071618e7474d4b2bcf0c2ebeaa"
    const val BASEURL = "https://api.themoviedb.org/3/"
    const val POSTER_BASEURL = "https://image.tmdb.org/t/p/w500/"
}