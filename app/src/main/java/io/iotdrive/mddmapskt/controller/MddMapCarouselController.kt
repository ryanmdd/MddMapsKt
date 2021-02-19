package io.iotdrive.mddmapskt.controller

import android.view.View
import com.airbnb.epoxy.VisibilityState.FOCUSED_VISIBLE
import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.carousel
import com.airbnb.epoxy.group
import io.iotdrive.mddmapskt.R
import io.iotdrive.mddmapskt.data.MddMapCarouselPagerData
import io.iotdrive.mddmapskt.models.*

class MddMapCarouselController : TypedEpoxyController<MddMapCarouselPagerData>() {

    override fun buildModels(data: MddMapCarouselPagerData?) {
        group {
            id("mdd-map-carousel-group")
            layout(R.layout.controller_carousel_map_group_mdd)

            mddMapModel {
                id("Mdd Map")
                data?.let { center(it.visibleItemIndex) }

            }
            carousel {
                id("This is a ViewPager.")
                hasFixedSize(true)
                data?.items?.mapIndexed { index, item ->
                    MddPagerItemModel_()
                        .title(item)
                        .id(item)
                        .onVisibilityStateChanged { _, _, visibilityState ->
                            if (visibilityState == FOCUSED_VISIBLE) {
                                data.visibleItemIndex = index
                                setData(data)
                            }
                        }
                }?.let { models(it) }
            }
        }
    }
}