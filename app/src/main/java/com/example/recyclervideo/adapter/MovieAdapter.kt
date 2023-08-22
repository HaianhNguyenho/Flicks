package com.example.recyclervideo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.recyclervideo.DetailsMovieActivity
import com.example.recyclervideo.R
import com.example.recyclervideo.databinding.ItemsRowBinding
import com.example.recyclervideo.response.MovieListResponse
import com.example.recyclervideo.utils.Constants.POSTER_BASEURL

/*
* Tạo ra 1 adapter để kết nối data từ ds phim vào Recyclerview
* ViewHolder được sử dụng để hiện thị thông tin từ các item
* Hiển thị thông tin về các bộ phim,gồm tên phim, đánh giá, ngôn ngữ, ngày ra mắt & hình ảnh poster.
*/
class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private lateinit var binding: ItemsRowBinding

    private lateinit var context: Context

    //Recyclerview cần tạo một viewholder mới cho một item
    //ItemRowBinding: gắn layoutitem
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemsRowBinding .inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()

    }

    //phương thức trả về số lượng item có trong danh sách
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // Xử lý khi RecyclerView cần hiện thị dữ liệu của 1 item tại vị trí cụ thể
    // gọi phương thức bind trong ViewHolder để cập nhật dữ liệu của một item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    //  Gắn kết và hiển thị dữ liệu của một item
    // phương thức bind cập nhật thông tin từ MovieListResponse.Result vào các thành phần UI
    // Tên phim, đánh giá, hình ảnh
    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieListResponse.Result) {
            binding.apply {
                tenPhim.text = item.title
                tvStar.text = item.vote_average.toString()
                val moviePosterURL = POSTER_BASEURL + item.poster_path
                imgMovie.load(moviePosterURL) {
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                tvLanguage.text = item.original_language
                Day.text = item.release_date

                root.setOnClickListener {
                    val intent = Intent(context, DetailsMovieActivity::class.java)
                    intent.putExtra("id", item.id)
                    context.startActivity(intent)
                }

            }

        }

    }

    // So sánh và xác định sự khác biệt giữa các items trong ds
    private val diffeCallBack = object : DiffUtil.ItemCallback<MovieListResponse.Result>() {
        override fun areItemsTheSame(
            oldItem: MovieListResponse.Result,
            newItem: MovieListResponse.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieListResponse.Result,
            newItem: MovieListResponse.Result
        ): Boolean {
            return oldItem == newItem
        }

    }

    //quản lý sự thay đổi trong danh sách dữ liệu và cập nhật RecyclerView
    val differ = AsyncListDiffer(this, diffeCallBack)

}