package it.mondovich.baseball.gamereport.ui.main.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.mondovich.baseball.gamereport.R
import it.mondovich.baseball.gamereport.ui.main.model.TeamViewModel
import it.mondovich.baseball.gamereport.ui.main.utils.StorageUtils
import java.util.*

/**
 * A fragment representing a list of Items.
 */
class SelectTeamFragment : Fragment() {

    private val viewModel: TeamViewModel by viewModels({requireParentFragment()})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.selectedItem.observe(this, Observer { item ->
            // Perform an action with the latest item data
            if (item != null) {
                setFragmentResult(SELECT_TEAM, bundleOf(SELECTED_TEAM to item))
                findNavController().popBackStack()
                viewModel.unselectItem()
            }
            return@Observer
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_team_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = TeamRecyclerViewAdapter(viewModel, StorageUtils.loadTeams(context))
            }
        }
        return view
    }

    companion object {
        const val SELECT_TEAM = "selectedTeamRequest"
        const val SELECTED_TEAM = "selectedTeam"
        @JvmStatic
        fun newInstance() = SelectTeamFragment()
    }
}