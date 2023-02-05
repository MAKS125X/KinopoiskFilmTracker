package com.example.tinkoffkinopoiskapi.ui.popularFilms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.tinkoffkinopoiskapi.R
import com.example.tinkoffkinopoiskapi.databinding.ItemFilmBinding
import com.example.tinkoffkinopoiskapi.model.Film

class FilmAdapter(

    private val onFilmItemListener: OnFilmItemListener
) : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    var filmsCollection: MutableList<Film> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film, parent, false)
        return FilmViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(filmsCollection[position])
        holder.binding.cardView.setOnLongClickListener{
            onFilmItemListener.changeFavourite(filmsCollection[position])
            //films[position].isFavourite = !films[position].isFavourite
            notifyItemChanged(position)
            //Toast.makeText(, "Избранное: ${films[position].isFavourite}", Toast.LENGTH_LONG)
            true
        }
        holder.binding.cardView.setOnClickListener{
            onFilmItemListener.getDetails(filmsCollection[position])
        }

    }

    override fun getItemCount(): Int {
        return filmsCollection.size
    }

    fun setList(list: MutableList<Film>){
        this.filmsCollection = list
    }

    class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemFilmBinding.bind(itemView)

        fun bind(film: Film) = with(binding){
            nameTextView.text = film.getShortenedNameRu()
            genreTextView.text = film.getInfoAboutFilm()
            isFavouriteImageView.isVisible = film.isFavourite

            film?.posterUrlPreview?.let{
                val imgUri = film.posterUrlPreview.toUri().buildUpon().scheme("https").build()
                bannerImageView.load(imgUri){
                    placeholder(R.drawable.animation_loading)
                    error(R.drawable.baseline_wifi_off_24)
                }
            }
        }
    }

    interface OnFilmItemListener {
        fun getDetails(film: Film)
        fun changeFavourite(film: Film)
    }
}