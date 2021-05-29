package amr.barakat.muzica.ui

import amr.barakat.muzica.R
import amr.barakat.muzica.data.model.Resource
import amr.barakat.muzica.data.model.Session
import amr.barakat.muzica.observe
import amr.barakat.muzica.ui.error.ErrorFragment
import amr.barakat.muzica.ui.listing.SongsListFragment
import amr.barakat.muzica.ui.loading.LoadingFragment
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
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
        Navigator.replaceFragment(
            supportFragmentManager,
            R.id.nav_host_fragment,
            ErrorFragment.newInstance()
        )
    }

    private fun showData(it: List<Session>) {
        Toast.makeText(this, "Got ${it.size} songs", Toast.LENGTH_SHORT).show()
        Navigator.replaceFragment(
            supportFragmentManager,
            R.id.nav_host_fragment,
            SongsListFragment.newInstance(2, it as ArrayList<Session>)
        )
    }

    private fun showLoading() {
        Navigator.replaceFragment(
            supportFragmentManager,
            R.id.nav_host_fragment,
            LoadingFragment.newInstance()
        )

    }


}