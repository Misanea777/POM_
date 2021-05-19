package com.example.mytrashyapp.ui.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.mytrashyapp.databinding.FragmentLibraryBinding
import com.example.mytrashyapp.ui.library.screens.genres.GenresFragment
import com.example.mytrashyapp.ui.library.screens.songs.SongsFragment
import com.google.android.material.tabs.TabLayoutMediator


class Library : Fragment() {

    private lateinit var binding: FragmentLibraryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLibraryBinding.inflate(layoutInflater)


        val fragmentList = arrayListOf<Fragment>(
            SongsFragment(),
            GenresFragment(),
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val titles = arrayOf("Songs", "Genres")
        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

}