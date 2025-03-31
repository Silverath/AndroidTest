package com.pabvazzam.test.ui.task.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pabvazzam.test.TASK_STATUS_DONE
import com.pabvazzam.test.TASK_STATUS_PENDING
import com.pabvazzam.test.data.Task
import com.pabvazzam.test.usecase.task.DeleteTaskUseCase
import com.pabvazzam.test.usecase.task.GetTaskToEditUseCase
import com.pabvazzam.test.usecase.task.SaveEditedTaskUseCase
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
    private val getTaskToEditUseCase: GetTaskToEditUseCase,
    private val saveEditedTaskUseCase: SaveEditedTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
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

    fun onFirstLoadCompleted() {
        _uiState.update {
            (_uiState.value as EditTaskUiState.Success).copy(
                isFirstLoad = false
            )
        }
    }

    fun onDateTimeSelected(date: Calendar) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault())
        val formattedDate = dateFormat.format(date.time)
        _uiState.update {
            (_uiState.value as EditTaskUiState.Success).copy(
                expirationDate = formattedDate
            )
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

    fun onDeleteTask(): Boolean {

        return deleteTaskUseCase((_uiState.value as EditTaskUiState.Success).task).isSuccess
    }

    fun onSaveTask(): Boolean {
        val state = (_uiState.value as EditTaskUiState.Success)
        val task =
            Task(
                id = state.task.id,
                title = state.title,
                description = state.description,
                status = state.status,
                expirationDate = state.expirationDate
            )
        return saveEditedTaskUseCase(task).isSuccess
    }

    fun onTaskStatusChanged(checked: Boolean) {
        val status = if (checked) TASK_STATUS_DONE else TASK_STATUS_PENDING
        _uiState.update {
            (_uiState.value as EditTaskUiState.Success).copy(
                status = status
            )
        }
    }
}