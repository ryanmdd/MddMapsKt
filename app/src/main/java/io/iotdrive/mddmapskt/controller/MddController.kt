package io.iotdrive.mddmapskt.controller

import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.mvrx.Loading
import io.iotdrive.mddmapskt.models.loadingRowModel
import io.iotdrive.mddmapskt.viewmodels.LayoutViewModel
import io.iotdrive.mddmapskt.viewmodels.LayoutViewModelState

class MddController : TypedEpoxyController<LayoutViewModelState>() {

    override fun buildModels(state: LayoutViewModelState) {
        when (state.componentScreen) {
            is Loading -> {
                loadingRowModel {
                    id("loading")
                }
            }
            else -> {
                
            }
        }
    }
}