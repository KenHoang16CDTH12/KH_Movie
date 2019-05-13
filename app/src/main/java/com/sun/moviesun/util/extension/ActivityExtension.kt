package com.sun.moviesun.util.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sun.moviesun.R
import com.sun.moviesun.data.repository.MovieRepository
import com.sun.moviesun.data.repository.MovieRepositoryImpl
import com.sun.moviesun.data.source.local.MovieLocalDataSource
import com.sun.moviesun.data.source.local.database.AppDatabase
import com.sun.moviesun.data.source.remote.MovieRemoteDataSource
import com.sun.moviesun.data.source.remote.connect.RetrofitClient

fun AppCompatActivity.setUpToolbar(toolbar: Toolbar, titleText: String = "") {
  setSupportActionBar(toolbar)
  supportActionBar?.run {
    title = titleText
  }
}

fun AppCompatActivity.simpleToolbarWithHome(toolbar: Toolbar, title: String = "") {
  setSupportActionBar(toolbar)
  supportActionBar?.run {
    setDisplayHomeAsUpEnabled(true)
    setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
    this.title = title
  }
}

fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int) {
  supportFragmentManager.transact {
    replace(frameId, fragment)
  }
}

fun AppCompatActivity.addFragmentToActivity(fragment: Fragment, tag: String) {
  supportFragmentManager.transact {
    add(fragment, tag)
  }
}

private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
  beginTransaction().apply {
    action()
  }.commit()
}

fun Context.circularRevealedAtCenter(view: View) {
  val cx = (view.left + view.right) / 2
  val cy = (view.top + view.bottom) / 2
  val finalRadius = Math.max(view.width, view.height)
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && view.isAttachedToWindow) {
    val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius.toFloat())
    view.visibility = View.VISIBLE
    view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
    anim.duration = 550
    anim.start()
  }
}

fun Context.requestGlideListener(view: View): RequestListener<Drawable> =
    object : RequestListener<Drawable> {
      override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean) = false

      override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
        circularRevealedAtCenter(view)
        return false
      }
    }

fun Context.provideMovieRepository(): MovieRepository {
  return MovieRepositoryImpl.getInstance(MovieLocalDataSource.getInstance(AppDatabase.getInstance(this)),
      MovieRemoteDataSource.getInstance(RetrofitClient(this)))
}

fun Context.isNetworkStatusAvailable(): Boolean {
  val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
  return connectivityManager?.activeNetworkInfo?.isConnected ?: false
}
