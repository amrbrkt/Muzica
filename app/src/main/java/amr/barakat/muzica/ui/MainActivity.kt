package amr.barakat.muzica.ui

import amr.barakat.muzica.R
import amr.barakat.muzica.data.model.Session
import amr.barakat.muzica.observe
import amr.barakat.muzica.ui.error.ErrorFragment
import amr.barakat.muzica.ui.listing.SongsListFragment
import amr.barakat.muzica.ui.loading.LoadingFragment
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingData
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
        showData()
    }


    private fun showError() {
        Navigator.replaceFragment(
            supportFragmentManager,
            R.id.nav_host_fragment,
            ErrorFragment.newInstance()
        )
    }

    private fun showData() {
        Navigator.replaceFragment(
            supportFragmentManager,
            R.id.nav_host_fragment,
            SongsListFragment.newInstance(2)
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