package com.pabvazzam.test

import android.content.SharedPreferences
import com.google.gson.Gson
import com.pabvazzam.test.repository.TaskRepositoryImpl
import com.pabvazzam.test.ui.task.add.AddTaskUiState
import com.pabvazzam.test.ui.task.add.AddTaskViewModel
import com.pabvazzam.test.usecase.task.AddTaskUseCase
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AddTaskViewModelUnitTest {
    private lateinit var viewModel: AddTaskViewModel
    private lateinit var taskRepository: TaskRepositoryImpl
    private val sharedPreferences: SharedPreferences = mockk(relaxed = true)
    private val gson: Gson = mockk(relaxed = true)
    private val dispatcher = StandardTestDispatcher()

    private val futureDate: Calendar = Calendar.getInstance()
    private val pastDate: Calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault())
    private val text = "newtext"

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        clearAllMocks()
        Dispatchers.setMain(dispatcher)
        futureDate.set(2222, 12, 30, 23, 59)
        pastDate.set(1995, 12, 30, 23, 59)
        taskRepository = TaskRepositoryImpl(sharedPreferences, gson)
        viewModel = AddTaskViewModel(
            addTaskUseCase = AddTaskUseCase(taskRepository)
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when selected new future date, the ui state is set correctly`(): Unit = runTest {
        viewModel.onDateTimeSelected(futureDate)
        assertEquals(
            viewModel.uiState.value,
            AddTaskUiState(
                expirationDate = dateFormat.format(futureDate.time),
                selectDateError = false
            )
        )
    }

    @Test
    fun `when selected new past date, the ui state is set correctly`(): Unit = runTest {

        viewModel.onDateTimeSelected(pastDate)
        assertEquals(
            viewModel.uiState.value,
            AddTaskUiState(
                expirationDate = dateFormat.format(pastDate.time),
                selectDateError = true
            )
        )
    }

    @Test
    fun `when changing title, text is set correctly in the ui state text`(): Unit = runTest {

        viewModel.onTitleEditTextChanged(text)
        assertEquals(
            viewModel.uiState.value,
            AddTaskUiState(
                title = text
            )
        )
    }

    @Test
    fun `when changing description, text is set correctly in the ui state text`(): Unit = runTest {

        viewModel.onDescriptionEditTextChanged(text)
        assertEquals(
            viewModel.uiState.value,
            AddTaskUiState(
                description = text
            )
        )
    }

    @Test
    fun `when saving the task, the result is success`(): Unit = runTest {
        every { taskRepository.getTasks() }.returns(emptyList())
        val result = viewModel.onSaveTask()
        assertTrue(
            result
        )
    }
}