package amr.barakat.muzica.ui.listing

import amr.barakat.muzica.databinding.LoadingFooterBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

class SongsLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<SongsLoadStateAdapter.SongsLoadStateViewHolder>() {
    inner class SongsLoadStateViewHolder(private val binding: LoadingFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState !is LoadState.Loading
                errorIcon.isVisible = loadState !is LoadState.Loading
            }
        }

        init {
            binding.retryButton.setOnClickListener {
                retry.invoke()
            }
        }
    }

    override fun onBindViewHolder(holder: SongsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): SongsLoadStateViewHolder {
        val binding = LoadingFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SongsLoadStateViewHolder(binding)
    }
}