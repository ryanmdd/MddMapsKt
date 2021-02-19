package io.iotdrive.mddmapskt.utils

import com.airbnb.epoxy.*
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.withState
import io.iotdrive.mddmapskt.core.MddMvRxViewModel
import io.iotdrive.mddmapskt.fragments.MddBaseFragment

class MddEpoxyController(
    val buildModelsCallback: EpoxyController.() -> Unit = {}
) : AsyncEpoxyController() {
    override fun buildModels() {
        buildModelsCallback()
    }
}

/**
 * Create a [MddEpoxyController] that builds models with the given callback.
 */
fun MddBaseFragment.simpleController(
    buildModels: EpoxyController.() -> Unit
) = MddEpoxyController {
    // Models are built asynchronously, so it is possible that this is called after the fragment
    // is detached under certain race conditions.
    if (view == null || isRemoving) return@MddEpoxyController
    buildModels()
}

/**
 * Create a [MddEpoxyController] that builds models with the given callback.
 * When models are built the current state of the viewmodel will be provided.
 */
fun <S : MavericksState, A : MddMvRxViewModel<S>> MddBaseFragment.simpleController(
    viewModel: A,
    buildModels: EpoxyController.(state: S) -> Unit
) = MddEpoxyController {
    if (view == null || isRemoving) return@MddEpoxyController
    com.airbnb.mvrx.withState(viewModel) { state ->
        buildModels(state)
    }
}

fun <A : MddMvRxViewModel<B>, B : MavericksState, C : MddMvRxViewModel<D>, D : MavericksState> MddBaseFragment.simpleController(
    viewModel1: A,
    viewModel2: C,
    buildModels: EpoxyController.(state1: B, state2: D) -> Unit
) = MddEpoxyController {
    if (view == null || isRemoving) return@MddEpoxyController
    withState(viewModel1, viewModel2) { state1, state2 ->
        buildModels(state1, state2)
    }
}

/** For use in the buildModels method of EpoxyController. A shortcut for creating a Carousel model, initializing it, and adding it to the controller.
 *
 */
inline fun EpoxyController.carousel(modelInitializer: CarouselModelBuilder.() -> Unit) {
    CarouselModel_().apply {
        modelInitializer()
    }.addTo(this)
}

/** Add models to a CarouselModel_ by transforming a list of items into EpoxyModels.
 *
 * @param items The items to transform to models
 * @param modelBuilder A function that take an item and returns a new EpoxyModel for that item.
 */
inline fun <T> CarouselModelBuilder.withModelsFrom(
    items: List<T>,
    modelBuilder: (T) -> EpoxyModel<*>
) {
    models(items.map { modelBuilder(it) })
}