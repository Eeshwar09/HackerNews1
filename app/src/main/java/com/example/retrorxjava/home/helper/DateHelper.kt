package com.example.retrorxjava.home.helper

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object DateHelper {
  @SuppressLint("SimpleDateFormat")
  fun date(date:String):String{

      val newFormat = SimpleDateFormat("yyyy-MM-dd")
      val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
      val normalDate = format.parse(date)
      return newFormat.format(normalDate!!)
  }

}