package amr.barakat.muzica.data.remote.repo


import amr.barakat.muzica.data.model.Session
import amr.barakat.muzica.data.remote.ServiceGenerator
import amr.barakat.muzica.data.remote.source.RemoteData
import amr.barakat.muzica.data.remote.source.RemoteDataSource
import amr.barakat.muzica.data.remote.source.SearchRemoteData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RepoImpl @Inject constructor(
    private val serviceGenerator: ServiceGenerator,
    private val remoteData: RemoteData) : Repo {
    override suspend fun requestSongsList(): Flow<PagingData<Session>> {

        return Pager(
            config = PagingConfig(10, 4, false),
            pagingSourceFactory = { remoteData }
        ).flow
    }

    override suspend fun searchSongsList(query: String): Flow<PagingData<Session>> {
        return Pager(
            config = PagingConfig(10, 4, false),
            pagingSourceFactory = { SearchRemoteData(serviceGenerator, query) }
        ).flow
    }
}