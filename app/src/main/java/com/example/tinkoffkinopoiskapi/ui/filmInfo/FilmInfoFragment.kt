package com.example.tinkoffkinopoiskapi.ui.filmInfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.tinkoffkinopoiskapi.R
import com.example.tinkoffkinopoiskapi.databinding.FragmentFilmInfoBinding

//import com.example.tinkoffkinopoiskapi.filmInfo.FilmInfoFragmentArgs

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
        binding.filmNameTextView.text = filmInfoViewModel.name.value
        Log.d("Отладка", filmInfoViewModel.name.value.toString())

    }

    private fun initObservers() {
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
}