package com.zakrodionov.practicalapp.app.di

import androidx.fragment.app.Fragment
import org.koin.android.ext.android.getKoin
import org.koin.android.scope.getScopeOrNull
import org.koin.core.component.getScopeName
import org.koin.core.scope.Scope

/**
 * В отличие от встроенного в Koin fragmentScope(), этот скоуп будет переживать пересоздание
 * app/activity/fragment, но его уничтожение лежит на нашей ответственности.
 * Лучше всего скоуп уничтожать в функции onRealDestroy() у фрагмента.
 */
fun Fragment.getOrCreateFragmentScope(id: String): Scope {
    val fragmentScope = getScopeOrNullById(id) ?: createScopeById(id)
    val activityScope = activity?.getScopeOrNull()
    activityScope?.let { fragmentScope.linkTo(it) }
    return fragmentScope
}

fun Fragment.getScopeOrNullById(id: String): Scope? = getKoin().getScopeOrNull(id)

fun Fragment.createScopeById(id: String): Scope = getKoin().createScope(id, getScopeName(), null)

