package com.pabvazzam.test.ui.task.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pabvazzam.test.data.Task
import com.pabvazzam.test.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()
    private lateinit var allTasks: List<Task>

    fun fetchTasks() {
        viewModelScope.launch {
            val result = getTasksUseCase.invoke()
            result.onSuccess { data ->
                allTasks = data
                _uiState.update {
                    _uiState.value.copy(
                        tasks = data
                    )
                }
            }
        }
    }

    fun onFilterByStatus(checked: Boolean, status: String) {
        if (checked) {
            _uiState.update {
                _uiState.value.copy(
                    tasks = allTasks.filter { task -> task.status == status }
                )
            }
        } else {
            _uiState.update {
                _uiState.value.copy(
                    tasks = allTasks
                )
            }
        }
    }
}