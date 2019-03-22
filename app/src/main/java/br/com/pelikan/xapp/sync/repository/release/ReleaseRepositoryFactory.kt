package br.com.pelikan.xapp.sync.repository.release

import br.com.pelikan.xapp.sync.repository.SyncRepository
import br.com.pelikan.xapp.sync.repository.RepositoryFactory

class ServiceRepositoryFactory : RepositoryFactory {

    override fun forSync(): SyncRepository {
        return ServiceSyncRepository()
    }
}