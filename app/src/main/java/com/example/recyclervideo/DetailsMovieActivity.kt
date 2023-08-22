package com.example.recyclervideo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import coil.load
import coil.size.Scale
import com.example.recyclervideo.api.ApiClient
import com.example.recyclervideo.api.ApiService
import com.example.recyclervideo.databinding.ActivityDetailsMovieBinding
import com.example.recyclervideo.response.DetailesMovieResponse
import com.example.recyclervideo.utils.Constants.POSTER_BASEURL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
* Hiện thị thông tin chi tiết của bộ phim dựa trên id của bộ phim
* Gửi request đến API sử dụng retrofit để lấy thông tin chi tiết và hien thi tren UI
*/

class DetailsMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsMovieBinding
    private val api: ApiService by lazy {
        ApiClient().getClient().create(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Lấy giá trị số nguyên có tên "id" từ Intent.
        //Điều này cho phép Activity lấy ID của bộ phim từ Intent truyền vào khi mở Activity.
        val movieid = intent.getIntExtra("id", 1)


//Các thành phần giao diện được cập nhật với thông tin chi tiết bộ phim từ DetailesMovieResponse
        binding.apply {

            val callDetaileMovieApi = api.getMovieDetaies(movieid)
            //Đây là phương thức gửi yêu cầu GET getMovieDetaies(movieid) đến API.
            callDetaileMovieApi.enqueue(object : Callback<DetailesMovieResponse> {
                //Phương thức này được gọi khi có phản hồi từ API.
                override fun onResponse(
                    call: Call<DetailesMovieResponse>, //cung cấp để xử lý phản hồi từ API.
                    response: Response<DetailesMovieResponse>
                ) {
                    when (response.code()) {
                        in 200..299 -> {
                            response.body().let { itBody ->
                                val imagePoster = POSTER_BASEURL + itBody!!.poster_path
                                //Dùng thư viện Coil để tải và hiển thị hình ảnh poster và hình nền của bộ phi,
                                imgMovie.load(imagePoster) {
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }
                                imgBackground.load(imagePoster) {
                                    crossfade(true)
                                    placeholder(R.drawable.poster_placeholder)
                                    scale(Scale.FILL)
                                }

                                tvTenPhim.text = itBody.title
                                tvChuDe.text = itBody.tagline
                                tvNgay.text = itBody.release_date
                                tvDanhGiaPhim.text = itBody.vote_average.toString()
                                tvThoiGianPhim.text = itBody.runtime.toString()
                                tvGiaVePhim.text = itBody.budget.toString()
                                tvDoanhThuPhim.text = itBody.revenue.toString()
                                tvMoTaPhim.text = itBody.overview

                            }
                        }
                        //Các TextView được cập nhật với thông tin chi tiết về bộ phim từ DetailesMovieResponse
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
                override fun onFailure(call: Call<DetailesMovieResponse>, t: Throwable) {
                    Log.e("onFailure", "Error: ${t.message}")
                }

            })

        }
    }
}