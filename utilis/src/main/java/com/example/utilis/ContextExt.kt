package com.example.utilis

import android.app.Activity
import android.util.Log
import android.widget.Toast
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