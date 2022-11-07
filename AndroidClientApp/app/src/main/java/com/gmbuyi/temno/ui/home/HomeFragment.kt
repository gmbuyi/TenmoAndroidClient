package com.gmbuyi.temno.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmbuyi.temno.api.models.AuthenticatedUser
import com.gmbuyi.temno.api.services.ConsumingApi
import com.gmbuyi.temno.api.services.TokenInterceptor
import com.gmbuyi.temno.databinding.FragmentHomeBinding
import com.google.gson.Gson

/**
 * Home Fragment is the Main fragment that shows account details
 * of the current user
 *
 */

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {




        this.activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        val textView: TextView = binding.textHome
        val balanceTextview : TextView = binding.balance
        val progress : View = binding.progressBarAccount.root
        val logoutBtn: ImageView = binding.logoutBtn


        // logout when clicking to the logout imageView
        logoutBtn.setOnClickListener {
            activity?.finish()
        }

        progress.visibility = View.VISIBLE

            val context =this@HomeFragment.context!!

        //Getting the current account details from the api
        ConsumingApi(context,TokenInterceptor(context))
                     .getAccount(balanceTextview,progress,
                        getAuthenticatedUser().user.id
                     )



        homeViewModel.text.observe(viewLifecycleOwner) {

            val username = getAuthenticatedUser().user.username
            textView.text = username

        }



        val param = root.layoutParams as ViewGroup.MarginLayoutParams
        param.bottomMargin =resources.getDimensionPixelSize(resources.getIdentifier("navigation_bar_height", "dimen", "android"))

        return root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        this.activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

    }

  //Getting the authenticatedUser object from the shared Preferences

    private fun getAuthenticatedUser(): AuthenticatedUser{
        val serializedObject =  context?.getSharedPreferences("Credentials", Context.MODE_PRIVATE )!!
            .getString("authenticatedUser", "")

       return Gson().fromJson(serializedObject, AuthenticatedUser::class.java)


    }


}