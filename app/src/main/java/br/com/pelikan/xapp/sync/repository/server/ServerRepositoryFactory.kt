package br.com.pelikan.xapp.sync.repository.server

import br.com.pelikan.xapp.sync.repository.SyncRepository
import br.com.pelikan.xapp.sync.repository.RepositoryFactory

class ServerRepositoryFactory : RepositoryFactory {

    override fun forSync(): SyncRepository {
        return ServerSyncRepository()
    }
}