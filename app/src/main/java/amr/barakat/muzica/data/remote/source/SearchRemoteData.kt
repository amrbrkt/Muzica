package amr.barakat.muzica.data.remote.source

import amr.barakat.muzica.data.model.Resource
import amr.barakat.muzica.data.model.ResponseData
import amr.barakat.muzica.data.model.Session
import amr.barakat.muzica.data.remote.ServiceGenerator
import amr.barakat.muzica.data.remote.services.Service
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

private const val CLIENT_SIDE_ERROR = -100

class SearchRemoteData @Inject constructor(
    private val serviceGenerator: ServiceGenerator,
    private val query: String
) : RemoteDataSource, PagingSource<Int, Session>() {

    override suspend fun fetch(): Resource<List<Session>> {
        val service = serviceGenerator.createService(Service::class.java)
        return when (val response = processCall(service::searchSongsList)) {
            is ResponseData -> {
                Resource.Success(filter(response))
            }
            else -> {
                Resource.DataError(response as Int)
            }
        }
    }

    private fun filter(response: ResponseData): List<Session> {
        val shuffled = response.data.sessions.filter {
            it.current_track.title.contains(query, true)
        }.shuffled()
        Log.d("TAG", "${query}: Result ${response.data.sessions.size} filtered ${shuffled.size}}");
        return shuffled
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

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Session> = try {
        val data: List<Session> = fetch().data!!
        LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = null
        )
    } catch (ex: Exception) {
        LoadResult.Error(ex)
    }

    override fun getRefreshKey(state: PagingState<Int, Session>): Int? {
        return null
    }
}