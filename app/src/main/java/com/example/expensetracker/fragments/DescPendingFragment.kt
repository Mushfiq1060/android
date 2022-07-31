package com.example.expensetracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentDescPendingBinding
import com.example.expensetracker.model.Data
import com.example.expensetracker.viewModel.SharedViewModelExpense
import com.example.expensetracker.viewModel.SharedViewModelPending
import java.text.SimpleDateFormat
import java.util.*

class DescPendingFragment : Fragment() {

    private lateinit var binding: FragmentDescPendingBinding
    private val viewModel: SharedViewModelPending by activityViewModels()
    private val anotherViewModel: SharedViewModelExpense by activityViewModels()
    private lateinit var obj: Data
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDescPendingBinding.inflate(layoutInflater)
        viewModel.factsLiveData.observe(viewLifecycleOwner) {
            obj = it
            binding.textTitle.text = it.title
            binding.textDescription.text = it.description
            binding.pendingType.text = "Type : ${it.type} "
            binding.timeStamp.text = "Saved on ${it.time}"
        }
        binding.paidBtn.setOnClickListener {
            val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
            val currentDate: String = sdf.format(Date())
            obj.time = currentDate
            viewModel.delete(obj)
            anotherViewModel.insert(obj)
            findNavController().navigate(R.id.action_descPendingFragment_to_homeFragment)
        }
        return binding.root
    }
}