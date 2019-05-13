package com.sun.moviesun.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sun.moviesun.data.annotation.PageKeyDef
import com.sun.moviesun.ui.home.actor.ActorFragment
import com.sun.moviesun.ui.home.discover.DiscoverFragment
import com.sun.moviesun.ui.home.favorite.FavoriteFragment
import com.sun.moviesun.ui.home.genres.GenresFragment

class MainPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

  override fun getItem(position: Int): Fragment =
      when (position) {
        PageKeyDef.DISCOVER -> { DiscoverFragment.newInstance() }
        PageKeyDef.GENRE -> { GenresFragment.newInstance() }
        PageKeyDef.FAVORITE -> { FavoriteFragment.newInstance() }
        PageKeyDef.ACTOR -> { ActorFragment.newInstance() }
        else -> { DiscoverFragment.newInstance() }
      }

  override fun getCount() = DEFAULT_PAGE_SIZE

  companion object {
    private const val DEFAULT_PAGE_SIZE = 5
  }
}
