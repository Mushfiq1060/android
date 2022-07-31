package com.example.expensetracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.adapter.ExpenseRvAdapter
import com.example.expensetracker.databinding.FragmentExpenseBinding
import com.example.expensetracker.model.Data
import com.example.expensetracker.viewModel.SharedViewModelExpense
import com.example.expensetracker.viewModel.SharedViewModelPending

class ExpenseFragment : Fragment(), ExpenseRvAdapter.OnItemClickListener {
    private lateinit var binding: FragmentExpenseBinding
    private val viewModel: SharedViewModelExpense by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpenseBinding.inflate(layoutInflater)
        val expenseRvAdapter = ExpenseRvAdapter(this)
        val expenseRV = binding.listExpenseRV
        expenseRV.layoutManager = LinearLayoutManager(requireContext())
        expenseRV.adapter = expenseRvAdapter
        viewModel.allData.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                expenseRvAdapter.updateList(it)
            }
        })
        /* For search in expense list*/
        binding.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.search.clearFocus()
                viewModel.searchData(query!!).observe(viewLifecycleOwner, Observer { list ->
                    list?.let {
                        expenseRvAdapter.updateList(it)
                    }
                })
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchData(newText!!).observe(viewLifecycleOwner, Observer { list ->
                    list?.let {
                        expenseRvAdapter.updateList(it)
                    }
                })
                return false
            }
        })
        return binding.root
    }
    override fun onItemClick(obj: Data) {
        viewModel.update(obj)
        findNavController().navigate(R.id.action_homeFragment_to_descExpenseFragment)
    }
}