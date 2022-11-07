package com.gmbuyi.temno.api.services

import com.gmbuyi.temno.api.models.Account
import com.gmbuyi.temno.api.models.AuthenticatedUser
import com.gmbuyi.temno.api.models.Transfer
import com.gmbuyi.temno.api.models.UserCredential
import retrofit2.Call
import retrofit2.http.*

interface JsonApi {
    @POST("login")
    fun  login(@Body userCredential: UserCredential) : Call<AuthenticatedUser>

    @POST("register")
    fun register(@Body userCredential: UserCredential) :Call <Void>


    @GET("accounts/{userId}")
    fun getAccount(@Path("userId") user_id : Long): Call<Account>


    @GET("transfers/listSent/{accountId}")
    fun getListTransfer(@Path("accountId") account_id: Long): Call<List<Transfer>>


    @GET("transfers/listRequest/{accountId}")
    fun getRequestListTransfer(@Path("accountId") account_id: Long): Call<List<Transfer>>

    @GET("transfers/listPendingRequest/{accountId}")
    fun getPendingRequestListTransfer(@Path("accountId") account_id: Long): Call<List<Transfer>>


    @POST("transfers/create")
    fun createTransfer(@Body transfer:Transfer): Call<Boolean>

    @PUT("transfers")
    fun updateTransferStatus(@Body transfer:Transfer): Call<Void>

    @GET("accounts/list/{accountId}")
    fun getListAccount(@Path("accountId") account_id: Long):Call<List<Account>>


}