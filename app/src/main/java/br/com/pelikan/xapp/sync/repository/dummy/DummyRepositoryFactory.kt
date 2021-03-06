package br.com.pelikan.xapp.sync.repository.dummy

import br.com.pelikan.xapp.sync.repository.SyncRepository
import br.com.pelikan.xapp.sync.repository.RepositoryFactory

class DummyRepositoryFactory : RepositoryFactory {

    override fun forSync(): SyncRepository {
        return DummySyncRepository()
    }
}