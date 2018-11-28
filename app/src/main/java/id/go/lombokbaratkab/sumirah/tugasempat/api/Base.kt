package id.go.lombokbaratkab.sumirah.tugasempat.api

interface Base {
    interface BasePresenter<V> {
        fun attach(view: V)
        fun detach()
    }
}