package com.sun.moviesun.ui.home.genres

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sun.moviesun.data.model.Genre
import com.sun.moviesun.ui.home.genres.GenreFragment

class GenrePageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

  private val genres: ObservableList<Genre> = ObservableArrayList()

  fun updateData(items: List<Genre>) {
    genres.clear()
    genres.addAll(items)
    notifyDataSetChanged()
  }

  override fun getItem(position: Int): Fragment = GenreFragment.newInstance(genres[position].id)

  override fun getCount(): Int = genres.size

  override fun getPageTitle(position: Int): CharSequence? = genres[position].name
}