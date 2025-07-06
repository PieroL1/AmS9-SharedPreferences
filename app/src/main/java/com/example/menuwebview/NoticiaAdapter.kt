package com.example.menuwebview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoticiaAdapter(
    private val listaNoticias: List<Noticia>,
    private val onLongClick: (View, Int) -> Unit // Para el context menu
) : RecyclerView.Adapter<NoticiaAdapter.NoticiaViewHolder>() {

    inner class NoticiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        init {
            itemView.setOnLongClickListener {
                onLongClick(it, adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_noticia, parent, false)
        return NoticiaViewHolder(vista)
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val noticia = listaNoticias[position]
        holder.tvTitulo.text = noticia.titulo
        holder.tvDescripcion.text = noticia.descripcion
    }

    override fun getItemCount(): Int = listaNoticias.size
}
