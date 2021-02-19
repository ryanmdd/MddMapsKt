package io.iotdrive.mddmapskt.viewmodels

import android.util.Log
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.amplifyframework.auth.AuthChannelEventName
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.InitializationStatus
import com.amplifyframework.hub.HubChannel
import com.amplifyframework.hub.HubEvent
import io.iotdrive.mddmapskt.core.MddMvRxViewModel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

data class UserViewModelState(
    val signInResult: Async<AuthSignInResult> = Uninitialized
) : MavericksState

class UserViewModel(initialState: UserViewModelState) :
    MddMvRxViewModel<UserViewModelState>(initialState) {

    fun signIn(username: String, password: String) {
        suspend {
            suspendCoroutine<AuthSignInResult> { cont ->
                Amplify.Auth.signIn(
                    username,
                    password,
                    { result -> cont.resume(result) },
                    { cont.resumeWithException(it) }
                )
            }
        }.execute { copy(signInResult = it) }
    }
}
