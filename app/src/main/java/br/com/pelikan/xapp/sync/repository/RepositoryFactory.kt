package br.com.pelikan.xapp.sync.repository

interface RepositoryFactory {
    fun forSync(): SyncRepository
}