package com.android.example.demomvvm.core.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.example.demomvvm.core.util.SingleLiveEvent

/**
 * Base class for View Models to declare the common LiveData objects in one place
 */
abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    val hideKeyboard: SingleLiveEvent<Boolean> = SingleLiveEvent()
}