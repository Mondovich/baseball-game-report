package it.mondovich.baseball.gamereport.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import it.mondovich.baseball.gamereport.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}