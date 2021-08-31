package com.zakrodionov.practicalapp.app.core.navigation

import com.github.terrakok.cicerone.Cicerone
import com.zakrodionov.practicalapp.app.di.DIQualifiers
import com.zakrodionov.practicalapp.app.di.modules.GLOBAL_QUALIFIER
import org.koin.core.module.Module
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.ScopeDSL

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

    single(GLOBAL_QUALIFIER.toRouterQualifier) {
        get<Cicerone<GlobalRouter>>(globalQualifier.toCiceroneQualifier).router
    }
}

// Отличается от обычного модуля только тем, что дополнительно биндится FlowNavigation(FlowRouter)
inline fun <reified T> flowModule(
    createdAtStart: Boolean = false,
    moduleDeclaration: ScopeDSL.() -> Unit,
): Module {
    val module = Module(createdAtStart)
    module.scope<T> {
        bindFlowNavigation()
        moduleDeclaration()
    }
    return module
}

fun ScopeDSL.bindFlowNavigation() {
    scoped {
        Cicerone.create(FlowRouter(get(GLOBAL_QUALIFIER.toRouterQualifier)))
    }
    scoped {
        get<Cicerone<FlowRouter>>().getNavigatorHolder()
    }
    scoped {
        get<Cicerone<FlowRouter>>().router
    }
}

// Отличается от обычного модуля только тем, что дополнительно биндится TabFlowNavigation(TabFlowRouter)
inline fun <reified T> tabFlowModule(
    createdAtStart: Boolean = false,
    moduleDeclaration: ScopeDSL.() -> Unit,
): Module {
    val module = Module(createdAtStart)
    module.scope<T> {
        bindTabFlowNavigation()
        moduleDeclaration()
    }
    return module
}

fun ScopeDSL.bindTabFlowNavigation() {
    scoped {
        Cicerone.create(TabFlowRouter())
    }

    scoped {
        get<Cicerone<TabFlowRouter>>().getNavigatorHolder()
    }

    scoped {
        get<Cicerone<TabFlowRouter>>().router
    }
}
