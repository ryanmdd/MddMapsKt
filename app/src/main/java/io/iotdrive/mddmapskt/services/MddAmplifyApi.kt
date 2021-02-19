package io.iotdrive.mddmapskt.services

import com.amplifyframework.api.rest.RestOptions
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.core.Amplify

class MddAmplifyApi : MddApi {

    override suspend fun authenticate(): Boolean {
        var isAuthenticated = false
        Amplify.Auth.fetchAuthSession({ result ->
            val cognitoAuthSession = result as AWSCognitoAuthSession
            when (cognitoAuthSession.identityId.type) {
                AuthSessionResult.Type.SUCCESS -> isAuthenticated = true
                AuthSessionResult.Type.FAILURE -> isAuthenticated = false
            }
        },
            { error -> isAuthenticated = false })
        return isAuthenticated
    }
}