package com.pabvazzam.test.ui.character.fav

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pabvazzam.test.databinding.FragmentTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFavFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null

    private val binding get() = _binding!!

    private val viewModel: CharacterFavViewModel by viewModels()
}