package io.iotdrive.mddmapskt.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.airbnb.epoxy.*
import com.airbnb.mvrx.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.iotdrive.mddmapskt.R
import io.iotdrive.mddmapskt.components.MddCarouselComponentItem
import io.iotdrive.mddmapskt.components.MddMapComponent
import io.iotdrive.mddmapskt.components.Section
import io.iotdrive.mddmapskt.controller.MddMapCarouselController
import io.iotdrive.mddmapskt.data.MddMapCarouselPagerData
import io.iotdrive.mddmapskt.models.*
import io.iotdrive.mddmapskt.utils.MddEpoxyController
import io.iotdrive.mddmapskt.utils.simpleController
import io.iotdrive.mddmapskt.utils.withModelsFrom
import io.iotdrive.mddmapskt.viewmodels.LayoutViewModel
import java.lang.Error

class MainFragment : MddBaseFragment() {
    private val layoutViewModel: LayoutViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutViewModel.loadTestData()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        epoxyVisibilityTracker.attach(mEpoxyRecyclerView)
    }

    override fun epoxyController() = simpleController(layoutViewModel) { state ->
        when (state.componentScreen) {
            is Loading -> {
                loadingRowModel { id("loading") }
                return@simpleController
            }
            else -> {
                if (true == state.componentScreen()?.sections?.isEmpty()) {
                    emptyContent {
                        id("empty_content")
                        title("Empty Content")
                    }
                    return@simpleController
                }
            }
        }
        group {
            id("mdd-map-carousel-group")
            layout(R.layout.controller_carousel_map_group_mdd)
            mddMapModel {
                id("Mdd Map")
                state.componentScreen.invoke()?.sections?.mapIndexed { _, item: Section ->
                    when (item) {
                        is MddMapComponent -> {
                            center(state.visibleIndex)
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
                state.componentScreen.invoke()?.sections?.mapIndexed { _, item: Section ->
                    when (item) {
                        is MddMapComponent -> {
                            Log.d("TAG", "MddMapComponent")
                            item.items.mapIndexed { iIndex: Int, iItem: MddCarouselComponentItem ->
                                MddPagerItemModel_()
                                    .title(iItem.title)
                                    .id(iIndex)
                                    .onVisibilityStateChanged { _, _, visibilityState ->
                                        Log.d("VISIBILITY_STATE", visibilityState.toString())
//                                        if (visibilityState == VisibilityState.FOCUSED_VISIBLE) {
//                                            layoutViewModel.setVisibleIndex(iIndex)
//                                        }
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

//    override fun epoxyController() = simpleController(layoutViewModel) { state ->
//        when (state.componentScreen) {
//            is Loading -> {
//                mProgressBar.visibility = View.VISIBLE
//            }
//            is Success -> {
//                mProgressBar.visibility = View.INVISIBLE
//                val visibleIndex = state.visibleIndex
//                Log.d("TAG", state.visibleIndex.toString())
////                var data = state.restResponse()?.data?.asJSONObject()
//                group {
//                    id("mdd-map-carousel-group")
//                    layout(R.layout.controller_carousel_map_group_mdd)
//                    mddMapModel {
//                        id("Mdd Map")
//                        state.componentScreen.invoke().sections.mapIndexed { _, item: Section ->
//                            when (item) {
//                                is MddMapComponent -> {
//                                    center(visibleIndex)
//                                    item.items.mapIndexed { _, mddCarouselComponentItem ->
//                                        LatLng(
//                                            mddCarouselComponentItem.coordinates.lat,
//                                            mddCarouselComponentItem.coordinates.lon
//                                        )
//                                    }.let { markers(it) }
//                                }
//                                else -> {
//                                    Log.d("TAG", "nothing to do")
//                                }
//                            }
//                        }
//                    }
//                    carousel {
//                        id("This is a ViewPager.")
//                        hasFixedSize(true)
//                        state.componentScreen.invoke().sections.mapIndexed { _, item: Section ->
//                            when (item) {
//                                is MddMapComponent -> {
//                                    Log.d("TAG", "MddMapComponent")
//                                    item.items.mapIndexed { iIndex: Int, iItem: MddCarouselComponentItem ->
//                                        MddPagerItemModel_()
//                                            .title(iItem.title)
//                                            .id(iItem.title)
//                                            .onClickListener {
//                                                Log.d("ON_CLICK", "TESTING")
//                                                layoutViewModel.setVisibleIndex(iIndex)
//                                            }
//                                            .onVisibilityStateChanged { _, _, visibilityState ->
//                                                Log.d("VISIBILITY_STATE", visibilityState.toString())
//                                                if (visibilityState == VisibilityState.FOCUSED_VISIBLE) {
//                                                    layoutViewModel.setVisibleIndex(iIndex)
//                                                }
//                                            }
//                                    }.let { models(it) }
//                                }
//                                else -> {
//                                    Log.e("TAG", "we have a problem")
//                                }
//                            }
//
//                        }
//                    }
//                }
//            }
//            is Error -> {
//                Log.d("TAG", state.componentScreen.toString())
//            }
//            else -> {
//                Log.d("TAG", "there was an error?")
//            }
//        }
//    }
}
