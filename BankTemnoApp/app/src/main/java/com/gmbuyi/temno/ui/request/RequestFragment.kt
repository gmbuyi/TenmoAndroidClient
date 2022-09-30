package com.gmbuyi.temno.ui.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmbuyi.temno.R


class RequestFragment : Fragment() {

    private lateinit var slideshowViewModel: RequestViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_request,container,false)
    }
}