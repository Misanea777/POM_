package com.example.mytrashyapp


import com.example.mytrashyapp.data.model.Songs
import com.example.mytrashyapp.data.remote.MusicApi
import com.example.mytrashyapp.data.repository.MusicRepository
import com.example.mytrashyapp.ui.library.screens.songs.SongsViewModel
import com.example.mytrashyapp.ui.library.screens.songs.adapters.SongRecycleViewAdapter
import com.example.mytrashyapp.ui.library.screens.songs.models.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class ExampleUnitTest {
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun onSuccessfulRequest_shouldUpdateUI() = runBlockingTest {
        val songRecycleViewAdapterMock = mock<SongRecycleViewAdapter>()
        val apiServiceMock = mock<MusicApi>()
        val musicRepository = mock<MusicRepository>()
        val viewModel = mock<SongsViewModel>()


        val songStub = mock<Song>()
        whenever(songStub.name).thenReturn("test name")
        whenever(songStub.artist).thenReturn("test artist")

        val songsStub = mock<Songs>()
        whenever(songsStub.songs).thenReturn(arrayListOf(songStub))

        whenever(apiServiceMock.getSongs(10)).thenReturn(songsStub)

        val expectedDataSet = arrayOf(
            Song(
                0,
                songStub.name,
                songStub. artist,
            )
        )

        viewModel.getSongs(10);

        verify(apiServiceMock).getSongs(10)
        verify(musicRepository).getSongs(10)
        verify(viewModel).getSongs(10)
        verify(songRecycleViewAdapterMock).updateDataSet(expectedDataSet)

    }

    @Test
    fun onFetchErrorRequest_shouldPresentErrorAlertDialog() = runBlockingTest {
        val songRecycleViewAdapterMock = mock<SongRecycleViewAdapter>()
        val apiServiceMock = mock<MusicApi>()
        val musicRepository = mock<MusicRepository>()
        val viewModel = mock<SongsViewModel>()

        val errorMessage = "Network Error"
        val error = Error(errorMessage)
        whenever(apiServiceMock.getSongs(10)).thenThrow(error)

        viewModel.getSongs(10);
        verify(apiServiceMock).getSongs(10)
        verify(musicRepository).getSongs(10)
        verify(viewModel).getSongs(10)
    }
}