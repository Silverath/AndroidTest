package com.pabvazzam.test.ui.character.fav

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pabvazzam.test.data.Character
import com.pabvazzam.test.databinding.RvItemCharacterFavBinding

class CharacterFavViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var _binding: RvItemCharacterFavBinding = RvItemCharacterFavBinding.bind(view)

    private val binding get() = _binding

    fun render(character: Character) {
        binding.rvItemCharacterFavName.text = character.name
        binding.rvItemCharacterFavStatus.text = character.status
        binding.rvItemCharacterFavSpecies.text = character.species
        Glide.with(binding.root.context)
            .load(character.image)
            .centerCrop()
            .into(binding.rvItemCharacterFavImage)
    }
}