package com.zakrodionov.practicalapp.app.core.navigation

import com.github.terrakok.cicerone.Cicerone
import org.koin.core.module.Module
import org.koin.dsl.ScopeDSL

// Биндит глобальный роутер на всё приложение
fun Module.bindGlobalNavigation() {
    single {
        Cicerone.create(GlobalRouter())
    }

    single {
        get<Cicerone<GlobalRouter>>().getNavigatorHolder()
    }

    single {
        get<Cicerone<GlobalRouter>>().router
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
        Cicerone.create(FlowRouter(get()))
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
