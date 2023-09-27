package com.example.utilis

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment

fun Fragment.toast(message:String){
    Toast.makeText(this.requireContext(),message,Toast.LENGTH_SHORT).show()
}

fun Activity.toast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun Any?.printToLogD(tag: String = "DEBUG_LOG") {
    Log.d(tag, toString())
}

fun Any?.printToLogE(tag: String = "DEBUG_LOG") {
    Log.e(tag, toString())
}


fun Activity.setTransparentStatusBar() {

    WindowCompat.setDecorFitsSystemWindows(window,false)
    window.statusBarColor=Color.TRANSPARENT
/*    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
    window?.statusBarColor = Color.TRANSPARENT*/


}