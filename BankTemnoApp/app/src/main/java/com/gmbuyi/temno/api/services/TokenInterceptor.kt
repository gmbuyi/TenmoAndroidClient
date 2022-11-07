package com.gmbuyi.temno.api.services

import android.content.Context
import com.gmbuyi.temno.api.models.AuthenticatedUser
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response

/**
 * the TokenInterceptor is called  for adding the header  to our request
 * the token received from the api when the user connect is used in
 * the Authorization Bearer header
 *
 */

class TokenInterceptor(private val context: Context?): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val serializedObject =  context?.getSharedPreferences("Credentials", Context.MODE_PRIVATE )!!
            .getString("authenticatedUser", "")

        val authenticatedUser = Gson().fromJson(serializedObject, AuthenticatedUser::class.java)

        val request = chain.request().newBuilder()
             .header("Authorization",
                 "Bearer "+
                         authenticatedUser.token)
             .build()

        return chain.proceed(
            request
        )
    }
}