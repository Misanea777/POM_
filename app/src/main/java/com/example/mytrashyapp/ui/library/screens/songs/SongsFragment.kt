package com.example.mytrashyapp.ui.library.screens.songs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrashyapp.R
import com.example.mytrashyapp.databinding.FragmentSongsBinding
import com.example.mytrashyapp.ui.MainActivity
import com.example.mytrashyapp.ui.auth.AuthViewModel

import com.example.mytrashyapp.ui.library.screens.songs.adapters.SongRecycleViewAdapter
import com.example.mytrashyapp.ui.library.screens.songs.models.Song
import com.example.mytrashyapp.ui.util.handleApiError
import com.example.mytrashyapp.util.Constants
import com.example.mytrashyapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongsFragment : Fragment() {

    private lateinit var binding: FragmentSongsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: SongRecycleViewAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val viewModel by viewModels<SongsViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSongsBinding.inflate(layoutInflater)

        return binding.getRoot()
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {

//        val dataSet = Array(30) {
//            Song(
//                    R.drawable.music_logo,
//                    "Unkonwn Song",
//                    "DJ Misanea"
//            )
//        }


        initRecycleView()

        viewModel.getSongs(30)

        viewModel.songsList.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success ->
                    viewAdapter.updateDataSet(it.value.songs.toTypedArray())
                is Resource.Failure -> handleApiError(it) {} // later for implementation
            }
        })
    }

    fun initRecycleView() {
        val playerControlView = (activity as? MainActivity)?.playerControlView
        val lastPosition = playerControlView?.player?.currentWindowIndex ?: -1

        viewManager = LinearLayoutManager(activity)
        viewAdapter = SongRecycleViewAdapter(playerControlView, emptyArray<Song>(), lastPosition)
        recyclerView = binding.songRecycleView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }


    }

}