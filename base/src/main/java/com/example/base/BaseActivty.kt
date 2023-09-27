package com.example.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.bas.R
import com.example.bas.databinding.ActivityBaseBinding
import com.example.domain.ErrorAPI.BAD_REQUEST
import com.example.domain.ErrorAPI.UNAUTHRIZED
import com.example.utilis.api.CustomErrorThrow
import com.example.utilis.printToLogD
import com.example.utilis.setTransparentStatusBar
import com.example.utilis.toast
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "hande-error-tag"

@AndroidEntryPoint
abstract class BaseActivty(private val layoutResource:Int) : AppCompatActivity() {
    private var viewBase:ActivityBaseBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBase=ActivityBaseBinding.inflate(layoutInflater)
        setContentView(viewBase!!.root)

        val activtyView= LayoutInflater.from(this).
        inflate(layoutResource,viewBase?.flContent,false)
        viewBase?.flContent?.addView(activtyView)



        setTransparentStatusBar()
    }

    fun handleErrorGeneral(th: Throwable, func: (() -> Unit)? = null): CustomErrorThrow? {

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
    private var sweetAlert:SweetAlertDialog?=null
    fun showProgress(){
        hideProgress()
        sweetAlert=SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE).apply {
            progressHelper.barColor=Color.BLUE
            titleText = getString(R.string.loading)
            setCancelable(true)
        }
        sweetAlert?.show()
    }

    fun hideProgress(){
        sweetAlert?.dismissWithAnimation()
        sweetAlert=null
    }
}

