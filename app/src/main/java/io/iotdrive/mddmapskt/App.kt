package io.iotdrive.mddmapskt

import android.app.Application
import android.util.Log
import com.airbnb.mvrx.Mavericks
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify


class App: Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(applicationContext)
        } catch (error: AmplifyException) {
            Log.e("APP", error.toString())
        }
        Mavericks.initialize(this)
    }
}