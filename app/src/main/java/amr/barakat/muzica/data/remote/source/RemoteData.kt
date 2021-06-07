package amr.barakat.muzica.data.remote.source

import amr.barakat.muzica.data.model.Resource
import amr.barakat.muzica.data.model.ResponseData
import amr.barakat.muzica.data.model.Session
import amr.barakat.muzica.data.remote.ServiceGenerator
import amr.barakat.muzica.data.remote.services.Service
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

private const val PAGE: Int = 1
private const val MAX_PAGES: Int = 5
private const val CLIENT_SIDE_ERROR = -100
class RemoteData @Inject constructor(
    private val serviceGenerator: ServiceGenerator,
) : RemoteDataSource, PagingSource<Int, Session>() {

    override suspend fun getSongsList(): Resource<List<Session>> {
        val service = serviceGenerator.createService(Service::class.java)
        return when (val response = processCall(service::getSongsList)) {
            is ResponseData -> {
                Resource.Success(response.data.sessions)
            }
            else -> {
                Resource.DataError(response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            CLIENT_SIDE_ERROR
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Session> {
        val pos = params.key ?: PAGE
        return try {
            val data: List<Session> = getSongsList().data!!
            LoadResult.Page(
                data = data,
                prevKey = if (pos == PAGE) null else pos - 1,
                nextKey = if (data.isEmpty() || pos == MAX_PAGES) null else pos + 1
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Session>): Int? {
        return null
    }
}