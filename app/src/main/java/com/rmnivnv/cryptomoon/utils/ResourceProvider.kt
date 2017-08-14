package com.rmnivnv.cryptomoon.utils

import android.support.v4.content.ContextCompat
import com.rmnivnv.cryptomoon.MainApp

/**
 * Created by rmnivnv on 01/08/2017.
 */
class ResourceProvider(val app: MainApp) {

    fun getString(id: Int) = app.getString(id)

    fun getDrawable(id: Int) = ContextCompat.getDrawable(app, id)

    fun getColor(id: Int) = ContextCompat.getColor(app, id)

}