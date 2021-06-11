package amr.barakat.muzica.data.remote.repo

import amr.barakat.muzica.data.model.Session
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow


interface Repo {
    suspend fun requestSongsList(): Flow<PagingData<Session>>
    suspend fun searchSongsList(query: String): Flow<PagingData<Session>>
}