package com.example.expensetracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.adapter.PendingRvAdapter
import com.example.expensetracker.databinding.FragmentPendingBinding
import com.example.expensetracker.model.Data
import com.example.expensetracker.viewModel.SharedViewModelPending
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class PendingFragment : Fragment(), PendingRvAdapter.OnItemClickListener {

    private lateinit var binding: FragmentPendingBinding
    private val viewModel: SharedViewModelPending by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPendingBinding.inflate(layoutInflater)
        val pendingRvAdapter = PendingRvAdapter(this)
        val pendingRV = binding.pendingExpenseRV
        pendingRV.layoutManager = LinearLayoutManager(requireContext())
        pendingRV.adapter = pendingRvAdapter
        viewModel.allData.observe(viewLifecycleOwner){ list ->
            list?.let {
                pendingRvAdapter.updateList(it)
            }
        }
        val calendar = Calendar.getInstance()
        viewModel.readFromDataStore.observe(viewLifecycleOwner) {
            val previousMonth = it.toInt() + 1
            viewModel.allRecurrent.observe(viewLifecycleOwner) { list ->
                GlobalScope.launch {
                    viewModel.autoUpdateRecurrent(previousMonth, list)
                }
            }
        }
        viewModel.saveToDataStore(calendar.get(Calendar.MONTH).toString())
        binding.FABtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addPendingFragment)
        }
        return binding.root
    }
    override fun onItemClick(obj: Data) {
        viewModel.update(obj)
        findNavController().navigate(R.id.action_homeFragment_to_descPendingFragment)
    }
}

