package id.go.lombokbaratkab.sumirah.tugasempat.utils

import android.support.test.espresso.idling.CountingIdlingResource


object EspressoIdlingResource {
    private val RESOURCE = "GLOBAL"

    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        countingIdlingResource.decrement()
    }
}