package dev.carlos.paywallet.fragments.dataClass

data class MenuItem(
    var itemCategory: String = "ITEM_ID",
    var imageUrl: String = "IMAGE_URL",
    var itemName: String = "ITEM_NAME",
    var itemPrice: Float = 0.0F,
    //var itemShortDesc: String = "ITEM_DESC",
    var itemStock: Int = 0,
    var quantity: Int = 0
)