package com.gmbuyi.temno


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.gmbuyi.temno.api.models.UserCredential
import com.gmbuyi.temno.api.services.ConsumingApi
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.contain_sign_in.*


/**
 * the Main Activity that shows the sign In page
 *  Method setListener , sets the Onchange Listener to each edit text in the activity
 *  that lets user notice about entry data error
 *
 */

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val layoutList = ArrayList<TextInputLayout>()
            layoutList.add(pseudo)
            layoutList.add(password)



        setListener(layoutList)

         // create new sing up Activity for user
        sign_up.setOnClickListener{
            startActivity(Intent(this, SignUpPageActivity::class.java))

        }


        /* confirm button On click Listener */

        btn_confirm.setOnClickListener {

            if ((pseudo_edit.text!!.isNotEmpty() && psswrd_edit1.text!!.isNotEmpty())
                or (pseudo.error == null && password.error == null)
            )
            {
                val intent = Intent(this, AccountActivity::class.java)
                val dialog = LoadingProgressDialog(this, R.style.TransparentDialog).show()
                val userCredential = UserCredential(pseudo_edit.text.toString(), psswrd_edit1.text.toString())
                ConsumingApi(this).getUserCredential(dialog, userCredential,intent)

            }

        }

    }


    // adding OnTextChanged listener to each editText in the list of textInputLayout

    private fun setListener(layouts: ArrayList<TextInputLayout>){

        layouts.forEach {

        it.editText?.doOnTextChanged{ text, _, _, _ ->
            if (text!!.isEmpty()){
                it.error="is required"

            } else {
                it.error=null

            }
        }

        }





    }



}