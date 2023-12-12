package com.example.ui.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newscleanarch.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    var binding: FragmentSettingsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSettingsBinding.inflate(layoutInflater)

        
        return binding?.root
    }

/*    private fun showDeletAccountDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val view = ConiDialogBinding.inflate(layoutInflater)
        view.root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)


        val str1 = "Start collecting coins without any minimum purchase requirement."

        val str2 =
            "Redeem your coins to receive discounts on your orders or even cover the entire purchase amount."


        view.collectDescriptionFirstTv.text = str1
        view.collectDescriptionSecondTv.text = str2

        builder.setView(view.root)
        val alertDialog = builder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.btnAddToCart.setOnClickListener {

            alertDialog?.cancel()
        }
        alertDialog.show()
    }

    *//*private fun formatDescriptionSpannableText(text: String): SpannableString {
        val spannableString = SpannableString(text)
        spannableString.setSpan(
            ForegroundColorSpan(Color.BLUE),
            0,
            2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }*/
}
