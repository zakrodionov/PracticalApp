package com.zakrodionov.practicalapp.app.di

import org.koin.core.qualifier.named

object DIQualifiers {

    private const val FORMAT_CICERONE = "CICERONE"
    private const val FORMAT_NAVIGATION_HOLDER = "NAVIGATION_HOLDER"
    private const val FORMAT_ROUTER = "ROUTER"
    private const val FORMAT_REPOSITORY = "REPOSITORY"
    private const val FORMAT_FAKE = "FAKE"
    private const val FORMAT_OTHER = "OTHER"

    private const val KEY_TOKEN = "TOKEN"
    private const val KEY_HOST = "HOST"
    private const val KEY_WEB_SOCKET = "WEB_SOCKET"

    fun ciceroneQualifier(moduleQualifier: String) = qualifier(moduleQualifier, FORMAT_CICERONE)
    fun navigationHolderQualifier(moduleQualifier: String) = qualifier(moduleQualifier, FORMAT_NAVIGATION_HOLDER)
    fun routerQualifier(moduleQualifier: String) = qualifier(moduleQualifier, FORMAT_ROUTER)
    fun repositoryQualifier(moduleQualifier: String) = qualifier(moduleQualifier, FORMAT_REPOSITORY)
    fun fakeQualifier(moduleQualifier: String) = qualifier(moduleQualifier, FORMAT_FAKE)
    val tokenKeyQualifier = qualifier(KEY_TOKEN, FORMAT_OTHER)
    val hostKeyQualifier = qualifier(KEY_HOST, FORMAT_OTHER)
    val webSocketKeyQualifier = qualifier(KEY_WEB_SOCKET, FORMAT_OTHER)

    private fun qualifier(moduleQualifier: String, format: String) = named("${moduleQualifier}_$format")
}
