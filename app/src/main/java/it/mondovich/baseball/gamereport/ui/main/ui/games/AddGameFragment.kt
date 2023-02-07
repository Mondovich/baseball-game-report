package it.mondovich.baseball.gamereport.ui.main.ui.games

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import it.mondovich.baseball.gamereport.R
import it.mondovich.baseball.gamereport.ui.main.model.Team
import it.mondovich.baseball.gamereport.ui.main.utils.StorageUtils


class AddGameFragment : Fragment() {

    private val homeTeam = MutableLiveData<Team>()
    private val guestTeam = MutableLiveData<Team>()
    private lateinit var teamInfoAdapter: TeamInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeTeam.value = Team("")
        guestTeam.value = Team("")
        // Use the Kotlin extension in the fragment-ktx artifact
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = view.findViewById<ViewPager2>(R.id.tab_view_pager)
        teamInfoAdapter = TeamInfoAdapter(this, viewPager, homeTeam, guestTeam)
        viewPager.adapter = teamInfoAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> resources.getString(R.string.tab_guest_team)
                1 -> resources.getString(R.string.tab_host_team)
                else -> ""
            }
        }.attach()
        setFragmentResultListener(SelectTeamFragment.SELECT_TEAM) { _, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val result = bundle.getParcelable<Team>(SelectTeamFragment.SELECTED_TEAM)
            // Do something with the result
            when (viewPager.currentItem) {
                0 -> homeTeam.value = result!!
                else -> guestTeam.value = result!!
            }
            Toast.makeText(context, "Team loaded", Toast.LENGTH_LONG).show();
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_game, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_settings -> {
                view?.findNavController()?.navigate(R.id.action_nav_add_game_to_nav_settings)
                true
            }
            R.id.action_load_team -> {
                view?.findNavController()?.navigate(R.id.action_nav_add_game_to_nav_select_team)
                true
            }
            R.id.action_save_team -> {
                val currentTeam = teamInfoAdapter.getCurrent().getTeam();
                StorageUtils.saveTeam(context, currentTeam)
                Toast.makeText(context, "Team saved", Toast.LENGTH_LONG).show();
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

class TeamInfoAdapter(fragment: Fragment,
                      private val viewPager: ViewPager2,
                      homeTeam: MutableLiveData<Team>,
                      guestTeam: MutableLiveData<Team>) : FragmentStateAdapter(fragment) {

    private val mFragmentManager = fragment.childFragmentManager
    private val fragments = arrayOf(TeamInfoFragment.newInstance(homeTeam), TeamInfoFragment.newInstance(guestTeam))

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun getCurrent(): TeamInfoFragment {
        return mFragmentManager.findFragmentById(fragments[viewPager.currentItem].id) as TeamInfoFragment
    }

}
