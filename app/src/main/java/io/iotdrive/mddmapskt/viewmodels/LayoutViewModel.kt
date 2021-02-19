package io.iotdrive.mddmapskt.viewmodels

import android.util.Log
import com.airbnb.mvrx.*
import com.amplifyframework.api.rest.RestOptions
import com.amplifyframework.api.rest.RestResponse
import com.amplifyframework.core.Amplify
import io.iotdrive.mddmapskt.core.MddMvRxViewModel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import io.iotdrive.mddmapskt.components.*

data class LayoutViewModelState(
    val restResponse: Async<RestResponse> = Uninitialized,
    val componentScreen: Async<ComponentScreen> = Uninitialized,
    val visibleIndex: Int = -1
) : MavericksState

class LayoutViewModel(initialState: LayoutViewModelState) :
    MddMvRxViewModel<LayoutViewModelState>(initialState) {

    fun setVisibleIndex(index: Int) {
        setState { copy(visibleIndex = index) }
    }

    fun loadData(url: String?) {
        suspend {
            suspendCoroutine<RestResponse> { continuation ->
                val options = RestOptions.builder()
                    .addPath(url)
                    .build()
                Amplify.API.put(options,
                    {
                        continuation.resume(it)
                    },
                    { continuation.resumeWithException(it) }
                )
            }
        }.execute { copy(restResponse = it) }
    }

    fun loadEmptyTestData() {
        suspend {
            suspendCoroutine<ComponentScreen> { continuation ->
                continuation.resume(
                    ComponentScreen(
                        "testing",
                        sections = emptyList()
                    )
                )
            }
        }.execute { copy(componentScreen = it) }
    }

    fun loadTestData() {
        Log.d("TAG", "got to here?")
        suspend {
            suspendCoroutine<ComponentScreen> { continuation ->
                continuation.resume(
                    ComponentScreen(
                        "testing",
                        sections=listOf(
                            MddMapComponent(
                                "map-testing",
                                listOf(
                                    MddCarouselComponentItem(
                                        title = "VIN_0",
                                        subtitles = listOf(
                                            MddSubtitle(key = "KEY", value = "VALUE")
                                        ),
                                        action = MddAction(
                                            type = "ComponentScreen",
                                            url = "url",
                                            payload = "{}"
                                        ),
                                        coordinates = MddCoordinates(
                                            lat = 45.1,
                                            lon = -94.1
                                        )
                                    ),
                                    MddCarouselComponentItem(
                                        "VIN_1",
                                        subtitles = listOf(
                                            MddSubtitle(key = "KEY", value = "VALUE")
                                        ),
                                        action = MddAction(
                                            type = "ComponentScreen",
                                            url = "url",
                                            payload = "{}"
                                        ),
                                        coordinates = MddCoordinates(
                                            lat = 45.12,
                                            lon = -94.12
                                        )
                                    ),
                                    MddCarouselComponentItem(
                                        "VIN_2",
                                        subtitles = listOf(
                                            MddSubtitle(key = "KEY", value = "VALUE")
                                        ),
                                        action = MddAction(
                                            type = "ComponentScreen",
                                            url = "url",
                                            payload = "{}"
                                        ),
                                        coordinates = MddCoordinates(
                                            lat = 45.14,
                                            lon = -94.14
                                        )
                                    ),
                                    MddCarouselComponentItem(
                                        "VIN_3",
                                        subtitles = listOf(
                                            MddSubtitle(key = "KEY", value = "VALUE")
                                        ),
                                        action = MddAction(
                                            type = "ComponentScreen",
                                            url = "url",
                                            payload = "{}"
                                        ),
                                        coordinates = MddCoordinates(
                                            lat = 45.16,
                                            lon = -94.16
                                        )
                                    ),
                                )
                            )
                        )
                    )
                )
                Log.d("TAG", "testing?")
            }
        }.execute { copy(componentScreen = it, visibleIndex = 0) }
    }
}