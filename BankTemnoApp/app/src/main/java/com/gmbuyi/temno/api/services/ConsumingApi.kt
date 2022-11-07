package com.gmbuyi.temno.api.services

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gmbuyi.temno.MyRequestTransferAdapter
import com.gmbuyi.temno.MySentTransferAdapter
import com.gmbuyi.temno.R
import com.gmbuyi.temno.TransferDialog
import com.gmbuyi.temno.api.models.Account
import com.gmbuyi.temno.api.models.AuthenticatedUser
import com.gmbuyi.temno.api.models.Transfer
import com.gmbuyi.temno.api.models.UserCredential
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class is responsible of consuming the api by using Retrofit library
 *
 *  Method getUserCredential gets from the api the Credentials after login by username and password (token and userId)
 *
 *  Method getListTransfer gets the list Of  approved transfers  done by the current Account Id
 *
 * Method getRequestListTransfer gets the list of pending and Rejected Request by the current Account id
 *
 * Method getAccount gets the current Account
 *
 *  Method getListAccount gets the all list of Account except the current account
 *
 *  Method updateStatus post the update status transfer to the api
 *
 *  Method addPreference adds the object passed in the parameter in the shared Preferences.
 *  the Object is serialized before adding to preference with Gson librry
 */


class ConsumingApi {


   private var retrofit: Retrofit
   private var jsonApi: JsonApi
   var context:Context


    constructor(context: Context){

        retrofit= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        this.context = context
        jsonApi= retrofit.create(JsonApi::class.java)
    }

    constructor(context: Context,interceptor: TokenInterceptor){

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        retrofit= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URL)
            .build()

        jsonApi= retrofit.create(JsonApi::class.java)
        this.context = context


    }

    fun getUserCredential(dialog: Dialog,userCredential: UserCredential,intent:Intent): AuthenticatedUser? {

        var user: AuthenticatedUser? = null

        val mCall: Call<AuthenticatedUser> = jsonApi.login(userCredential)

        mCall.enqueue(object : Callback<AuthenticatedUser> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<AuthenticatedUser>,
                response: Response<AuthenticatedUser>
            ) {
                user = response.body()!!
                dialog.cancel()

                val res: AuthenticatedUser = response.body()!!
                addPreference("authenticatedUser",res)
                context.startActivity(intent)


            }

            override fun onFailure(call: Call<AuthenticatedUser>, t: Throwable) {


            }


        })

        return user





    }

    fun getListTransfer(list: ListView, progress : View, accountId: Long): List<Transfer> {

        var transferList: List<Transfer> = ArrayList()

        val mCall: Call<List<Transfer>> = jsonApi.getListTransfer(accountId)

        mCall.enqueue(object : Callback<List<Transfer>> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<List<Transfer>>,
                response: Response<List<Transfer>>
            ) {
                transferList = response.body()!!

                progress.visibility = View.INVISIBLE
                list.adapter = MySentTransferAdapter(context,0, transferList, accountId )

            }

            override fun onFailure(call: Call<List<Transfer>>, t: Throwable) {


            }


        })

        return transferList


    }

    fun getRequestListTransfer(list: ListView, progress : View, currentAccount: Account, hideRejected : Boolean ): List<Transfer> {

        var transferList: List<Transfer> = ArrayList()

        val mCall: Call<List<Transfer>> = if (hideRejected){
            jsonApi.getRequestListTransfer(currentAccount.account_id)

        } else {
            jsonApi.getPendingRequestListTransfer(currentAccount.account_id)
        }



        mCall.enqueue(object : Callback<List<Transfer>> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<List<Transfer>>,
                response: Response<List<Transfer>>
            ) {
                transferList = response.body()!!

                progress.visibility = View.INVISIBLE

                /**
                 * add adapter to list and
                 * add onClick Listener to a Pending Request Item
                 */
                val adapter = MyRequestTransferAdapter(context,0, transferList,currentAccount.account_id)
                list.adapter = adapter
                list.onItemClickListener = adapter

            }

            override fun onFailure(call: Call<List<Transfer>>, t: Throwable) {


            }


        })

        return transferList


    }

    fun getAccount(textView: TextView, progress : View,user_id: Long): Account? {

        var account: Account? = null
        val mCall: Call<Account> = jsonApi.getAccount(user_id)

        mCall.enqueue(object : Callback<Account> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<Account>,
                response: Response<Account>
            ) {
                account = response.body()!!
                addPreference("account", account!!)


                textView.text = "$"+ account!!.balance.toString()
                progress.visibility = View.INVISIBLE

            }

            override fun onFailure(call: Call<Account>, t: Throwable) {


            }


        })

        return account


    }


    fun getListAccount(dialog: Dialog,transferType : Int , currentAccount: Account): List<Account> {

        var list: List<Account>  = ArrayList()
        val mCall: Call<List<Account>> = jsonApi.getListAccount(currentAccount.account_id)

        mCall.enqueue(object : Callback<List<Account>> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<List<Account>>,
                response: Response<List<Account>>
            ) {

                list = response.body()!!
                dialog.cancel()
               TransferDialog(context,R.style.TransparentDialog, transferType,currentAccount, list)
                    .show()





            }

            override fun onFailure(call: Call<List<Account>>, t: Throwable) {


            }


        })

        return list


    }

    fun createTransfer( transfer : Transfer, progressDialog : Dialog, transferDialog: TransferDialog) {

        val mCall: Call<Boolean> = jsonApi.createTransfer(transfer)

        mCall.enqueue(object : Callback<Boolean> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<Boolean>,
                response: Response<Boolean>
            ) {
                progressDialog.cancel()
                val code = response.code()
                if (code == 201 && response.body()!!){

                    transferDialog.cancel()
                    Toast.makeText(context,transfer.typeTransfer.transferTypeDesc +
                            " created successfully",Toast.LENGTH_LONG).show()


                }

                else {

                    transferDialog.setError()

                }




            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                progressDialog.cancel()
                Toast.makeText(context,"Error: Connection failed",Toast.LENGTH_LONG).show()

            }


        })



    }

    fun updateStatus(transfer : Transfer, progressDialog : Dialog) {

        val mCall: Call<Void> = jsonApi.updateTransferStatus(transfer)

        mCall.enqueue(object : Callback<Void> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                progressDialog.cancel()
                val code = response.code()
                if (code == 204){

                    Toast.makeText(context,"Request"
                            + transfer.transferStatus.transferStatusDesc +
                            " successfully",Toast.LENGTH_LONG).show()
                }

                else {

                    Toast.makeText(context, code.toString(),Toast.LENGTH_LONG).show()

                }




            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                progressDialog.cancel()
                Toast.makeText(context,"Error: Connection failed",Toast.LENGTH_LONG).show()

            }


        })



    }




    fun createNewUser(user: UserCredential, progressDialog : Dialog, layout: TextInputLayout){



        val jsonApi = retrofit.create(JsonApi::class.java)
        val mCall: Call<Void> =
            jsonApi.register(user)



        mCall.enqueue(object : Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                progressDialog.cancel()
                (context as Activity).finish()

                val code = response.code()
                    if (code == 201) {

                        Toast.makeText(context, "User Created", Toast.LENGTH_SHORT).show()

                    } else {

                        layout.error = "User already exists"

                    }


            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                    progressDialog.cancel()

                    Toast.makeText(context, "connection failed", Toast.LENGTH_SHORT).show()

                }




        })


    }


    private fun addPreference(title: String, obj: Any){
        val preference =  context.getSharedPreferences("Credentials", AppCompatActivity.MODE_PRIVATE)
        val edit = preference.edit()
        val gson = Gson().toJson(obj)
        edit.putString(title, gson)
        edit.apply()



    }





    companion object{

        const val BASE_URL = "http://10.0.2.2:8080/"

    }


}