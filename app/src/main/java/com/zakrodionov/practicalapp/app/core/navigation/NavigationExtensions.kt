package com.zakrodionov.practicalapp.app.core.navigation

import com.github.terrakok.cicerone.Cicerone
import com.zakrodionov.practicalapp.app.di.DIQualifiers
import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration

val String.toCiceroneQualifier get() = DIQualifiers.ciceroneQualifier(this)
val String.toNavigationHolderQualifier get() = DIQualifiers.navigationHolderQualifier(this)
val String.toRouterQualifier get() = DIQualifiers.routerQualifier(this)

// Биндит глобальный роутер на всё приложение
fun Module.bindGlobalNavigation(globalQualifier: String) {
    single(globalQualifier.toCiceroneQualifier) {
        Cicerone.create(GlobalRouter())
    }

    single(globalQualifier.toNavigationHolderQualifier) {
        get<Cicerone<GlobalRouter>>(globalQualifier.toCiceroneQualifier).getNavigatorHolder()
    }

    single {
        get<Cicerone<GlobalRouter>>(globalQualifier.toCiceroneQualifier).router
    }
}

// Отличается от обычного модуля только тем, что дополнительно биндится FlowNavigation(FlowRouter)
fun flowModule(
    flowQualifier: String,
    createdAtStart: Boolean = false,
    moduleDeclaration: ModuleDeclaration,
): Module {
    val module = Module(createdAtStart)
    module.bindFlowNavigation(flowQualifier)
    moduleDeclaration(module)
    return module
}

fun Module.bindFlowNavigation(flowQualifier: String) {
    single(flowQualifier.toCiceroneQualifier) {
        Cicerone.create(FlowRouter(get(), flowQualifier.toRouterQualifier.value))
    }

    single(flowQualifier.toNavigationHolderQualifier) {
        get<Cicerone<FlowRouter>>(flowQualifier.toCiceroneQualifier).getNavigatorHolder()
    }

    single(flowQualifier.toRouterQualifier) {
        get<Cicerone<FlowRouter>>(flowQualifier.toCiceroneQualifier).router
    }
}

// Отличается от обычного модуля только тем, что дополнительно биндится TabFlowNavigation(TabFlowRouter)
fun tabFlowModule(
    flowQualifier: String,
    createdAtStart: Boolean = false,
    moduleDeclaration: ModuleDeclaration,
): Module {
    val module = Module(createdAtStart)
    module.bindTabFlowNavigation(flowQualifier)
    moduleDeclaration(module)
    return module
}

fun Module.bindTabFlowNavigation(flowQualifier: String) {
    single(flowQualifier.toCiceroneQualifier) {
        Cicerone.create(TabFlowRouter())
    }

    single(flowQualifier.toNavigationHolderQualifier) {
        get<Cicerone<TabFlowRouter>>(flowQualifier.toCiceroneQualifier).getNavigatorHolder()
    }

    single(flowQualifier.toRouterQualifier) {
        get<Cicerone<TabFlowRouter>>(flowQualifier.toCiceroneQualifier).router
    }
}
