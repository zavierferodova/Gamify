package com.zavierdev.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zavierdev.gamify.ui.adapter.ListGameAdapter
import com.zavierdev.gamify.ui.detail.DetailGameActivity
import com.zavierdev.ui.favorite.databinding.FragmentFavoriteBinding
import com.zavierdev.ui.favorite.di.favoriteModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadKoinModules(favoriteModule)
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val gameAdapter = ListGameAdapter()

            gameAdapter.onItemClick = { game ->
                val intent = Intent(activity, DetailGameActivity::class.java)
                intent.putExtra(DetailGameActivity.EXTRA_GAME, game)
                startActivity(intent)
            }

            favoriteViewModel.game.observe(viewLifecycleOwner) { game ->
                if (game.isNotEmpty()) {
                    binding.tvEmpty.visibility = View.GONE
                    binding.rvFavoriteGame.visibility = View.VISIBLE
                    gameAdapter.setData(game)
                } else {
                    binding.tvEmpty.visibility = View.VISIBLE
                    binding.rvFavoriteGame.visibility = View.GONE
                }
            }

            with(binding.rvFavoriteGame) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = gameAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}