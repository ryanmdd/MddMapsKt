package io.iotdrive.mddmapskt.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.iotdrive.mddmapskt.components.*

typealias OnChangeHandler = (index: Int) -> Unit

sealed class ViewState<T>(val data: T? = null) {
    class Loading<T>(data: T? = null) : ViewState<T>(data)
    class Error<T>(val throwable: Throwable, data: T? = null) : ViewState<T>(data)
    class Loaded<T>(data: T? = null) : ViewState<T>(data)

    fun isLoading(): Boolean {
        return when (this) {
            is Loading -> {
                true
            }
            else -> {
                false
            }
        }
    }
}

data class MainActivityViewState(
    val layout: ComponentScreen,
    val currentMapIndex: Int = -1,
    val onChangeHandler: OnChangeHandler
)

class MainActivityViewModel : ViewModel() {

    companion object {
        private val TAG = "MainAcitivityViewModel"
    }

    private val _viewState = MutableLiveData<ViewState<MainActivityViewState>>()
    val viewState: LiveData<ViewState<MainActivityViewState>> = _viewState
    private lateinit var layout: ComponentScreen
    private fun onChangeHandler(index: Int) {
//        _viewState.postValue(ViewState.Loading())
//        _viewState.postValue(
//            ViewState.Loaded(
//                MainActivityViewState(
//                    currentMapIndex = currentMapIndex,
//                    layout = layout,
//                    onChangeHandler=::onChangeHandler
//                )
//            )
//        )
    }

    fun loadData() {
        _viewState.postValue(ViewState.Loading())
        layout = ComponentScreen(
            "testing",
            sections = listOf(
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
        _viewState.postValue(
            ViewState.Loaded(
                MainActivityViewState(
                    currentMapIndex = 0,
                    layout = layout,
                    onChangeHandler=::onChangeHandler
                )
            )
        )
    }
}