package it.mondovich.baseball.gamereport.ui.main.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TeamViewModel : ViewModel() {
    private val mutableSelectedItem = MutableLiveData<Team?>()
    val selectedItem: MutableLiveData<Team?> get() = mutableSelectedItem

    fun selectItem(team: Team) {
        if (mutableSelectedItem.value != team) mutableSelectedItem.value = team
    }

    fun unselectItem() {
        mutableSelectedItem.value = null
    }
}