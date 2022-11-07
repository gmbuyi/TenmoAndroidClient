package com.gmbuyi.temno

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.gmbuyi.temno.api.models.UserCredential
import com.gmbuyi.temno.api.services.ConsumingApi
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.contain_sign_in.password
import kotlinx.android.synthetic.main.contain_sign_in.pseudo
import kotlinx.android.synthetic.main.contain_sign_up.*
import kotlinx.android.synthetic.main.sign_up_layout.*


/**
 * Sing Up activity, for user to sign up in the app
 *
 */

class SignUpPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.sign_up_layout)

        setListener(pseudo)
        setListener(password)
        passwordMatchedListener(password,password1)


        back_btn.setOnClickListener {
            this@SignUpPageActivity.finish()
        }

        btn_confirm.setOnClickListener {

            if ((new_user.text!!.isNotEmpty() && new_password.text!!.isNotEmpty() && new_password_confirm.text!!.isNotEmpty())
                or (pseudo.error == null && password.error == null && password1.error == null)
            ) {
                val userCredential = UserCredential(new_user.text.toString(),new_password.text.toString())
                val progressDialog = LoadingProgressDialog(this@SignUpPageActivity,R.style.TransparentDialog).show()
                ConsumingApi(this@SignUpPageActivity).createNewUser(userCredential,progressDialog, pseudo)
            }

        }

        }






    private fun setListener(layout: TextInputLayout){

        layout.editText?.doOnTextChanged{ text, _, _, _ ->
            if (text!!.isEmpty()){
                layout.error="is required"

            } else
                layout.error=null

        }

    }


    private fun passwordMatchedListener(password: TextInputLayout, passwordConfirm : TextInputLayout){
        passwordConfirm.editText?.doOnTextChanged { text, _, _, _ ->


            if (text!!.toString() == password.editText?.text.toString()){
                passwordConfirm.error = null
            } else {
                passwordConfirm.error = "password doesn't match"
            }


        }

    }
}