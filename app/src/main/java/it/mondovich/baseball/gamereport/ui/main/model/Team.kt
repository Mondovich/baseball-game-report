package it.mondovich.baseball.gamereport.ui.main.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(var name: String?) : Parcelable {
    val roster = Array(9) { Player("", -1) }
}