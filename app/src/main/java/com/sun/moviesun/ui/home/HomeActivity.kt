package com.sun.moviesun.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sun.moviesun.R
import com.sun.moviesun.data.annotation.PageKeyDef
import com.sun.moviesun.ui.search.SearchActivity
import com.sun.moviesun.util.extension.setUpToolbar
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.home_activity)
    setUpToolbar(toolbar, getString(R.string.app_name))
    initializeUI()
    initializeEvent()
  }

  private fun initializeUI() {
    pagerMain.run {
      adapter = MainPageAdapter(supportFragmentManager)
      offscreenPageLimit = (adapter as MainPageAdapter).count
    }
  }

  private fun initializeEvent() {
    menuBottom.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener())
  }

  private fun onNavigationItemSelectedListener() = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
      R.id.actionDiscover -> {
        pagerMain.currentItem = PageKeyDef.DISCOVER
        return@OnNavigationItemSelectedListener true
      }
      R.id.actionGenre -> {
        pagerMain.currentItem = PageKeyDef.GENRE
        return@OnNavigationItemSelectedListener true
      }
      R.id.actionFavorite-> {
        pagerMain.currentItem = PageKeyDef.FAVORITE
        return@OnNavigationItemSelectedListener true
      }
      R.id.actionActor-> {
        pagerMain.currentItem = PageKeyDef.ACTOR
        return@OnNavigationItemSelectedListener true
      }
    }
    false
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.option_menu_home, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.actionSearch -> {
        startActivity(SearchActivity.newInstance(applicationContext))
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }
}
