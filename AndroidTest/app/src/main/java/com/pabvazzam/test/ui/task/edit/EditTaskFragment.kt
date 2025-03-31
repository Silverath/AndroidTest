package com.pabvazzam.test.ui.task.edit

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pabvazzam.test.R
import com.pabvazzam.test.TASK_STATUS_DONE
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

        binding.editStatusValue.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onTaskStatusChanged(isChecked)
        }

        binding.deleteTask.setOnClickListener {
            context?.let {
                val builder = AlertDialog.Builder(it)
                builder.setMessage(resources.getString(R.string.task_delete_confirmation_message))
                    .setCancelable(false)
                    .setPositiveButton(resources.getString(R.string.task_delete_yes)) { dialog, _ ->
                        if (viewModel.onDeleteTask()) {
                            Toast.makeText(
                                activity,
                                resources.getString(R.string.task_delete_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().popBackStack()
                        } else {
                            Toast.makeText(
                                activity,
                                resources.getString(R.string.task_delete_failure),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        dialog.dismiss()
                    }
                    .setNegativeButton(resources.getString(R.string.task_delete_no)) { dialog, _ ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        }

        binding.editTaskSaveButton.setOnClickListener {
            if (viewModel.onSaveTask()) {
                Toast.makeText(
                    activity,
                    resources.getString(R.string.task_edit_save_success),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(
                    activity,
                    resources.getString(R.string.task_edit_save_failure),
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
                    if (state is EditTaskUiState.Success) {
                        if (state.isFirstLoad) {

                            binding.editTitleEditText.text =
                                Editable.Factory.getInstance().newEditable(state.title)
                            binding.editDescriptionEditText.text =
                                Editable.Factory.getInstance().newEditable(state.description)
                            binding.editStatusValue.isChecked = (state.status == TASK_STATUS_DONE)
                            binding.progressBar.visibility = View.GONE

                            viewModel.onFirstLoadCompleted()
                        }

                        binding.editTaskSaveButton.isEnabled = state.title.isNotBlank() &&
                                state.description.isNotBlank() &&
                                state.expirationDate.isNotBlank()
                        binding.editDateEditText.text =
                            Editable.Factory.getInstance().newEditable(state.expirationDate)
                    }


                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}