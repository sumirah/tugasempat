package id.go.lombokbaratkab.sumirah.tugasempat.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.go.lombokbaratkab.sumirah.tugasempat.api.ApiRepository
import id.go.lombokbaratkab.sumirah.tugasempat.api.TheSportDBApi
import id.go.lombokbaratkab.sumirah.tugasempat.detail.MatchDetailActivity
import id.go.lombokbaratkab.sumirah.tugasempat.model.Match
import id.go.lombokbaratkab.sumirah.tugasempat.model.MatchesResponses
import id.go.lombokbaratkab.sumirah.tugasempat.R
import kotlinx.android.synthetic.main.fragment_next.*
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NextFragment : Fragment() {

    private var service : Call<MatchesResponses>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_next, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        service = ApiRepository.retrofit.create(TheSportDBApi::class.java).getNextMatch()
        service?.enqueue(object : Callback<MatchesResponses> {
            override fun onFailure(call: Call<MatchesResponses>, t: Throwable) {
                Log.e("ERROR_RESPONSE", t.localizedMessage)
            }

            override fun onResponse(call: Call<MatchesResponses>, response: Response<MatchesResponses>) {
                response.body()?.events?.let {
                    initRecyclerView(it)
                }
            }

        })
    }

    private fun initRecyclerView(list: MutableList<Match>) {
        rv_next.layoutManager = LinearLayoutManager(context)
        val adapter = NextRecyclerViewAdapter(list) {
            startActivity<MatchDetailActivity>(
                    "EVENT_ID" to it.eventId,
                    "HOME_ID" to it.homeId,
                    "AWAY_ID" to it.awayId
            )
        }
        rv_next.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (service?.isExecuted == true){
            service?.cancel()
        }
    }
}