package br.com.pelikan.xapp.sync.repository

import br.com.pelikan.xapp.BuildConfig
import br.com.pelikan.xapp.sync.repository.dummy.DummyRepositoryFactory
import br.com.pelikan.xapp.sync.repository.server.ServerRepositoryFactory

object Repository {
    fun with(): RepositoryFactory {
        if(BuildConfig.DEBUG) {
            return DummyRepositoryFactory()
        }else{
            return ServerRepositoryFactory()
        }
    }
}