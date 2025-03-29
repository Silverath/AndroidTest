package com.pabvazzam.test.ui.character.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pabvazzam.test.R
import com.pabvazzam.test.data.Character
import com.pabvazzam.test.databinding.FragmentCharacterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private var _binding: FragmentCharacterBinding? = null

    private lateinit var characterAdapter: CharacterAdapter

    private val binding get() = _binding!!

    private val viewModel: CharacterViewModel by viewModels()

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(context)
        val decoration = DividerItemDecoration(context, manager.orientation)
        characterAdapter = CharacterAdapter { character ->
            viewModel.onFavChanged(
                character
            ) { added -> onAddedFavCharacter(character, added) }
        }
        binding.characterRv.layoutManager = manager
        binding.characterRv.adapter =
            characterAdapter

        binding.characterRv.addItemDecoration(decoration)
    }

    private fun onAddedFavCharacter(character: Character, added: Boolean) {
        if (added) {
            Toast.makeText(
                binding.root.context,
                resources.getString(R.string.character_fav_success, character.name),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                binding.root.context,
                resources.getString(R.string.character_fav_failure, character.name),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is CharacterUiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                        }

                        is CharacterUiState.Error -> {
                            binding.progressBar.visibility = View.GONE

                            Toast.makeText(
                                binding.root.context,
                                resources.getString(R.string.character_list_load_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is CharacterUiState.Loading -> {
                            binding.progressBar.visibility =
                                View.VISIBLE
                        }
                    }

                }


            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        val view: View = binding.root
        initRecyclerView()
        viewModel.fetchCharacters(characterAdapter)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}