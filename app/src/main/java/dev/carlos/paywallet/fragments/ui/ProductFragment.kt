package dev.carlos.paywallet.fragments.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.carlos.paywallet.R
import dev.carlos.paywallet.databinding.FragmentProductBinding
import dev.carlos.paywallet.fragments.MainActivity
import dev.carlos.paywallet.fragments.SavedPreference
import dev.carlos.paywallet.fragments.adapters.RecyclerFoodItemAdapter
import dev.carlos.paywallet.fragments.adapters.RecyclerSingleItemAdapter
import dev.carlos.paywallet.fragments.dataClass.MenuItem
import dev.carlos.paywallet.fragments.dataClass.User
import dev.carlos.paywallet.fragments.interfaces.MenuApi
import dev.carlos.paywallet.fragments.interfaces.RequestType

class ProductFragment : Fragment(), RecyclerSingleItemAdapter.OnItemClickListener, MenuApi {

    //Database and user dataclass
    private lateinit var user: User
    private lateinit var auth: FirebaseAuth

    private val messages = listOf("Lookin' good, right?", "¿Listx para ordenar?", "Yummy!", "Delicioso!", "¡Buena elección!")
    private var messageIndex = 0


    private lateinit var navController: NavController

    private var _binding: FragmentProductBinding? = null

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
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("id")
        val name = arguments?.getString("nombre")
        val price = arguments?.getFloat("precio").toString().toFloat()
        val imagen = arguments?.getString("imagen.completeUrl")
        val desc = arguments?.getString("descripcion")
        val categoria = arguments?.getString("categoria")

        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences(SavedPreference.toString(), Context.MODE_PRIVATE)

        val allItems = mutableListOf<MenuItem>()
        val item = id?.let {
            if (categoria != null && imagen != null && name != null && desc != null) {
                val newItem = MenuItem(
                    itemID = it,
                    itemCategory = categoria,
                    imageUrl = imagen,
                    itemName = name,
                    itemPrice = price,
                    itemShortDesc = desc
                )
                allItems.add(newItem)
                btnAddToCart(newItem)
            }
            val itemRecyclerView = binding.rvProduct
            val recyclerFoodAdapter = RecyclerSingleItemAdapter(
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

    override fun onFetchSuccessListener(list: ArrayList<MenuItem>, requestType: RequestType) {
        TODO("Not yet implemented")
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onItemClick(item: MenuItem) {
        val message = if (messageIndex < messages.size) {
            messages[messageIndex]
        } else {
            messageIndex = 0
            messages[messageIndex]
        }

        val toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
            toast.show()
            messageIndex++
        }

    override fun onPlusBtnClick(item: MenuItem) {
        TODO("Not yet implemented")
    }

    override fun onMinusBtnClick(item: MenuItem) {
        TODO("Not yet implemented")
    }

    override fun btnAddToCart(item: MenuItem) {
        binding.btnProducto1.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentProducto_to_fragmentInstrucciones, bundleOf(
                "id" to item.itemID,
                "imagen.completeUrl" to item.imageUrl,
                "descripcion" to item.itemShortDesc,
                "nombre" to item.itemName,
                "precio" to item.itemPrice,
                "categoria" to item.itemCategory
            )
            )
        }
    }


}