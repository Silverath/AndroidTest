package com.pabvazzam.test.ui.task

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState: StateFlow<AddTaskUiState> = _uiState.asStateFlow()
    private var selectedDate: Calendar = Calendar.getInstance()

    fun onDateSelected(year: Int, monthOfYear: Int, dayOfMonth: Int) {

        val selected = Calendar.getInstance()
        selected.set(year, monthOfYear, dayOfMonth)
        //val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        // formattedDate = dateFormat.format(selectedDate.time)

        selectedDate = selected
        // Update the TextView to display the selected date with the "Selected Date: " prefix
    }
}
