package com.example.expensetracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.expensetracker.R
import com.example.expensetracker.adapter.ViewPagerAdapter
import com.example.expensetracker.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val viewPageAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        binding.viewPager.adapter = viewPageAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "Pending Expenses"
                1 -> tab.text = "List Of Expenses"
            }
        }.attach()
        return binding.root
    }
}