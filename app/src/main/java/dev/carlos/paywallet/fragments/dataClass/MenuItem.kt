package dev.carlos.paywallet.fragments.dataClass

data class MenuItem(
    var itemID: String = "ID",
    var itemCategory: String = "ITEM_ID",
    var imageUrl: String = "ITEM_IMAGE",
    var itemName: String = "ITEM_NAME",
    var itemPrice: Float = 0.0F,
    var itemShortDesc: String = "ITEM_SHORT_DESC",
    var itemStock: Int = 0,
    var quantity: Int = 0
)