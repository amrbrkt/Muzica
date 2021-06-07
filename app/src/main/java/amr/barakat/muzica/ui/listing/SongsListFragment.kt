package amr.barakat.muzica.ui.listing

import amr.barakat.muzica.R
import amr.barakat.muzica.data.model.Session
import amr.barakat.muzica.databinding.FragmentSongsListListBinding
import amr.barakat.muzica.observe
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class SongsListFragment : Fragment(R.layout.fragment_songs_list_list) {

    private var columnCount = 2
    private val viewModel by viewModels<SongsListViewModel>()
    private var _binding: FragmentSongsListListBinding? = null
    private val binding get() = _binding!!

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
        val songsListAdapter = SongsListRecyclerViewAdapter()
        val footerAdapter = SongsLoadStateAdapter { songsListAdapter.retry() }
        binding.apply {
            list.layoutManager = layoutManager
            list.adapter = songsListAdapter.withLoadStateFooter(
                footer = footerAdapter
            )
        }
        songsListAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData = it)
    }
}
