package br.com.pelikan.xapp.sync.repository

import br.com.pelikan.xapp.sync.repository.dummy.DummyRepositoryFactory

object Repository {
    fun with(): RepositoryFactory {
        return DummyRepositoryFactory()
    }
}