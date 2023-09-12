package com.example.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.domain.ErrorAPI
import com.example.domain.ErrorAPI.BAD_REQUEST
import com.example.domain.ErrorAPI.UNAUTHRIZED
import com.example.utilis.api.CustomErrorThrow
import com.example.utilis.printToLogD
import com.example.utilis.printToLogE
import com.example.utilis.toast
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "hande-error-tag"

//@AndroidEntryPoint
abstract class BaseActivty<VB : ViewBinding> : AppCompatActivity() {
    private  var _binding:VB?=null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(binding.root)
    }

    abstract fun getViewBinding(): VB


    fun handleErrorGeneral(th: Throwable, func: (() -> Unit)? = null): CustomErrorThrow? {
        "handleErrorGeneral: "+th.message.printToLogE(TAG)

        when (th.message) {
            BAD_REQUEST -> {
                toast( BAD_REQUEST)
            }
            UNAUTHRIZED -> {
                toast(UNAUTHRIZED)
            //                finishAffinity()
            }
            else -> {
                if (th.cause is CustomErrorThrow) {
                    val cause = th.cause as CustomErrorThrow
                    toast(cause.value+"Custom error")
                    return cause

                } else {
//                    toast("${th.message}")
                    th.message.toString().printToLogD("error-handeled")
                }
            }
        }
        return null
    }
}

