package com.pabvazzam.test.ui.character.fav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pabvazzam.test.R
import com.pabvazzam.test.data.Character

class CharacterFavAdapter(private val characters: List<Character>) :
    RecyclerView.Adapter<CharacterFavViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterFavViewHolder {
        return CharacterFavViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_item_character_fav, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacterFavViewHolder, position: Int) {
        val character = characters[position]
        holder.render(character)
    }

    override fun getItemCount(): Int = characters.size
}