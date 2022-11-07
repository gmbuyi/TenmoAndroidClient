package com.gmbuyi.temno.ui.request

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmbuyi.temno.LoadingProgressDialog
import com.gmbuyi.temno.R
import com.gmbuyi.temno.api.models.Account
import com.gmbuyi.temno.api.models.TransferType
import com.gmbuyi.temno.api.services.ConsumingApi
import com.gmbuyi.temno.api.services.TokenInterceptor
import com.gmbuyi.temno.databinding.FragmentRequestBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

/**
 * This Fragment Shows the list of Request transfers
 *
 */


class RequestFragment : Fragment() {

    private var _binding: FragmentRequestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[RequestViewModel::class.java]

        _binding = FragmentRequestBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val listView : ListView = binding.listItem
        val btnHide :ImageButton = binding.imgBtnHide
        val btnCreate: FloatingActionButton = binding.btnCreateRequest





        val progress : View = binding.progressBarRequest.root
        progress.visibility = View.VISIBLE


        //Getting the List of requests
        ConsumingApi(requireContext(), TokenInterceptor(context))
            .getRequestListTransfer(listView,progress,
                getAccount(), btnHide.isEnabled
            )


        // hide or Un hide the rejected request
        btnHide.setOnClickListener {
            progress.visibility = View.VISIBLE
            btnHide.isEnabled = !btnHide.isEnabled

            ConsumingApi(requireContext(), TokenInterceptor(context))
                .getRequestListTransfer(listView,progress,
                   getAccount(), btnHide.isEnabled
                )
        }


        // Create a new Request
        btnCreate.setOnClickListener {
            val progressDialog = LoadingProgressDialog(requireContext(),R.style.TransparentDialog).show()
            ConsumingApi(requireContext(), TokenInterceptor(context))
               .getListAccount(progressDialog, TransferType.ID_REQUEST,getAccount())


        }

        val textView: TextView = binding.textRequest
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val param = root.layoutParams as ViewGroup.MarginLayoutParams
        param.bottomMargin =resources.getDimensionPixelSize(resources.getIdentifier("navigation_bar_height", "dimen", "android"))

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    //Getting the current account in the shared Preference
    private fun getAccount(): Account {
        val serializedObject =  context?.getSharedPreferences("Credentials", Context.MODE_PRIVATE )!!
            .getString("account", "")

        return Gson().fromJson(serializedObject, Account::class.java)


    }


    companion object {

        fun newInstance(): RequestFragment {
            return RequestFragment()
        }
    }


}