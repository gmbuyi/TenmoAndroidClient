package com.gmbuyi.temno

import android.app.Activity
import android.app.AlertDialog
import android.content.Context

/**
 * class that generates progress transition progress dialog
 *
 */

class LoadingProgressDialog(context: Context, style: Int) : AlertDialog.Builder(context,style){

    init {

        val act =context as Activity
        val view=act.layoutInflater.inflate(R.layout.progress_dialog,null)
        view.setBackgroundColor(context.resources.getColor(R.color.transparent,null))


        setView(view)
        setCancelable(false)


    }



}