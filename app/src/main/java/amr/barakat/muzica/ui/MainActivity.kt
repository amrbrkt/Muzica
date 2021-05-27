package amr.barakat.muzica.ui

import amr.barakat.muzica.R
import amr.barakat.muzica.data.model.Resource
import amr.barakat.muzica.data.model.Session
import amr.barakat.muzica.observe
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        observe(viewModel.songs, ::handleSongsResponse)
        viewModel.getSongList()
    }

    private fun handleSongsResponse(resource: Resource<List<Session>>) {
        when(resource) {
            is Resource.Loading -> showLoading()
            is Resource.Success<List<Session>> -> resource.data?.let { showData(it) }
            else -> showError()
        }
    }

    private fun showError() {
        Toast.makeText(this, "Error!!", Toast.LENGTH_LONG).show()

    }

    private fun showData(it: List<Session>) {
        Toast.makeText(this, "Got ${it.size} songs", Toast.LENGTH_LONG).show()

    }

    private fun showLoading() {
        Toast.makeText(this, "loading…", Toast.LENGTH_LONG).show()
    }
}