package io.iotdrive.mddmapskt.controller

import android.util.Log
import android.view.View
import com.airbnb.epoxy.*
import com.airbnb.epoxy.VisibilityState.FOCUSED_VISIBLE
import com.google.android.gms.maps.model.LatLng
import io.iotdrive.mddmapskt.R
import io.iotdrive.mddmapskt.components.MddCarouselComponentItem
import io.iotdrive.mddmapskt.components.MddMapComponent
import io.iotdrive.mddmapskt.components.Section
import io.iotdrive.mddmapskt.data.MddMapCarouselPagerData
import io.iotdrive.mddmapskt.models.*
import io.iotdrive.mddmapskt.viewmodels.MainActivityViewState
import io.iotdrive.mddmapskt.viewmodels.ViewState

class MddMapCarouselController : TypedEpoxyController<ViewState<MainActivityViewState>>(
    EpoxyAsyncUtil.getAsyncBackgroundHandler(),
    EpoxyAsyncUtil.getAsyncBackgroundHandler()
) {

    override fun buildModels(data: ViewState<MainActivityViewState>) {
        Log.d("TAG", "got to here?")
        if (data.isLoading()) {
            loadingRowModel { id("loadingRowModel") }
            return
        }
        when (data) {
            is ViewState.Error -> {
                return
            }
            else -> {
                group {
                    id("mdd-map-carousel-group")
                    layout(R.layout.controller_carousel_map_group_mdd)
                    mddMapModel {
                        data.data?.layout?.sections?.mapIndexed { _, item: Section ->
                            when (item) {
                                is MddMapComponent -> {
                                    center(data.data.currentMapIndex)
                                    item.items.mapIndexed { _, mddCarouselComponentItem ->
                                        LatLng(
                                            mddCarouselComponentItem.coordinates.lat,
                                            mddCarouselComponentItem.coordinates.lon
                                        )
                                    }.let { markers(it) }
                                }
                                else -> {
                                    Log.d("TAG", "nothing to do")
                                }
                            }
                        }
                    }
                    carousel {
                        id("This is a ViewPager.")
                        hasFixedSize(true)
                        data.data?.layout?.sections?.forEachIndexed { _, section ->
                            when (section) {
                                is MddMapComponent -> {
                                    Log.d("TAG", "MddMapComponent")
                                    section.items.mapIndexed { iIndex: Int, iItem: MddCarouselComponentItem ->
                                        MddPagerItemModel_()
                                            .title(iItem.title)
                                            .id(iIndex)
                                            .onClickListener {
                                                Log.d("TAG", iIndex.toString())
                                                data.data?.onChangeHandler(iIndex)
                                            }
                                            .onVisibilityStateChanged { _, _, visibilityState ->
                                                Log.d(
                                                    "VISIBILITY_STATE",
                                                    visibilityState.toString()
                                                )
                                                if (visibilityState == FOCUSED_VISIBLE) {
//                                                data.data?.onChangeHandler(iIndex)
                                                }
                                            }
                                    }.let { models(it) }
                                }
                                else -> {
                                    Log.e("TAG", "we have a problem")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}