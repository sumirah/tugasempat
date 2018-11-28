package id.go.lombokbaratkab.sumirah.tugasempat.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import id.go.lombokbaratkab.sumirah.tugasempat.api.ApiRepository
import id.go.lombokbaratkab.sumirah.tugasempat.api.TheSportDBApi
import id.go.lombokbaratkab.sumirah.tugasempat.model.Match
import id.go.lombokbaratkab.sumirah.tugasempat.model.TeamResponses
import id.go.lombokbaratkab.sumirah.tugasempat.R
import id.go.lombokbaratkab.sumirah.tugasempat.utils.EspressoIdlingResource
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MatchDetailActivity : AppCompatActivity(), DetailView{

    private lateinit var presenter: MatchDetailPresenter
    private var idEvent : String? = ""
    private var event : Match? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        idEvent = intent.getStringExtra("EVENT_ID")
        val idHome = intent.getStringExtra("HOME_ID")
        val idAway = intent.getStringExtra("AWAY_ID")

        presenter = MatchDetailPresenter(this, this)
        presenter.getEventDetailById(idEvent)

        //Ambil Logo
        EspressoIdlingResource.increment()
        ApiRepository.retrofit
                .create(TheSportDBApi::class.java)
                .getClubDetailById(idHome)
                .enqueue(object : Callback<TeamResponses> {
                    override fun onFailure(call: Call<TeamResponses>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<TeamResponses>, response: Response<TeamResponses>) {
                        EspressoIdlingResource.decrement()

                        val imgUrl = response.body()?.teams?.get(0)?.strTeamBadge
                        Glide.with(this@MatchDetailActivity).load(imgUrl).into(img_detail_club_home_logo)
                    }

                })

        EspressoIdlingResource.increment()
        ApiRepository.retrofit.create(TheSportDBApi::class.java)
                .getClubDetailById(idAway)
                .enqueue(object : Callback<TeamResponses> {
                    override fun onFailure(call: Call<TeamResponses>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<TeamResponses>, response: Response<TeamResponses>) {
                        EspressoIdlingResource.decrement()

                        val imgUrl = response.body()?.teams?.get(0)?.strTeamBadge
                        Glide.with(this@MatchDetailActivity).load(imgUrl).into(img_detail_club_away_logo)
                    }

                })


        //acton bar
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun initValue(match: Match){
        this.event = match

        text_detail_match_date.text = match.eventDate
        detail_club_home_name.text = match.homeTeam
        detail_club_away_name.text = match.awayTeam
        detail_club_home_score.text = match.homeScore
        detail_club_away_score.text = match.awayScore
        detail_club_home_goals.text = match.homeGoalDetails
        detail_club_away_goals.text = match.awayGoalDetails
        detail_club_home_gk.text = match.homeGoalKeeper
        detail_club_away_gk.text = match.awayGoalDetails
        detail_club_home_df.text = match.homeDefense
        detail_club_away_df.text = match.awayDefense
        detail_club_home_mf.text = match.homeMidfield
        detail_club_away_mf.text = match.awayMidfield
    }

    //handle action bar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite, menu)

        val item = menu?.findItem(R.id.favorite_star)
        if (isFavorite()) {
            item?.setIcon(R.drawable.ic_star)
        } else {
            item?.setIcon(R.drawable.ic_star_border)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.favorite_star -> {
                if (isFavorite()) {
                    presenter.deleteFavoriteMatch(idEvent)
                    item.setIcon(R.drawable.ic_star_border)
                } else {
                    presenter.saveFavoriteMatch(event)
                    item.setIcon(R.drawable.ic_star)
                }

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    private fun isFavorite() = presenter.getFavoriteMatchById(idEvent).size > 0


}