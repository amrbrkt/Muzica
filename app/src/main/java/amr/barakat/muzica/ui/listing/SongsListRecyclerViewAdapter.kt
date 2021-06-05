package amr.barakat.muzica.ui.listing

import amr.barakat.muzica.R
import amr.barakat.muzica.data.model.Session
import amr.barakat.muzica.dummy.DummyContent.DummyItem
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class SongsListRecyclerViewAdapter :
    PagingDataAdapter<Session, SongsListRecyclerViewAdapter.ViewHolder>(dataDiffCallback) {

    companion object {
        private val dataDiffCallback = object : DiffUtil.ItemCallback<Session>() {
            override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean {
                return oldItem.name.equals(newItem.name, true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_song, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.title.text = item.current_track.title
            holder.playCount.text = item.listener_count.toString()
            holder.genre.text = item.genres.joinToString(", ")

            Glide.with(holder.itemView)
                .load(item.current_track.artwork_url)
                .centerCrop()
                .placeholder(
                    ColorDrawable(
                        holder.itemView.resources.getColor(
                            R.color.primaryColor,
                            null
                        )
                    )
                )
                .into(holder.cover)
        }


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val playCount: TextView = view.findViewById(R.id.count)
        val genre: TextView = view.findViewById(R.id.genres)
        val cover: ImageView = view.findViewById(R.id.albumCover)

        override fun toString(): String {
            return super.toString() + " '" + title.text + "'"
        }
    }
}