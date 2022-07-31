package com.example.expensetracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentAddPendingBinding
import com.example.expensetracker.model.Data
import com.example.expensetracker.viewModel.SharedViewModelExpense
import com.example.expensetracker.viewModel.SharedViewModelPending
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class AddPendingFragment : Fragment() {

    private lateinit var binding: FragmentAddPendingBinding
    private val viewModel: SharedViewModelPending by activityViewModels()
    var type: String = "Recurrent"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPendingBinding.inflate(layoutInflater)
        binding.addPending = this
        val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
        val calendar = Calendar.getInstance()
        binding.timeStamp.text = sdf.format(calendar.time)
        binding.addExpenseBtn.setOnClickListener {
            val title = binding.titleText.text.toString()
            val description = binding.descriptionText.text.toString()
            val amount = binding.amountText.text.toString()
            val currentDate = sdf.format(calendar.time)
            if(title.isEmpty() || description.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this.context, "Please Insert All Field", Toast.LENGTH_LONG).show()
            } else {
                val data = Data(null, title, description, currentDate, amount, type)
                GlobalScope.launch {
                    viewModel.insert(data)
                }
                findNavController().navigate(R.id.action_addPendingFragment_to_homeFragment)
            }
        }
        return binding.root
    }
    fun onRadioButtonClicked(flag: Int) {
        if(flag == 1) {
            type = "Recurrent"
        } else {
            type = "Random"
        }
    }
}