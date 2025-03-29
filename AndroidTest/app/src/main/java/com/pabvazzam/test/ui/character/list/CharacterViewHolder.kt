package com.pabvazzam.test.ui.character.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pabvazzam.test.data.Character
import com.pabvazzam.test.databinding.RvItemCharacterBinding

class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var _binding: RvItemCharacterBinding = RvItemCharacterBinding.bind(view)

    private val binding get() = _binding

    fun render(character: Character, onCharacterClicked: (Character) -> Unit) {
        binding.rvItemCharacterName.text = character.name
        binding.rvItemCharacterSpecies.text = character.species
        binding.rvItemCharacterStatus.text = character.status
        Glide.with(binding.root.context)
            .load(character.image)
            .centerCrop()
            .into(binding.rvItemCharacterImage)
    }
}