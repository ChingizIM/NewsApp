package com.example.newsapp.kotlinFile

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.sT(msg:String){
    Toast.makeText(requireContext(),msg,Toast.LENGTH_SHORT).show()
}