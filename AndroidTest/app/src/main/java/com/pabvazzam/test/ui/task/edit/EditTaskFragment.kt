package com.pabvazzam.test.ui.task.edit

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
import androidx.navigation.fragment.navArgs
import com.pabvazzam.test.R
import com.pabvazzam.test.databinding.FragmentEditTaskBinding
import com.pabvazzam.test.ui.showDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditTaskFragment : Fragment() {

    private var _binding: FragmentEditTaskBinding? = null

    private val binding get() = _binding!!

    private val viewModel: EditTaskViewModel by viewModels()

    private val args: EditTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.fetchTask(args.taskId)

        binding.editTitleEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onTitleEditTextChanged(text.toString())
        }

        binding.editDescriptionEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onDescriptionEditTextChanged(text.toString())
        }

        binding.editDateEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showDatePicker(
                    context,
                    binding.editDateEditText
                ) { dateTime -> viewModel.onDateTimeSelected(dateTime) }
                binding.editDateEditText.clearFocus()
            }
        }

        binding.editTaskSaveButton.setOnClickListener {
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
                    binding.editTaskSaveButton.isEnabled = state.title.isNotBlank() &&
                            state.description.isNotBlank() &&
                            state.expirationDate.isNotBlank() &&
                            !state.selectDateError
                    binding.editDateEditText.text =
                        Editable.Factory.getInstance().newEditable(state.expirationDate)
                    if (state.selectDateError) {
                        binding.editDateInputLayout.error =
                            resources.getString(R.string.task_add_date_error)
                    } else {
                        binding.editDateInputLayout.error = null
                    }
                }
            }
        }
    }
}