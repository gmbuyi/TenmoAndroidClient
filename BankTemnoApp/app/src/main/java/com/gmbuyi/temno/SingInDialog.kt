package com.gmbuyi.temno

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView

class SingInDialog(context: Context, style: Int) : AlertDialog.Builder(context,style){

    init {

        val act =context as Activity
        val view=act.layoutInflater.inflate(R.layout.sign_in_layout,null)


        val pseudo=view.findViewById<TextInputLayout>(R.id.pseudo)
        val pseudoInput=view.findViewById<TextInputEditText>(R.id.pseudo_edit)
        val password=view.findViewById<TextInputLayout>(R.id.password)
        val psswrdInput=view.findViewById<TextInputEditText>(R.id.psswrd_edit1)
        val signUp=view.findViewById<MaterialTextView>(R.id.sign_up)
        val confirm=view.findViewById<Button>(R.id.btn_confirm)


        setView(view)
        setCancelable(false)

        setListener(pseudo,pseudoInput)
        setListener(password,psswrdInput)


        confirm.setOnClickListener {

            if (pseudo.error==null && password.error==null ){



            }


        }









    }

    private fun cancel(){
        this.cancel()
    }

     /*
        Method that set textInput On text Listener

      */


    private fun setListener(layout: TextInputLayout, input: TextInputEditText){

        input.doOnTextChanged{text, start, before, count ->
            if (text!!.isEmpty()){
                layout.error="is required"

            } else {
                layout.error=null
            }
        }

    }






}