package com.sun.moviesun.ui.home.actor

import com.sun.moviesun.data.model.Person

interface ActorNavigator {
  fun onClickItemActor(person: Person)
}