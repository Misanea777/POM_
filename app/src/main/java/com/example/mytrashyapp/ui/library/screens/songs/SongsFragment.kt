package com.example.mytrashyapp.ui.library.screens.songs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytrashyapp.R
import com.example.mytrashyapp.data.network.MusicApi
import com.example.mytrashyapp.data.network.Resource
import com.example.mytrashyapp.data.repository.MusicRep
import com.example.mytrashyapp.databinding.FragmentSongsBinding


import com.example.mytrashyapp.ui.library.screens.songs.adapters.SongRecycleViewAdapter
import com.example.mytrashyapp.ui.library.screens.songs.models.Song
import com.example.mytrashyapp.ui.base.BaseFragment


class SongsFragment : BaseFragment<SongsViewModel, FragmentSongsBinding, MusicRep>() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: SongRecycleViewAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager





    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {

        var dataSet = Array(30) {
            Song(
                    R.drawable.music_logo,
                    "Unkonwn Song",
                    "DJ Misanea"
            )
        }



        viewModel.songs.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    dataSet = it.value.songs
                }
                is Resource.Failure ->
                    Toast.makeText(requireContext(), "Failure during music fetching", Toast.LENGTH_LONG).show()
            }
        })

        viewManager = LinearLayoutManager(activity)
        viewAdapter = SongRecycleViewAdapter(dataSet)
        recyclerView = binding.songRecycleView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun getViewModel() = SongsViewModel::class.java


    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentSongsBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = MusicRep(remoteDataSrc.buildApi(MusicApi::class.java))

}