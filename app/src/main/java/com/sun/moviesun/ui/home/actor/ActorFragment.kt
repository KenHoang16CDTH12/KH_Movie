package com.sun.moviesun.ui.home.actor


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sun.moviesun.R
import com.sun.moviesun.base.RecyclerViewPaginator
import com.sun.moviesun.data.model.Person
import com.sun.moviesun.databinding.ActorFragmentBinding
import com.sun.moviesun.ui.detail.actor.ActorDetailActivity
import com.sun.moviesun.util.extension.provideMovieRepository

class ActorFragment : Fragment(), ActorNavigator {

  private lateinit var actorBinding: ActorFragmentBinding
  private lateinit var actorViewModel: ActorViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    actorViewModel = ActorViewModel(activity!!.applicationContext.provideMovieRepository(), this)
    actorBinding = DataBindingUtil.inflate(inflater, R.layout.actor_fragment, container, false)
    initializeUI()
    return actorBinding.root
  }

  private fun initializeUI() {
    actorBinding.run {
      viewModel = actorViewModel
      RecyclerViewPaginator(
          recyclerView = recyclerActors,
          loadMore = { page -> actorViewModel.loadActors(page) }
      )
    }
  }

  override fun onStop() {
    super.onStop()
    actorViewModel.onCleared()
  }

  override fun onClickItemActor(person: Person) {
    startActivity(ActorDetailActivity.newInstance(activity!!.applicationContext, person.id))
  }

  companion object {
    fun newInstance() = ActorFragment()
  }
}

