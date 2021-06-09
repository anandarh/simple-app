package com.anandarh.githubuserapp.utilities

import android.content.Context
import java.io.InputStream

class ResourceProvider(private val mContext: Context) {

    fun getContext(): Context {
        return mContext
    }

    fun getAsset(fileName: String): InputStream {
        return mContext.assets.open(fileName)
    }

}