package com.gmbuyi.temno.ui.transfer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.gmbuyi.temno.databinding.FragmentTransferBinding
import com.google.gson.Gson


/**
 * Fragment that shows the list of transfers
 */

class TransferFragment : Fragment() {



    private var _binding: FragmentTransferBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[TransferViewModel::class.java]

        _binding = FragmentTransferBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textTransfer
        val listView : ListView = binding.listItem
        val newTransferBtn = binding.createTransferBtn

        //create new Transfer
        newTransferBtn.setOnClickListener {
            val progressDialog = LoadingProgressDialog(requireContext(),R.style.TransparentDialog).show()
            ConsumingApi(requireContext(), TokenInterceptor(context))
                .getListAccount(progressDialog, TransferType.ID_SEND,getAccount())
         }



        val userAccount =getAccount().account_id

        val progress : View = binding.progressBarTransfer.root
        progress.visibility = View.VISIBLE

        //getting List of Transfers
        ConsumingApi(requireContext(),TokenInterceptor(context))
            .getListTransfer(listView,progress,
                userAccount
            )



        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }


        // adjust the fragment to the bottom navigator menu
        val param = root.layoutParams as ViewGroup.MarginLayoutParams
        param.bottomMargin =resources.getDimensionPixelSize(resources.getIdentifier("navigation_bar_height", "dimen", "android"))

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Getting current Account saved in the shared Preferences
    private fun getAccount(): Account {
        val serializedObject =  context?.getSharedPreferences("Credentials", Context.MODE_PRIVATE )!!
            .getString("account", "")

        return Gson().fromJson(serializedObject, Account::class.java)


    }




    companion object {

        fun newInstance(): TransferFragment {
            return TransferFragment()
        }
    }



}