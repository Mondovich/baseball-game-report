package it.mondovich.baseball.gamereport.ui.main.ui.games

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import it.mondovich.baseball.gamereport.R
import it.mondovich.baseball.gamereport.ui.main.model.Team
import it.mondovich.baseball.gamereport.ui.main.model.TeamViewModel

/**
 * [RecyclerView.Adapter] that can display a [Team].
 */
class TeamRecyclerViewAdapter(private val viewModel: TeamViewModel, private val values: Map<String,Team>)
    : RecyclerView.Adapter<TeamRecyclerViewAdapter.ViewHolder>() {

    private val orderedKeys = values.keys.sorted();
    private var selectedItemPos = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_team_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Team? = values[orderedKeys[position]]
        holder.contentView.text = item?.name
        if (position == selectedItemPos) {
            if (item != null) viewModel.selectItem(item)
            selectedItemPos = -1
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentView: TextView = view.findViewById(R.id.content)

        init {
            contentView.setOnClickListener {
                selectedItemPos = adapterPosition
                notifyItemChanged(adapterPosition)
                notifyDataSetChanged()
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}