package com.gtechnofy.fun_imageloading

object ImageUtil {
    fun getCompleteUrl(url: String?) : String? {
        if (url.isNullOrEmpty()) return url
        return Constants.IMAGE_HOST + Constants.WIDTH_BANNER + url
    }
}