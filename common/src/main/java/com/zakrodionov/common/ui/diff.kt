package com.zakrodionov.common.ui

/**
 * From https://github.com/arkivanov/MVIKotlin
 * Provides a way of `Model` diffing, useful for efficient partial UI updates.
 *
 * Typical usage:
 * ```
 * class PostsFragment : BaseFragment<PostsState, PostsEvent>(R.layout.fragment_posts) {
 *
 *     // View initialization
 *
 *     private val renderer = diff<PostsState> {
 *         diff(get = PostsState::lceState, set = { binding.lceLayout.renderState(it) })
 *         diff(get = PostsState::posts, compare = { a, b -> a === b }, set = adapter::setItems)
 *     }
 *
 *     override fun render(state: PostsState) {
 *         renderer.render(state)
 *     }
 * }
 * ```
 *
 * @param block the DSL block function
 *
 * @return a [ViewRenderer] that accepts `Models` via its [ViewRenderer.render] method.
 * Every `Model` is passed through the provided `getters` and `comparators` to corresponding `consumers`.
 */
inline fun <Model : Any> diff(block: DiffBuilder<Model>.() -> Unit): ViewRenderer<Model> {
    val builder =
        object : DiffBuilder<Model>(), ViewRenderer<Model> {
            override fun render(model: Model) {
                binders.forEach { it.render(model) }
            }
        }

    builder.block()

    return builder
}

open class DiffBuilder<Model : Any> {

    @PublishedApi
    internal val binders = ArrayList<ViewRenderer<Model>>()

    /**
     * Registers the diff strategy
     *
     * @param get a `getter` to extract a piece of data (typically a field value) from the original `Model`
     * @param compare a `comparator` to compare a new value with the old one, default is `equals`
     * @param set a `consumer` of the values, receives the new value if it is the first value or if
     * the `comparator` returned `false`
     */
    inline fun <T> diff(
        crossinline get: (Model) -> T,
        crossinline compare: (new: T, old: T) -> Boolean = { a, b -> a == b },
        crossinline set: (T) -> Unit,
    ) {
        binders +=
            object : ViewRenderer<Model> {
                private var oldValue: T? = null

                override fun render(model: Model) {
                    val newValue = get(model)
                    val oldValue = oldValue
                    this.oldValue = newValue

                    if ((oldValue == null) || !compare(newValue, oldValue)) {
                        set(newValue)
                    }
                }
            }
    }
}

interface ViewRenderer<in Model : Any> {
    fun render(model: Model)
}
