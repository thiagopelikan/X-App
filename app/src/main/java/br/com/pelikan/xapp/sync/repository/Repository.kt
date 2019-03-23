package br.com.pelikan.xapp.sync.repository

import br.com.pelikan.xapp.sync.repository.server.ServerRepositoryFactory

object Repository {
    fun with(): RepositoryFactory {
        return ServerRepositoryFactory()
    }
}