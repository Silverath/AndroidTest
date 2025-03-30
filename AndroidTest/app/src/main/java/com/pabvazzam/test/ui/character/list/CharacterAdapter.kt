package com.pabvazzam.test.ui.character.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.pabvazzam.test.R
import com.pabvazzam.test.data.Character

class CharacterAdapter(
    private val onFavClicked: (Character) -> Unit
) :
    PagingDataAdapter<Character, CharacterViewHolder>(CharacterDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_character, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        if (character != null) {
            holder.render(character, onFavClicked)
        }
    }
}