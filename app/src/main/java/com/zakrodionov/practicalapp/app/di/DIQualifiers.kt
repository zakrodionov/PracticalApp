package com.zakrodionov.practicalapp.app.di

import org.koin.core.qualifier.named

object DIQualifiers {

    private const val FORMAT_CICERONE = "CICERONE"
    private const val FORMAT_NAVIGATION_HOLDER = "NAVIGATION_HOLDER"
    private const val FORMAT_ROUTER = "ROUTER"

    fun ciceroneQualifier(moduleQualifier: String) = qualifier(moduleQualifier, FORMAT_CICERONE)
    fun navigationHolderQualifier(moduleQualifier: String) = qualifier(moduleQualifier, FORMAT_NAVIGATION_HOLDER)
    fun routerQualifier(moduleQualifier: String) = qualifier(moduleQualifier, FORMAT_ROUTER)

    private fun qualifier(moduleQualifier: String, format: String) = named("${moduleQualifier}_$format")
}
