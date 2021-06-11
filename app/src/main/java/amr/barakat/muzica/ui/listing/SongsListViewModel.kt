package amr.barakat.muzica.ui.listing

import amr.barakat.muzica.data.model.Session
import amr.barakat.muzica.data.remote.repo.Repo
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongsListViewModel @Inject constructor(private val repo: Repo) : ViewModel() {

    var songsPrivate = MutableLiveData<PagingData<Session>>()
    val songs: LiveData<PagingData<Session>> get() = songsPrivate

    fun getSongList() {
        viewModelScope.launch {
            repo.requestSongsList().collect {
                songsPrivate.value = it
            }
        }
    }

    fun search(query: String) {
        Log.d("TAG", "called on $query");
        viewModelScope.launch {
            repo.searchSongsList(query).collect {
                songsPrivate.value = it
            }
        }
    }


}