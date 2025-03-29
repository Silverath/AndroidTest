package com.pabvazzam.test.ui.task.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pabvazzam.test.usecase.GetTaskToEditUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val getTaskToEditUseCase: GetTaskToEditUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<EditTaskUiState>(EditTaskUiState.Loading)
    val uiState: StateFlow<EditTaskUiState> = _uiState.asStateFlow()

    fun fetchTask(taskId: String) {
        viewModelScope.launch {
            val result = getTaskToEditUseCase.invoke(taskId)
            result.onSuccess { task ->
                _uiState.update {
                    EditTaskUiState.Success(task = task)
                }
            }
        }
    }

    fun onDateTimeSelected(date: Calendar) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault())
        val formattedDate = dateFormat.format(date.time)
        if (date.before(Calendar.getInstance())) {
            _uiState.update {
                (_uiState.value as EditTaskUiState.Success).copy(
                    expirationDate = formattedDate,
                    selectDateError = true
                )
            }
        } else {
            _uiState.update {
                (_uiState.value as EditTaskUiState.Success).copy(
                    expirationDate = formattedDate,
                    selectDateError = false
                )
            }
        }
    }

    fun onTitleEditTextChanged(text: String) {
        _uiState.update {
            (_uiState.value as EditTaskUiState.Success).copy(
                title = text
            )
        }
    }

    fun onDescriptionEditTextChanged(text: String) {
        _uiState.update {
            (_uiState.value as EditTaskUiState.Success).copy(
                description = text
            )
        }
    }

    fun onSaveTask(): Boolean {

        return true
    }
}