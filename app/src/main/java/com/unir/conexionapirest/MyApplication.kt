package com.unir.conexionapirest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Esta clase permite la gestión global de dependencias con Hilt
@HiltAndroidApp
class MyApplication: Application(){
    // You can add other application-level initialization code here if needed.
}