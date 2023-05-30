package dev.carlos.paywallet.fragments.services

import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference
import dev.carlos.paywallet.fragments.MainActivity
import dev.carlos.paywallet.fragments.dataClass.MenuItem
import dev.carlos.paywallet.fragments.interfaces.RequestType

class FirebaseDBService {
    private var databaseRef: DatabaseReference = FirebaseDatabase.getInstance().reference

    private val foodMenu = "food_menu"

    fun readAllMenu(menuApi: MainActivity, requestType: RequestType) {
        val menuList = ArrayList<MenuItem>()

        val menuDbRef = databaseRef.child(foodMenu)
        menuDbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap in snapshot.children) {
                    val item = MenuItem(
                        imageUrl = snap.child("item_image_url").value.toString(),
                        itemName = snap.child("item_name").value.toString(),
                        itemPrice = snap.child("item_price").value.toString().toFloat(),
                        //itemShortDesc = snap.child("item_desc").value.toString(),
                    )
                    menuList.add(item)
                }
                menuList.shuffle() //so that every time user can see different items on opening app
                menuApi.onFetchSuccessListener(menuList, requestType)
            }

            override fun onCancelled(error: DatabaseError) {
                // HANDLE ERROR
            }
        })
    }

    fun insertMenuItem(item: MenuItem) {
        val menuRef = databaseRef.child(foodMenu)

        menuRef.setValue(item)
    }
}