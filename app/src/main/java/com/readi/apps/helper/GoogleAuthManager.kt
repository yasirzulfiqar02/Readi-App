package com.readi.apps.helper

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import android.util.Log

class GoogleAuthManager(activity: Activity,
    private val launcher: ActivityResultLauncher<Intent>
) {

    private val googleSignInClient: GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    fun signIn() {
        val intent = googleSignInClient.signInIntent
        launcher.launch(intent)
    }

    fun handleResult(
        data: Intent?,
        onSuccess: (name: String?, email: String?, id: String?) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            onSuccess(
                account.displayName,
                account.email,
                account.id
            )

        } catch (e: Exception) {
            Log.e("GOOGLE_AUTH", "Error: ${e.message}")
            onError(e.message ?: "Login failed")
        }
    }
}