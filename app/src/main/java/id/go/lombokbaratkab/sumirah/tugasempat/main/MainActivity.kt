package id.go.lombokbaratkab.sumirah.tugasempat.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import id.go.lombokbaratkab.sumirah.tugasempat.fragment.FavoriteMatchFragment
import id.go.lombokbaratkab.sumirah.tugasempat.fragment.NextFragment
import id.go.lombokbaratkab.sumirah.tugasempat.fragment.PrevFragment
import id.go.lombokbaratkab.sumirah.tugasempat.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.navigation_prev-> {
                fragment = PrevFragment()
            }
            R.id.navigation_next -> {
                fragment = NextFragment()
            }
            R.id.navigation_fav -> {
                fragment = FavoriteMatchFragment()
            }
        }
        loadFragment(fragment)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        fragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        supportActionBar?.elevation = 0f

        btn_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        loadFragment(PrevFragment())
    }
}