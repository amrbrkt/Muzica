package amr.barakat.muzica.data.remote.repo

import amr.barakat.muzica.data.model.Resource
import amr.barakat.muzica.data.model.Session
import kotlinx.coroutines.flow.Flow


interface Repo {
    suspend fun requestSongsList(): Flow<Resource<List<Session>>>
}