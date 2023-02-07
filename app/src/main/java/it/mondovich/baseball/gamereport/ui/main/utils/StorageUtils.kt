package it.mondovich.baseball.gamereport.ui.main.utils

import android.content.Context
import android.widget.Toast
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import it.mondovich.baseball.gamereport.ui.main.model.Team
import java.io.FileNotFoundException

class StorageUtils {
    companion object {
        private const val TEAMS = "teams"
        private val mapper = jacksonObjectMapper()

        fun loadTeams(context: Context?): Map<String, Team> {
            return try {
                context?.openFileInput(TEAMS)?.bufferedReader()?.use {
                    val json = it.readText()
                    return mapper.readValue<Map<String, Team>>(json)
                } ?: LinkedHashMap()
            } catch (e: FileNotFoundException) {
                LinkedHashMap()
            }
        }

        fun loadTeam(context: Context?, name: String): Team? {
            if (name.isNullOrBlank()) {
                Toast.makeText(context, "Specificare un nome", Toast.LENGTH_LONG).show();
                return null
            }
            val teams = loadTeams(context).toMutableMap()
            return teams[name]
        }

        fun saveTeam(context: Context?, team: Team) {
            if (team.name.isNullOrBlank()) {
                Toast.makeText(context, "Dare un nome alla squadra", Toast.LENGTH_LONG).show();
                return
            }
            val teams = loadTeams(context).toMutableMap()
            teams[team.name!!] = team
            saveTeams(context, teams)
        }

        private fun saveTeams(context: Context?, teams: Map<String, Team>): Map<String, Team> {
            context?.openFileOutput(TEAMS, Context.MODE_PRIVATE).use {
                val json = mapper.writeValueAsString(teams)
                it?.write(json.toByteArray())
            }
            return teams
        }
    }
}