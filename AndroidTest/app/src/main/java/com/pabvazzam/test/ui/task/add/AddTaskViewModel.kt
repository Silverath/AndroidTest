package com.pabvazzam.test.ui.task.add

import androidx.lifecycle.ViewModel
import com.pabvazzam.test.data.Task
import com.pabvazzam.test.usecase.AddTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState: StateFlow<AddTaskUiState> = _uiState.asStateFlow()

    fun onDateTimeSelected(date: Calendar) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault())
        val formattedDate = dateFormat.format(date.time)
        if (date.before(Calendar.getInstance())) {
            _uiState.update {
                _uiState.value.copy(
                    expirationDate = formattedDate,
                    selectDateError = true
                )
            }
        } else {
            _uiState.update {
                _uiState.value.copy(
                    expirationDate = formattedDate,
                    selectDateError = false
                )
            }
        }
    }

    fun onTitleEditTextChanged(text: String) {
        _uiState.update {
            _uiState.value.copy(
                title = text
            )
        }
    }

    fun onDescriptionEditTextChanged(text: String) {
        _uiState.update {
            _uiState.value.copy(
                description = text
            )
        }
    }

    fun onSaveTask(): Boolean {
        val task =
            Task(
                id = generateUniqueId(),
                title = _uiState.value.title,
                description = _uiState.value.description,
                expirationDate = _uiState.value.expirationDate
            )
        return addTaskUseCase(task).isSuccess
    }

    private fun generateUniqueId(): String {
        return UUID.randomUUID().toString()
    }
}
