package dev.carlos.paywallet.fragments.interfaces

import dev.carlos.paywallet.fragments.dataClass.MenuItem

enum class RequestType {
    READ, OFFLINE_UPDATE
}

interface MenuApi {
    fun onFetchSuccessListener(list: ArrayList<MenuItem>, requestType: RequestType)
}