package amr.barakat.muzica.ui.listing

import amr.barakat.muzica.R
import amr.barakat.muzica.databinding.FragmentSongsListListBinding
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSongsListListBinding.bind(view)
        val layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }
        val songsListAdapter = SongsListRecyclerViewAdapter()

        binding.apply {
            list.layoutManager = layoutManager
            list.adapter = songsListAdapter.withLoadStateHeaderAndFooter(
                header = SongsLoadStateAdapter { songsListAdapter.retry() },
                footer = SongsLoadStateAdapter { songsListAdapter.retry() }
            )
        }

        viewModel.songs.observe(viewLifecycleOwner) {
            viewModel.viewModelScope.launch {

                songsListAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData = it)
            }
        }
        viewModel.getSongList()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            SongsListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}