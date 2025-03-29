package com.pabvazzam.test.ui.character.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pabvazzam.test.databinding.FragmentCharacterFavBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFavFragment : Fragment() {
    private var _binding: FragmentCharacterFavBinding? = null

    private val binding get() = _binding!!

    private val viewModel: CharacterFavViewModel by viewModels()

    private fun initRecyclerView() {
        viewModel.fetchCharacters()
        val manager = LinearLayoutManager(context)
        val decoration = DividerItemDecoration(context, manager.orientation)
        binding.characterFavRv.layoutManager = manager
        binding.characterFavRv.adapter =
            CharacterFavAdapter(viewModel.uiState.value.characters)
        binding.characterFavRv.addItemDecoration(decoration)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterFavBinding.inflate(inflater, container, false)
        val view = binding.root

        initRecyclerView()

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}