package com.sun.moviesun.util.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.sun.moviesun.R
import com.sun.moviesun.data.source.remote.api.Api
import com.sun.moviesun.util.extension.requestGlideListener

object BindingImage {

  @JvmStatic
  @BindingAdapter("bindImageUrl")
  fun bindImageUrl(imageView: ImageView, url: String?) {
    url?.let {
      Glide.with(imageView.context).load(Api.getPosterPath(it))
          .listener(imageView.context.requestGlideListener(imageView))
          .into(imageView)
    }
  }

  @JvmStatic
  @BindingAdapter("bindPaletteColor")
  fun bindPaletteColor(view: View, url: String?) {
    url?.let {
      Glide.with(view.context).load(Api.getPosterPath(it))
          .listener(GlidePalette.with(Api.getPosterPath(it))
              .use(BitmapPalette.Profile.VIBRANT)
              .intoBackground(view)
              .crossfade(true))
          .submit()
    }
  }

  @JvmStatic
  @BindingAdapter("bindSlideUrl")
  fun bindSlideUrl(imageView: ImageView, url: String?) {
    url?.let {
      Glide.with(imageView.context).load(Api.getBackdropPath(it))
          .listener(imageView.context.requestGlideListener(imageView))
          .into(imageView)
    }
  }

  @JvmStatic
  @BindingAdapter("bindFavoriteImage")
  fun bindFavoriteImage(imageView: ImageView, isFavorite: Boolean) {
    if (isFavorite) {
      imageView.setImageResource(R.drawable.ic_favorite_white_24dp)
    } else {
      imageView.setImageResource(R.drawable.ic_unfavorite_white_24dp)
    }
  }
}