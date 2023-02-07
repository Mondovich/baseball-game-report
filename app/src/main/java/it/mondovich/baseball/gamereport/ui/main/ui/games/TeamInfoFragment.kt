package it.mondovich.baseball.gamereport.ui.main.ui.games

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import it.mondovich.baseball.gamereport.R
import it.mondovich.baseball.gamereport.ui.main.model.Player
import it.mondovich.baseball.gamereport.ui.main.model.Team

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_NAME = "name"

/**
 * A simple [Fragment] subclass.
 * Use the [TeamInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TeamInfoFragment(private val team: MutableLiveData<Team>) : Fragment() {
    private var name: String? = null
    private lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_NAME)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_team_info, container, false)

        var i = 1
        playerLayoutIds.iterator().forEach { id ->
            val view = root.findViewById<View>(id)
            val pos = view.findViewById<TextView>(R.id.text_player_pos)
            pos.text = (i++).toString()
        }
        team.observe(viewLifecycleOwner, Observer {
            apply(it)
            return@Observer
        })
        return root
    }

    fun getTeam(): Team {
        val teamView = view?.findViewById<TextView>(R.id.text_team_name)
        val team = Team(teamView?.text.toString())
        playerLayoutIds.iterator().forEach { id ->
            val layout = view?.findViewById<View>(id)
            val pos = layout?.findViewById<TextView>(R.id.text_player_pos)
            val name = layout?.findViewById<TextView>(R.id.text_player_name)
            val num = layout?.findViewById<TextView>(R.id.text_player_num)
            if (!name?.text.isNullOrBlank() && num?.text?.isDigitsOnly() == true) {
                val player = Player(name?.text.toString(), num.text.toString()?.toInt())
                team.roster[pos?.text?.toString()?.toInt()?.dec() ?: 0] = player
            }
        }
        return team
    }

    private fun apply(it: Team) {
        val teamName = root.findViewById<TextView>(R.id.text_team_name)
        teamName.text = it.name
        playerLayoutIds.iterator().forEach { id ->
            val layout = root.findViewById<View>(id)
            val pos = layout.findViewById<TextView>(R.id.text_player_pos)
            val name = layout.findViewById<TextView>(R.id.text_player_name)
            val num = layout.findViewById<TextView>(R.id.text_player_num)
            if (pos?.text != null) {
                val player = it.roster[pos.text?.toString()?.toInt()?.dec() ?: -1]
                name?.text = player.name
                num?.text = if (player.number > 0) player.number.toString() else ""
            }
        }
    }

    companion object {
        val playerLayoutIds = arrayOf(R.id.player_1, R.id.player_2, R.id.player_3, R.id.player_4,
            R.id.player_5, R.id.player_6, R.id.player_7, R.id.player_8, R.id.player_9)
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param team Team data.
         * @return A new instance of fragment TeamInfoFragment.
         */
        @JvmStatic
        fun newInstance(team: MutableLiveData<Team>) =
                TeamInfoFragment(team).apply {
                    arguments = Bundle().apply {
                        putString(ARG_NAME, name)
                    }
                }

    }
}