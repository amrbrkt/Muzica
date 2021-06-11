package amr.barakat.muzica.di

import amr.barakat.muzica.data.remote.repo.Repo
import amr.barakat.muzica.data.remote.repo.RepoImpl
import amr.barakat.muzica.data.remote.source.RemoteData
import amr.barakat.muzica.data.remote.source.RemoteDataSource
import amr.barakat.muzica.data.remote.source.SearchRemoteData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideRepository(repo: RepoImpl): Repo

    @Binds
    @Singleton
    abstract fun provideRemoteDataSource(remoteDataSource: RemoteData): RemoteDataSource

   /* @Binds
    @Singleton
    abstract fun provideSearchDataSource(remoteDataSource: SearchRemoteData): RemoteDataSource
*/
}