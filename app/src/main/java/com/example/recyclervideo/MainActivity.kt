package com.example.recyclervideo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclervideo.adapter.MovieAdapter
import com.example.recyclervideo.api.ApiClient
import com.example.recyclervideo.api.ApiService
import com.example.recyclervideo.databinding.ActivityMainBinding
import com.example.recyclervideo.response.MovieListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Sử dụng retrofit để gọi API và hiển thị ds phim trong recyclerview
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // biến lazy để tạo đối tượng Movie Apdapter
    private val movieAdapter by lazy {
        MovieAdapter()
    }

    // biến lazy để tao đối tượng ApiService
    private val api: ApiService by lazy {
        ApiClient().getClient().create(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //liên kết các thành phần giao diện trong layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            proBarMovie.visibility = View.VISIBLE
            //gửi yêu cầu đến api
            val callMovieApi = api.getPopularMovie(1)
            callMovieApi.enqueue(object : Callback<MovieListResponse> {

                //Phương thức này được gọi khi có phản hồi từ API
                override fun onResponse(
                    //xử lý phản hồi api
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    proBarMovie.visibility = View.GONE
                    //xác định trạng thái phản hồi và xử lý dữ liệu.
                    when (response.code()) {
                        //Phản hồi thành công
                        in 200..299 -> {
                            response.body().let { itBody ->
                                itBody?.results.let { itData ->
                                    if (itData!!.isNotEmpty()) {
                                        //Gửi danh sách phim mới tải vào movieAdapter để cập nhật RecyclerView.
                                        movieAdapter.differ.submitList(itData)
                                        // Thiết lập LinearLayoutManager cho RecyclerView và gắn adapter là movieAdapter
                                        rvMovie.apply {
                                            layoutManager = LinearLayoutManager(this@MainActivity)
                                            adapter = movieAdapter
                                        }
                                    }
                                }
                            }
                        }
                        //Phạm vi chuyển hướng
                        in 300..399 -> {
                            Log.d("Phản hồi", "Phạm vi chuyển hướng : ${response.code()}")
                        }
                        //Lỗi máy khách
                        in 400..499 -> {
                            Log.d("Phản hồi", "Lỗi máy khách: ${response.code()}")
                        }
                        //Lỗi máy chủ
                        in 500..599 -> {
                            Log.d("Phản hồi", "Lỗi máy chủ: ${response.code()}")
                        }
                    }
                }

                //Phương thức này được gọi khi yêu cầu gặp lỗi.
                //Log thông báo lỗi
                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    //Hiển thị thanh ProgressBar khi đang tải dữ liệu từ API và ẩn nó khi dữ liệu đã được tải.
                    binding.proBarMovie.visibility = View.GONE
                    Log.e("onFailure", "Error: ${t.message}")
                }

            })

        }
    }
}