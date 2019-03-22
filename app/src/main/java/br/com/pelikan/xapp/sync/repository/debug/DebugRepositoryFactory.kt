package br.com.pelikan.xapp.sync.repository.debug

import br.com.pelikan.xapp.sync.repository.SyncRepository
import br.com.pelikan.xapp.sync.repository.RepositoryFactory

class DebugRepositoryFactory : RepositoryFactory {

    override fun forSync(): SyncRepository {
        return DebugSyncRepository()
    }
}