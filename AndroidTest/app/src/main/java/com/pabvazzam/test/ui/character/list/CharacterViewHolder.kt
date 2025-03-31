package com.pabvazzam.test.ui.character.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pabvazzam.test.R
import com.pabvazzam.test.data.Character
import com.pabvazzam.test.databinding.RvItemCharacterBinding

class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var _binding: RvItemCharacterBinding = RvItemCharacterBinding.bind(view)

    private val binding get() = _binding

    fun render(
        character: Character,
        onFavClicked: (Character, Boolean) -> Unit,
        favList: List<Character>
    ) {
        binding.rvItemCharacterName.text = character.name
        binding.rvItemCharacterSpecies.text = character.species
        binding.rvItemCharacterStatus.text = character.status
        binding.rvItemCharacterFav.text =
            if (favList.contains(character)) binding.root.resources.getString(
                R.string.rv_item_character_fav_remove
            ) else binding.root.resources.getString(
                R.string.rv_item_character_fav_add
            )
        Glide.with(binding.root.context)
            .load(character.image)
            .centerCrop()
            .into(binding.rvItemCharacterImage)

        binding.rvItemCharacterFav.setOnClickListener {
            onFavClicked(character, favList.contains(character))
        }
    }
}