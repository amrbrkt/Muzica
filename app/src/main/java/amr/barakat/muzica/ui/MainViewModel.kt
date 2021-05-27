package amr.barakat.muzica.ui

import amr.barakat.muzica.data.model.Resource
import amr.barakat.muzica.data.model.Session
import amr.barakat.muzica.data.remote.repo.Repo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: Repo) : ViewModel() {

    var songsPrivate = MutableLiveData<Resource<List<Session>>>()
    val songs: LiveData<Resource<List<Session>>> get() = songsPrivate

    fun getSongList () {
        songsPrivate.value = Resource.Loading()
        viewModelScope.launch {
            repo.requestSongsList().collect {
                songsPrivate.value = it
            }
        }
    }


}