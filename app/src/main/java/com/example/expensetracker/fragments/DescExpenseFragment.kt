package com.example.expensetracker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.expensetracker.databinding.FragmentDescExpenseBinding
import com.example.expensetracker.viewModel.SharedViewModelExpense
import com.example.expensetracker.viewModel.SharedViewModelPending

class DescExpenseFragment : Fragment() {
    private lateinit var binding: FragmentDescExpenseBinding
    private val viewModel: SharedViewModelExpense by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDescExpenseBinding.inflate((layoutInflater))
        viewModel.factsLiveData.observe(viewLifecycleOwner) {
            binding.textTitle.text = it.title
            binding.textDescription.text = it.description
            binding.timeStamp.text = "Paid on ${it.time}"
            binding.expenseType.text = "Type : ${it.type}"
        }
        return binding.root
    }
}