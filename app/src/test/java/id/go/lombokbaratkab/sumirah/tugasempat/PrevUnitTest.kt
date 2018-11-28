package id.go.lombokbaratkab.sumirah.tugasempat

import android.content.Context
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import id.go.lombokbaratkab.sumirah.tugasempat.detail.DetailView
import id.go.lombokbaratkab.sumirah.tugasempat.detail.MatchDetailPresenter
import id.go.lombokbaratkab.sumirah.tugasempat.model.MatchesResponses
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PrevUnitTest {
    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var view: DetailView

    @Mock
    private lateinit var response: MatchesResponses

    private lateinit var presenter: MatchDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchDetailPresenter(context, view)
    }

    @Test
    fun testGetNextEvent() {
        val id = "584458"

        presenter.getEventDetailById(id)

        Mockito.verify(view).showLoading()

        argumentCaptor<DetailView>().apply {
            Mockito.verify(presenter).getEventDetailById(eq(id))
            firstValue.initValue(response.events[0])
        }

        Mockito.verify(view).hideLoading()
    }
}