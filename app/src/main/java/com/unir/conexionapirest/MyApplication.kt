package com.unir.conexionapirest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Esta clase permite la gestión global de dependencias con Hilt. No hacer nada con ella.
@HiltAndroidApp
class MyApplication: Application()