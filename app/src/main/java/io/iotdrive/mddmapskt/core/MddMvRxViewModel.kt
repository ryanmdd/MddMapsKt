package io.iotdrive.mddmapskt.core

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel

abstract class MddMvRxViewModel<S : MavericksState>(initialState: S) : MavericksViewModel<S>(initialState)