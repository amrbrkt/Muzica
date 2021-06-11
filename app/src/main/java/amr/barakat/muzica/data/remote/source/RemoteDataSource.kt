package amr.barakat.muzica.data.remote.source

import amr.barakat.muzica.data.model.Resource
import amr.barakat.muzica.data.model.Session


interface RemoteDataSource {
    suspend fun fetch(): Resource<List<Session>>
}