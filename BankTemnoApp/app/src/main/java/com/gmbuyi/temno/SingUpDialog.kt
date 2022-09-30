package com.gmbuyi.temno

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.widget.Button
import android.widget.RelativeLayout
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

@SuppressLint("InflateParams")
class SingUpDialog(context: Context, style: Int): AlertDialog.Builder(context,style){

   init {

        val act =context as Activity
        val view=act.layoutInflater.inflate(R.layout.sign_up_layout,null)
        val id =view.findViewById<RelativeLayout>(R.id.dialog)

        val username=view.findViewById<TextInputLayout>(R.id.pseudo)
        val usernameInput=view.findViewById<TextInputEditText>(R.id.pseudo_edit)
        val password=view.findViewById<TextInputLayout>(R.id.password)
        val passwordInput=view.findViewById<TextInputEditText>(R.id.psswrd_edit1)
        val passwordRepeat=view.findViewById<TextInputLayout>(R.id.password1)
        val passwordRepeatInput=view.findViewById<TextInputEditText>(R.id.psswrd_edit2)

        val confirm=view.findViewById<Button>(R.id.btn_confirm)


        setView(view)

       setListener(username,usernameInput)
       setListener(password,passwordInput)
       setListener(passwordRepeat,passwordRepeatInput)



       confirm.setOnClickListener {


       }





    }


    private fun setListener(layout:TextInputLayout, input:TextInputEditText){

        input.doOnTextChanged{ text, _, _, _ ->
            if (text!!.isEmpty()){
                layout.error="is required"

            } else {
                layout.error=null
            }
        }

    }






}