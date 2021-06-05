package amr.barakat.muzica.ui

import amr.barakat.muzica.R
import amr.barakat.muzica.ui.listing.SongsListViewModel
import amr.barakat.muzica.ui.listing.SongsListFragment
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: SongsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        showData()
    }

    private fun showData() {
        Navigator.replaceFragment(
            supportFragmentManager,
            R.id.nav_host_fragment,
            SongsListFragment.newInstance(2)
        )
    }

}