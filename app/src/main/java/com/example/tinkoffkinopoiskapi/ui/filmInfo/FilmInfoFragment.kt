package com.example.tinkoffkinopoiskapi.ui.filmInfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.tinkoffkinopoiskapi.R
import com.example.tinkoffkinopoiskapi.data.api.FilmApiStatus
import com.example.tinkoffkinopoiskapi.databinding.FragmentFilmInfoBinding
import kotlinx.coroutines.launch

class FilmInfoFragment : Fragment() {

    private lateinit var binding: FragmentFilmInfoBinding

    private val args by navArgs<FilmInfoFragmentArgs>()
    private val filmInfoViewModel: FilmInfoViewModel by viewModels {
        FilmInfoFactory(
            args.filmForGettingInfo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilmInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initClicks()
        //binding.filmNameTextView.text = filmInfoViewModel.name.value
    }

    private fun setElementsVisible(isVisible: Boolean) {
        when (isVisible) {
            true -> {
                binding.filmGenresTextView.visibility = View.VISIBLE
                binding.filmPhotoImageView.visibility = View.VISIBLE
                binding.filmCountriesTextView.visibility = View.VISIBLE
                binding.filmDescriptionTextView.visibility = View.VISIBLE
                binding.filmNameTextView.visibility = View.VISIBLE
            }
            false -> {
                binding.filmGenresTextView.visibility = View.GONE
                binding.filmPhotoImageView.visibility = View.GONE
                binding.filmCountriesTextView.visibility = View.GONE
                binding.filmDescriptionTextView.visibility = View.GONE
                binding.filmNameTextView.visibility = View.GONE
            }
        }

    }

    private fun initObservers() {
        filmInfoViewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                FilmApiStatus.LOADING -> {
                    setElementsVisible(false)
                    binding.lostConnectionLayout.visibility = View.VISIBLE
                    binding.refreshButton.visibility = View.GONE
                    binding.lostConnectionTextView.visibility = View.GONE
                    binding.networkStatusImageView.visibility = View.VISIBLE
                    binding.networkStatusImageView.setImageResource(R.drawable.animation_loading)

                }
                FilmApiStatus.ERROR -> {
                    setElementsVisible(false)
                    binding.lostConnectionLayout.visibility = View.VISIBLE
                    binding.networkStatusImageView.visibility = View.VISIBLE
                    binding.refreshButton.visibility = View.VISIBLE
                    binding.lostConnectionTextView.visibility = View.VISIBLE
                    binding.networkStatusImageView.setImageResource(R.drawable.baseline_cloud_off_24)
                }
                FilmApiStatus.DONE -> {
                    setElementsVisible(true)
                    binding.lostConnectionLayout.visibility = View.GONE
                }
                else -> {
                    setElementsVisible(false)
                    binding.lostConnectionLayout.visibility = View.VISIBLE
                    binding.networkStatusImageView.visibility = View.VISIBLE
                    binding.refreshButton.visibility = View.VISIBLE
                    binding.lostConnectionTextView.visibility = View.VISIBLE
                    binding.networkStatusImageView.setImageResource(R.drawable.baseline_wifi_off_24)
                }
            }
        }

        filmInfoViewModel.name.observe(viewLifecycleOwner) {
            binding.filmNameTextView.text = filmInfoViewModel.name.value
        }
        filmInfoViewModel.description.observe(viewLifecycleOwner) {
            binding.filmDescriptionTextView.text = filmInfoViewModel.description.value
        }
        filmInfoViewModel.genres.observe(viewLifecycleOwner) {
            binding.filmGenresTextView.text =
                if (filmInfoViewModel.genres.value != null)
                    "Жанры: ${filmInfoViewModel.genres.value?.joinToString(", ")}"
                else "Жанры: не указаны"
        }
        filmInfoViewModel.countries.observe(viewLifecycleOwner) {
            binding.filmCountriesTextView.text =
                if (filmInfoViewModel.countries.value != null)
                    "Страны: ${filmInfoViewModel.countries.value?.joinToString(", ")}"
                else "Страны: не указаны"
        }
        filmInfoViewModel.posterUrl.observe(viewLifecycleOwner) { url ->
            url?.let {
                val imgUri = url.toUri().buildUpon().scheme("https").build()
                binding.filmPhotoImageView.load(imgUri) {
                    placeholder(R.drawable.animation_loading)
                    error(R.drawable.baseline_wifi_off_24)
                }
            }
        }
    }

    private fun initClicks() {
        binding.refreshButton.setOnClickListener {
            filmInfoViewModel.viewModelScope.launch {
                filmInfoViewModel.startLoading()
            }
        }
    }
}