package it.mondovich.baseball.gamereport.ui.main.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(val name: String, val number: Int) : Parcelable {

}