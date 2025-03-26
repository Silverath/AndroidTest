package com.pabvazzam.test.ui.task

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddTaskFragment : Fragment() {

    private val viewModel: AddTaskViewModel by viewModels()

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun showDatePicker() {
        context?.let {
            DatePickerDialog(
                it, { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    viewModel.onDateSelected(year, monthOfYear, dayOfMonth)
                    // Create a new Calendar instance to hold the selected date
                    val selectedDate = Calendar.getInstance()
                    // Set the selected date using the values received from the DatePicker dialog
                    selectedDate.set(year, monthOfYear, dayOfMonth)
                    // Create a SimpleDateFormat to format the date as "dd/MM/yyyy"
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    // Format the selected date into a string
                    val formattedDate = dateFormat.format(selectedDate.time)
                    // Update the TextView to display the selected date with the "Selected Date: " prefix

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }?.show()
    }
}