package id.go.lombokbaratkab.sumirah.tugasempat.fragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.go.lombokbaratkab.sumirah.tugasempat.model.Match
import id.go.lombokbaratkab.sumirah.tugasempat.R
import org.jetbrains.anko.find

class PrevRecyclerViewAdapter(private val list: MutableList<Match>, private val listener: (Match) -> Unit) : RecyclerView.Adapter<PrevRecyclerViewAdapter.PrevViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PrevViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.eventlist_item, p0, false)
        return PrevViewHolder(view)
    }

    override fun getItemCount(): Int  = list.size

    override fun onBindViewHolder(p0: PrevViewHolder, p1: Int) {
        p0.bindItem(list[p1], listener)
    }

    class PrevViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val homeName = view.find<TextView>(R.id.daftar_home_name)
        val eventDate: TextView = view.find(R.id.daftar_date_event)
        val homeScore = view.find<TextView>(R.id.daftar_home_score)
        val awayName = view.find<TextView>(R.id.daftar_away_name)
        val awayScore = view.find<TextView>(R.id.daftar_away_score)


        fun bindItem(e: Match, listener: (Match) -> Unit) {
            homeName.text = e.homeTeam
            eventDate.text = e.eventDate
            homeScore.text = e.homeScore
            awayName.text = e.awayTeam
            awayScore.text = e.awayScore

            itemView.setOnClickListener {
                listener(e)
            }
        }
    }
}