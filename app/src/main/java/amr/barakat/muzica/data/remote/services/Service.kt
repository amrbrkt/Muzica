package amr.barakat.muzica.data.remote.services

import amr.barakat.muzica.data.model.ResponseData
import amr.barakat.muzica.data.remote.LISTING_END_POINT
import amr.barakat.muzica.data.remote.SEARCH_END_POINT
import retrofit2.Response
import retrofit2.http.GET

interface Service {
    @GET(LISTING_END_POINT)
    suspend fun getSongsList(): Response<ResponseData>

    @GET(SEARCH_END_POINT)
    suspend fun searchSongsList(): Response<ResponseData>
}