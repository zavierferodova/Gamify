package com.zavierdev.gamify.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zavierdev.gamify.R
import com.zavierdev.gamify.databinding.ItemCardGameBinding
import com.zavierdev.gamify.core.domain.model.Game

class ListGameAdapter : RecyclerView.Adapter<ListGameAdapter.ViewHolder>() {
    private var listGame = ArrayList<Game>()
    var onItemClick: ((Game) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListGame: List<Game>) {
        listGame.clear()
        listGame.addAll(newListGame)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_card_game,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listGame[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listGame.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCardGameBinding.bind(itemView)

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listGame[adapterPosition])
            }
        }

        fun bind(game: Game) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(game.backgroundImage)
                    .into(ivImage)
                tvName.text = game.name
            }
        }
    }
}