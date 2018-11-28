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
import kotlinx.android.synthetic.main.fragment_prev.*
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import id.go.lombokbaratkab.sumirah.tugasempat.utils.EspressoIdlingResource

class PrevFragment : Fragment() {

    private var service: Call<MatchesResponses>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_prev, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        EspressoIdlingResource.increment()
        service = ApiRepository.retrofit.create(TheSportDBApi::class.java).getPrevMatch()
        service?.enqueue(object : Callback<MatchesResponses> {
            override fun onFailure(call: Call<MatchesResponses>, t: Throwable) {
                Log.e("ERROR_RESPONSE", t.localizedMessage)
            }

            override fun onResponse(call: Call<MatchesResponses>, response: Response<MatchesResponses>) {
                EspressoIdlingResource.decrement()

                response.body()?.events?.let {
                    initRecyclerView(it)
                }
            }

        })
    }


    private fun initRecyclerView(list: MutableList<Match>) {
        rv_prev.layoutManager = LinearLayoutManager(context)
        val adapter = PrevRecyclerViewAdapter(list) {
            startActivity<MatchDetailActivity>(
                    "EVENT_ID" to it.eventId,
                    "HOME_ID" to it.homeId,
                    "AWAY_ID" to it.awayId
            )
        }
        rv_prev.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (service?.isExecuted == true) {
            service?.cancel()
        }
    }
}