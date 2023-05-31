package dev.carlos.paywallet.fragments.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.carlos.paywallet.R
import dev.carlos.paywallet.databinding.FragmentLoginBinding
import dev.carlos.paywallet.databinding.FragmentdotdBinding
import dev.carlos.paywallet.fragments.MainActivity
import dev.carlos.paywallet.fragments.SavedPreference
import dev.carlos.paywallet.fragments.adapters.RecyclerFoodItemAdapter
import dev.carlos.paywallet.fragments.dataClass.MenuItem
import dev.carlos.paywallet.fragments.dataClass.User
import dev.carlos.paywallet.fragments.interfaces.MenuApi
import dev.carlos.paywallet.fragments.interfaces.RequestType


class DOTDFragment() : Fragment(), RecyclerFoodItemAdapter.OnItemClickListener, MenuApi {

    //Database and user dataclass
    private lateinit var user: User
    private lateinit var auth: FirebaseAuth

    private lateinit var navController: NavController

    private var _binding: FragmentdotdBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity)

        //Firebase access
        auth = Firebase.auth
        var firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentdotdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Firebase.firestore
        val preferences =
            requireActivity().getSharedPreferences(SavedPreference.toString(), Context.MODE_PRIVATE)

              val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences(SavedPreference.toString(), Context.MODE_PRIVATE)

        db.collection("platillos")
            .get()
            .addOnSuccessListener {
                val allItems = mutableListOf<MenuItem>()
                for (document in it) {
                    var imageUrl = document.getString("imagen.completeUrl")
                    var itemShortDesc = document.getString("descripcion")
                    val item = MenuItem(
                        itemCategory = document.data["categoria"].toString(),
                        imageUrl = imageUrl.toString(),
                        itemName = document.data["nombre"].toString(),
                        itemPrice = document.data["precio"].toString().toFloat(),
                        itemShortDesc = itemShortDesc.toString()
                    )
                    allItems.add(item)
                    println(imageUrl)
                    println(itemShortDesc)
                    println("F U pay me")
                }

                val itemRecyclerView = binding.rvdelDia
                val recyclerFoodAdapter = RecyclerFoodItemAdapter(
                    requireContext(),
                    allItems,
                    sharedPreferences?.getInt("loadItemImages",0) ?: 0,
                    this
                )
                itemRecyclerView.adapter = recyclerFoodAdapter
                itemRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerFoodAdapter.filterList(allItems) //display complete list
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: MenuItem) {
        val db = Firebase.firestore
        db.collection("platillos")
            .whereEqualTo("id", item.itemID) // Asegúrate de reemplazar "id" con el campo adecuado en Firestore
            .get()
            .addOnSuccessListener { snapshot ->
                val document = snapshot.documents.firstOrNull()
                if (document != null) {
                    val imageUrl = document.getString("imagen.completeUrl")
                    val itemShortDesc = document.getString("descripcion")
                    val selectedItem = document.data?.get("nombre")?.let {
                        MenuItem(
                            //itemCategory = document.data["categoria"].toString(),
                            imageUrl = imageUrl.toString(),
                            itemName = it.toString(),
                            itemPrice = document.data?.get("precio").toString().toFloat(),
                            itemShortDesc = itemShortDesc.toString()
                        )
                    }
                    // Realiza la navegación a la pantalla de información del platillo aquí,
                    // pasando el objeto "selectedItem" como argumento.

                }
            }
    }

    /* override fun onItemClick(item: MenuItem) {
         val db = Firebase.firestore
         db.collection("platillos")
             .whereEqualTo("id", item.id)
             .get()
             .addOnSuccessListener { snapsshot ->


             }
      /*   db.collection("platillos")
             .get()
             .addOnSuccessListener {
                 val items = mutableListOf<MenuItem>()
                 for (document in it) {
                     var idItem = document.id
                     var imageUrl = document.getString("imagen.completeUrl")
                     var itemShortDesc = document.getString("descripcion")
                     val item = MenuItem(
                         itemCategory = document.data["categoria"].toString(),
                         imageUrl = imageUrl.toString(),
                         itemName = document.data["nombre"].toString(),
                         itemPrice = document.data["precio"].toString().toFloat(),
                         itemShortDesc = itemShortDesc.toString()
                     )
                     items.add(item)
                 }
             }*/
         navController.navigate(R.id.action_delDia_to_fragmentProducto)
     }*/

    override fun onPlusBtnClick(item: MenuItem) {
        TODO("Not yet implemented")
    }

    override fun onMinusBtnClick(item: MenuItem) {
        TODO("Not yet implemented")
    }

    override fun onFetchSuccessListener(list: ArrayList<MenuItem>, requestType: RequestType) {
        TODO("Not yet implemented")
    }
}