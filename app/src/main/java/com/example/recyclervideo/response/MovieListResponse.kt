package com.example.recyclervideo.response


/*
* Chúng ta cần chuỗi json này để tạo 1 lớp model
* Tìm nạp các data này hiển thị các view
* chuyển đổi json thành lớp data
* có tất cả các trường để hiện thị trên view mình
*/

data class MovieListResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
) {
    data class Result(
        val adult: Boolean,
        val backdrop_path: String,
        val genre_ids: List<Int>,
        val id: Int,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String,
        val release_date: String,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int
    )
}