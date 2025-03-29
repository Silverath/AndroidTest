package com.pabvazzam.test.ui.task.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
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
    private val getTasksUseCase: GetTasksUseCase,
    private val gson: Gson
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    fun fetchTasks() {
        viewModelScope.launch {
            val result = getTasksUseCase.invoke()
            result.onSuccess { data ->
                _uiState.update {
                    _uiState.value.copy(
                        tasks = data
                    )
                }
            }
        }
    }

    fun convertTaskToJson(task: Task): String {
        val jsonTask = gson.toJson(task)
        return jsonTask
    }

}