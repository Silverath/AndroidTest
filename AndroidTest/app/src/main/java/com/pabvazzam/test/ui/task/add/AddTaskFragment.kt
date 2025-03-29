package com.pabvazzam.test.ui.task.add

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.pabvazzam.test.R
import com.pabvazzam.test.databinding.FragmentAddTaskBinding
import com.pabvazzam.test.ui.showDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null

    private val binding get() = _binding!!

    private val viewModel: AddTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.addTitleEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onTitleEditTextChanged(text.toString())
        }

        binding.addDescriptionEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onDescriptionEditTextChanged(text.toString())
        }

        binding.addDateEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showDatePicker(
                    context,
                    binding.addDateEditText
                ) { dateTime -> viewModel.onDateTimeSelected(dateTime) }
                binding.addDateEditText.clearFocus()
            }
        }

        binding.addTaskSaveButton.setOnClickListener {
            if (viewModel.onSaveTask()) {
                Toast.makeText(
                    activity,
                    resources.getString(R.string.task_add_save_success),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(
                    activity,
                    resources.getString(R.string.task_add_save_failure),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.addTaskSaveButton.isEnabled = state.title.isNotBlank() &&
                            state.description.isNotBlank() &&
                            state.expirationDate.isNotBlank() &&
                            !state.selectDateError
                    binding.addDateEditText.text =
                        Editable.Factory.getInstance().newEditable(state.expirationDate)
                    if (state.selectDateError) {
                        binding.addDateInputLayout.error =
                            resources.getString(R.string.task_date_error)
                    } else {
                        binding.addDateInputLayout.error = null
                    }
                }
            }
        }
    }
}