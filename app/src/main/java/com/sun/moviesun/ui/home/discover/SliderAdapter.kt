package com.sun.moviesun.ui.home.discover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.sun.moviesun.R
import com.sun.moviesun.data.model.entity.Movie
import com.sun.moviesun.databinding.ItemSliderBinding
import com.sun.moviesun.ui.viewmodel.ItemMovieViewModel
import com.sun.moviesun.util.OnItemRecyclerViewClick

class SliderAdapter(
    private var onItemRecyclerViewClick: OnItemRecyclerViewClick<Movie>? = null
) : PagerAdapter() {

  private val list: ArrayList<Movie> = ArrayList()

  fun updateData(items: List<Movie>) {
    list.clear()
    list.addAll(items)
    notifyDataSetChanged()
  }

  override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
    val binding = DataBindingUtil.inflate(LayoutInflater.from(container.context), R.layout.item_slider, container, true) as ItemSliderBinding
    binding.viewModel = ItemMovieViewModel(onItemRecyclerViewClick)
    binding.viewModel!!.setData(list[position])
    binding.executePendingBindings()
    return binding.root
  }

  override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    container.removeView(`object` as View)
  }

  override fun isViewFromObject(view: View, obj: Any) = view == obj

  override fun getCount() = list.size

  companion object {

    private var sCurrentPosition: Int = 0

    fun setCurrentPosition(position: Int) { sCurrentPosition = position }
  }
}
