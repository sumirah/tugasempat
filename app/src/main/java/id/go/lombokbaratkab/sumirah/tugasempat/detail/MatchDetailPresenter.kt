package id.go.lombokbaratkab.sumirah.tugasempat.detail

import android.content.Context
import id.go.lombokbaratkab.sumirah.tugasempat.api.ApiRepository
import id.go.lombokbaratkab.sumirah.tugasempat.api.TheSportDBApi
import id.go.lombokbaratkab.sumirah.tugasempat.model.LocalEvent
import id.go.lombokbaratkab.sumirah.tugasempat.model.Match
import id.go.lombokbaratkab.sumirah.tugasempat.model.MatchesResponses
import id.go.lombokbaratkab.sumirah.tugasempat.utils.EspressoIdlingResource
import id.go.lombokbaratkab.sumirah.tugasempat.utils.database
import org.jetbrains.anko.db.*
import org.jetbrains.anko.longToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchDetailPresenter(private val context: Context?, private val view: DetailView) {
    fun getEventDetailById(idEvent: String?) {
        EspressoIdlingResource.increment()

        val service = ApiRepository.retrofit.create(TheSportDBApi::class.java).getEventDetailById(idEvent)
        service.enqueue(object : Callback<MatchesResponses> {
            override fun onFailure(call: Call<MatchesResponses>, t: Throwable) {

            }

            override fun onResponse(call: Call<MatchesResponses>, response: Response<MatchesResponses>) {
                EspressoIdlingResource.decrement()

                response.body()?.events?.get(0)?.let { view.initValue(it) }
            }

        })
    }

    fun getFavoriteMatchById(idEvent: String?): MutableList<LocalEvent> {
        val events: MutableList<LocalEvent> = ArrayList()

        this.context?.database?.use {
            select("FavoriteMatch")
                    .whereArgs("(idEvent = {id_event})",
                            "id_event" to "${idEvent}").exec {
                        parseList(object : MapRowParser<MutableList<LocalEvent>> {
                            override fun parseRow(columns: Map<String, Any?>): MutableList<LocalEvent> {
                                val evt = LocalEvent(
                                        columns["idEvent"].toString()
                                )
                                events.add(evt)

                                return events
                            }

                        })
                    }
        }

        return events
    }

    fun saveFavoriteMatch(event: Match?) {
        this.context?.database?.use {
            insert(
                    "FavoriteMatch",
                    "idEvent" to event?.eventId,
                    "idHomeTeam" to event?.homeId,
                    "idAwayTeam" to event?.awayId,
                    "strHomeTeam" to event?.homeTeam,
                    "strAwayTeam" to event?.awayTeam,
                    "intHomeScore" to event?.homeScore,
                    "intAwayScore" to event?.awayScore,
                    "dateEvent" to event?.eventDate
            )
            context?.longToast("Match ditambahkan ke Favorit")
        }
    }

    fun deleteFavoriteMatch(idEvent: String?) {
        EspressoIdlingResource.increment()
        context?.database?.use {
            delete("FavoriteMatch", "idEvent = {id_event}", "id_event" to "${idEvent}")
            context.longToast("Match dihapus dari Favorit")
            EspressoIdlingResource.decrement()
        }
    }
}