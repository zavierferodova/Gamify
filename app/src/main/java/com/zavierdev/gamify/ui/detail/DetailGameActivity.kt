package com.zavierdev.gamify.ui.detail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.zavierdev.gamify.R
import com.zavierdev.gamify.core.data.Resource
import com.zavierdev.gamify.core.domain.model.Game
import com.zavierdev.gamify.databinding.ActivityDetailGameBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailGameActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_GAME = "EXTRA_GAME_DATA"
    }

    private lateinit var binding: ActivityDetailGameBinding
    private val detailGameViewModel: DetailGameViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailGame = intent.getParcelableExtra<Game>(EXTRA_GAME)
        fetchData(detailGame)
    }

    private fun fetchData(game: Game?) {
        game?.let {
            detailGameViewModel.getDetailGame(game.id).observe(this) {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressDetailGame.visibility = View.VISIBLE
                        binding.scrollDetailGame.visibility = View.GONE
                        binding.viewError.root.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        val detailGame = it.data
                        if (detailGame != null) {
                            binding.progressDetailGame.visibility = View.GONE
                            binding.scrollDetailGame.visibility = View.VISIBLE
                            binding.viewError.root.visibility = View.GONE
                            showView(detailGame)
                        }
                    }
                    is Resource.Error -> {
                        binding.progressDetailGame.visibility = View.GONE
                        binding.scrollDetailGame.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                    }
                    else -> {
                        // pass
                    }
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showView(game: Game) {
        with(binding) {
            Glide.with(this@DetailGameActivity)
                .load(game.backgroundImage)
                .into(ivDetailBackground)

            if (game.isFavorite) {
                fabFavorite.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_baseline_favorite_24,
                        null
                    )
                )
            } else {
                fabFavorite.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_baseline_favorite_border_24,
                        null
                    )
                )
            }

            tvDetailName.text = game.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvDetailDescription.text =
                    Html.fromHtml(game.description, Html.FROM_HTML_MODE_COMPACT)
            } else {
                @Suppress("DEPRECATION")
                tvDetailDescription.text = Html.fromHtml(game.description)
            }

            var isFavoriteGame = game.isFavorite
            fabFavorite.setOnClickListener {
                detailGameViewModel.setFavoriteTourism(game, !isFavoriteGame)
            }
        }
    }
}