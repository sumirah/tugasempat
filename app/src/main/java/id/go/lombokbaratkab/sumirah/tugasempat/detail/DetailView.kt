package id.go.lombokbaratkab.sumirah.tugasempat.detail

import id.go.lombokbaratkab.sumirah.tugasempat.model.Match

interface DetailView {
    fun showLoading()
    fun hideLoading()

    fun initValue(match: Match)
}