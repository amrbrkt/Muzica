package amr.barakat.muzica.data.remote.source

import amr.barakat.muzica.data.model.Resource
import amr.barakat.muzica.data.model.ResponseData
import amr.barakat.muzica.data.model.Session
import amr.barakat.muzica.data.remote.ServiceGenerator
import amr.barakat.muzica.data.remote.services.Service
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteData @Inject constructor(
    private val serviceGenerator: ServiceGenerator,
) : RemoteDataSource {

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
            "NETWORK_ERROR"
        }
    }
}