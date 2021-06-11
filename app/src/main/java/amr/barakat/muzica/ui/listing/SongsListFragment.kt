package amr.barakat.muzica.ui.listing

import amr.barakat.muzica.R
import amr.barakat.muzica.data.model.Session
import amr.barakat.muzica.databinding.FragmentSongsListListBinding
import amr.barakat.muzica.observe
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class SongsListFragment : Fragment(R.layout.fragment_songs_list_list) {

    private var columnCount = 2
    private val viewModel by viewModels<SongsListViewModel>()
    private var _binding: FragmentSongsListListBinding? = null
    private val binding get() = _binding!!
    private val songsListAdapter = SongsListRecyclerViewAdapter()
    private val footerAdapter = SongsLoadStateAdapter { songsListAdapter.retry() }
    private var queryTextChangedJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSongsListListBinding.bind(view)

        observe(viewModel.songs, ::showData)
        viewModel.getSongList()
        binding.searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearch(query)
                binding.searchText.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                queryTextChangedJob?.cancel()

                queryTextChangedJob = lifecycleScope.launch(Dispatchers.Main) {
                    delay(500)
                    performSearch(query)
                }

                return true
            }
        })


        songsListAdapter.addLoadStateListener { loadState ->
            binding.apply {
                loadView.container.isVisible = loadState.source.refresh is LoadState.Loading
                dataView.container.isVisible = loadState.source.refresh is LoadState.NotLoading
                errorView.container.isVisible = loadState.source.refresh is LoadState.Error
                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    songsListAdapter.itemCount < 1
                ) {
                    dataView.container.isVisible = false
                    emptyView.container.isVisible = true
                } else {
                    emptyView.container.isVisible = false
                }

                errorView.retryButton.setOnClickListener { songsListAdapter.retry() }
            }
        }

    }

    fun performSearch(query: String?) {
        if (query == null || query.isEmpty()) {
            viewModel.getSongList()
        } else {
            viewModel.search(query)
        }
        binding.dataView.list.scrollToPosition(0)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            SongsListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    private fun showData(it: PagingData<Session>) {
        val layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }

        binding.apply {
            dataView.list.layoutManager = layoutManager
            dataView.list.adapter = songsListAdapter.withLoadStateFooter(
                footer = footerAdapter
            )
        }
        songsListAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData = it)
    }
}
