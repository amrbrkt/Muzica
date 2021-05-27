package amr.barakat.muzica.ui.main

import amr.barakat.muzica.R
import amr.barakat.muzica.data.model.Resource
import amr.barakat.muzica.data.model.Session
import amr.barakat.muzica.observe
import amr.barakat.muzica.ui.MainViewModel
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observe(viewModel.songs, ::handleSongsResponse)
        viewModel.getSongList()
    }

    private fun handleSongsResponse(resource: Resource<List<Session>>) {
        when (resource) {
            is Resource.Loading -> showLoading()
            is Resource.Success<List<Session>> -> resource.data?.let { showData(it) }
            else -> showError()
        }
    }

    private fun showError() {
        Toast.makeText(activity, "Error!!", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.showError)

    }

    private fun showData(it: List<Session>) {
        Toast.makeText(activity, "Got ${it.size} songs", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.showData)
    }

    private fun showLoading() {
        Toast.makeText(activity, "loading…", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.showLoading)

    }
}