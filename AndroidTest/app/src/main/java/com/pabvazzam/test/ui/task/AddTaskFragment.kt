package com.pabvazzam.test.ui.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pabvazzam.test.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class AddTaskFragment : Fragment() {


    private val viewModel: AddTaskViewModel by viewModels()

    private lateinit var titleEditText: TextInputEditText
    private lateinit var descriptionEditText: TextInputEditText
    private lateinit var taskDateText: TextInputEditText
    private lateinit var taskDateInputLayout: TextInputLayout
    private lateinit var saveTaskButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val v: View = inflater.inflate(R.layout.fragment_add_task, container, false)

        titleEditText = v.findViewById(R.id.title_edit_text)
        descriptionEditText = v.findViewById(R.id.description_edit_text)
        taskDateInputLayout = v.findViewById(R.id.date_input_layout)
        taskDateText = v.findViewById(R.id.date_edit_text)
        saveTaskButton = v.findViewById(R.id.add_task_save)

        titleEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onTitleEditTextChanged(text.toString())
        }

        descriptionEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onDescriptionEditTextChanged(text.toString())
        }

        taskDateText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                onShowDatePicker()
                taskDateText.clearFocus()
            }
        }

        saveTaskButton.setOnClickListener {
            viewModel.onSaveTask()
            findNavController().popBackStack()
        }

        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    saveTaskButton.isEnabled = state.title.isNotEmpty() &&
                            state.description.isNotEmpty() &&
                            !state.selectDateError
                    taskDateText.text =
                        Editable.Factory.getInstance().newEditable(state.expirationDate)
                    if (state.selectDateError) {
                        taskDateInputLayout.error =
                            resources.getString(R.string.task_add_date_error)
                    } else {
                        taskDateInputLayout.error = null
                    }
                }
            }
        }
    }

    private fun onShowDatePicker() {
        val now = Calendar.getInstance()
        context?.let {
            DatePickerDialog(
                it, { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, monthOfYear, dayOfMonth)
                    showTimePicker(selectedDate, now)
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
        }?.show()
    }

    private fun showTimePicker(selectedDate: Calendar, now: Calendar) {
        context?.let {
            TimePickerDialog(
                it, { _, hours: Int, minutes: Int ->
                    selectedDate.set(Calendar.HOUR, hours)
                    selectedDate.set(Calendar.MINUTE, minutes)
                    taskDateText.clearFocus()
                    viewModel.onDateTimeSelected(selectedDate)
                },
                now.get(Calendar.HOUR),
                now.get(Calendar.MINUTE),
                true
            )
        }?.show()
    }
}