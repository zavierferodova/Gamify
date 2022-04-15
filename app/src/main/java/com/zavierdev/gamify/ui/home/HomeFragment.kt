package com.zavierdev.gamify.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zavierdev.gamify.core.data.Resource
import com.zavierdev.gamify.databinding.FragmentHomeBinding
import com.zavierdev.gamify.ui.adapter.ListGameAdapter
import com.zavierdev.gamify.ui.detail.DetailGameActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModel()
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
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

            homeViewModel.game.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        binding?.progressHome?.visibility = View.VISIBLE
                        binding?.rvGame?.visibility = View.GONE
                        binding?.viewError?.root?.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        val game = it.data
                        if (game != null) {
                            gameAdapter.setData(game)
                        }
                        binding?.progressHome?.visibility = View.GONE
                        binding?.rvGame?.visibility = View.VISIBLE
                        binding?.viewError?.root?.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        binding?.progressHome?.visibility = View.GONE
                        binding?.rvGame?.visibility = View.GONE
                        binding?.viewError?.root?.visibility = View.VISIBLE
                    }
                    else -> {}
                }
            }

            with(binding?.rvGame) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = gameAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}