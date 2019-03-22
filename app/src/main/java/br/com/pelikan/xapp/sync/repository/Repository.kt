package br.com.pelikan.xapp.sync.repository

import br.com.pelikan.xapp.sync.repository.release.ServiceRepositoryFactory

object Repository {
    fun with(): RepositoryFactory {
        return ServiceRepositoryFactory()
    }
}