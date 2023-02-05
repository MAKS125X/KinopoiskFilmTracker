package com.example.tinkoffkinopoiskapi.ui.popularFilms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tinkoffkinopoiskapi.R
import com.example.tinkoffkinopoiskapi.data.api.FilmApiStatus
import com.example.tinkoffkinopoiskapi.databinding.FragmentPopularFilmsBinding
import com.example.tinkoffkinopoiskapi.model.Film
import kotlinx.coroutines.launch

//import com.example.tinkoffkinopoiskapi.popularFilms.PopularFilmsFragmentDirections

class PopularFilmsFragment : Fragment() {

    private lateinit var binding: FragmentPopularFilmsBinding
    private lateinit var adapter: FilmAdapter

    private val vm: PopularFilmsViewModel by viewModels()

    private fun initClicks() = with(binding) {
        refreshButton.setOnClickListener {
            vm.viewModelScope.launch {
                vm.startLoading()
            }
        }
    }

    private fun initObservers() {

        vm.filmList.observe(viewLifecycleOwner) { list ->
            adapter.setList(list)
            adapter.notifyDataSetChanged()
        }
        vm.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                FilmApiStatus.LOADING -> {
                    binding.lostConnectionLayout.visibility = View.VISIBLE
                    binding.refreshButton.visibility = View.GONE
                    binding.lostConnectionTextView.visibility = View.GONE
                    binding.networkStatusImageView.visibility = View.VISIBLE
                    binding.networkStatusImageView.setImageResource(R.drawable.animation_loading)

                }
                FilmApiStatus.ERROR -> {
                    binding.lostConnectionLayout.visibility = View.VISIBLE
                    binding.networkStatusImageView.visibility = View.VISIBLE
                    binding.refreshButton.visibility = View.VISIBLE
                    binding.lostConnectionTextView.visibility = View.VISIBLE
                    binding.networkStatusImageView.setImageResource(R.drawable.baseline_cloud_off_24)
                }
                FilmApiStatus.DONE -> {
                    binding.lostConnectionLayout.visibility = View.GONE
                }
                else -> {
                    binding.lostConnectionLayout.visibility = View.VISIBLE
                    binding.networkStatusImageView.visibility = View.VISIBLE
                    binding.refreshButton.visibility = View.VISIBLE
                    binding.lostConnectionTextView.visibility = View.VISIBLE
                    binding.networkStatusImageView.setImageResource(R.drawable.baseline_wifi_off_24)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPopularFilmsBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initRecyclerView()
        initClicks()
    }

    private fun initRecyclerView() = with(binding) {
        adapter = FilmAdapter(
            object : FilmAdapter.OnFilmItemListener {
                override fun getDetails(film: Film) {
                    val action =
                        PopularFilmsFragmentDirections.actionPopularFilmsFragmentToFilmInfoFragment(
                            film.filmId
                        )
                    findNavController().navigate(action)
                }

                override fun changeFavourite(film: Film) {
                    film.isFavourite = !film.isFavourite
                    //TODO:Nothing
                }
            }
        )
        filmRecyclerView.layoutManager = LinearLayoutManager(context)
        filmRecyclerView.adapter = adapter

    }

    companion object {
        fun newInstance() = PopularFilmsFragment()
    }
}