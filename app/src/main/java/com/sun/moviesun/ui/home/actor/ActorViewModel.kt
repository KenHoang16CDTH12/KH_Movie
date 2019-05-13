package com.sun.moviesun.ui.home.actor

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import com.sun.moviesun.data.model.Person
import com.sun.moviesun.data.repository.MovieRepository
import com.sun.moviesun.util.OnItemRecyclerViewClick
import com.sun.moviesun.util.extension.notNull
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ActorViewModel constructor(
    private val movieRepository: MovieRepository,
    private val navigator: ActorNavigator
) : BaseObservable(), OnItemRecyclerViewClick<Person> {

  val adapterActorObservable: ObservableField<ActorGridAdapter> = ObservableField()
  val compositeDisposable: CompositeDisposable = CompositeDisposable()

  init {
    setUpAdapter()
    loadActors()
  }

  private fun setUpAdapter() {
    adapterActorObservable.set(ActorGridAdapter(this))
  }

  fun loadActors(page: Int = DEFAULT_PAGE) {
    val disposable = movieRepository.getPopularPeople(page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          if (page == DEFAULT_PAGE)
            adapterActorObservable.get()!!.replaceItems(it.results)
          else
            adapterActorObservable.get()!!.addItems(it.results)
        }, { throwable -> handleError(throwable) })
    compositeDisposable.add(disposable)
  }

  fun handleError(t: Throwable) {

  }

  fun onCleared() {
    compositeDisposable.clear()
  }

  override fun onItemClickListener(data: Person) {
    navigator.notNull { it.onClickItemActor(data) }
  }

  companion object {
    private const val DEFAULT_PAGE = 1
  }
}