package amr.barakat.muzica.data.remote.repo


import amr.barakat.muzica.data.model.Resource
import amr.barakat.muzica.data.model.Session
import amr.barakat.muzica.data.remote.source.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RepoImpl @Inject constructor(
    private val remoteRepo: RemoteDataSource,
    private val ioDispatcher: CoroutineContext
) : Repo {
    override suspend fun requestSongsList(): Flow<Resource<List<Session>>> {
        return flow {
            emit(remoteRepo.getSongsList())
        }
    }
}