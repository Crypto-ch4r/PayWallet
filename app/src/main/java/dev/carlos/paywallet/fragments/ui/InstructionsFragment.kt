package dev.carlos.paywallet.fragments.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.carlos.paywallet.R
import dev.carlos.paywallet.databinding.FragmentInstructionsBinding
import dev.carlos.paywallet.fragments.MainActivity
import dev.carlos.paywallet.fragments.SavedPreference
import dev.carlos.paywallet.fragments.dataClass.MenuItem
import dev.carlos.paywallet.fragments.dataClass.User

class InstructionsFragment : Fragment() {

    //Database and user dataclass
    private lateinit var user: User
    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity)

        //Firebase access
        auth = Firebase.auth
        var firebaseAuth = FirebaseAuth.getInstance()
        auth.currentUser?.email?.let {
            db.collection("usuarios")
                .whereEqualTo("correo", it)
                .get().addOnSuccessListener {
                    user = it.documents[0].toObject(User::class.java)!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)
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

        binding.btnInstrucciones1.setOnClickListener {

            val instru: String = binding.etInstrucciones.text.toString()
            val quantity: Int = binding.etInstrucciones2.text.toString().toInt()

            // Create object "Order" with the necesary data
            val order = MenuItem(
                itemID = id.toString(),
                itemName = name.toString(),
                itemPrice = price,
                imageUrl = imagen.toString(),
                itemShortDesc = desc.toString(),
                itemCategory = categoria.toString(),
                quantity = quantity,
                details = instru
            )

            db.collection("ordenes").whereEqualTo("cliente", user.rfid)
                .get().addOnSuccessListener {

                    if (it.documents.isEmpty()) {
                        db.collection("ordenes")
                            .add(
                                mapOf(
                                    "cliente" to user.rfid,
                                    "fecha" to FieldValue.serverTimestamp(),

                                    "platillos" to FieldValue.arrayUnion(
                                        mapOf(
                                            "detalles" to order.details,
                                            "platillo" to order.itemName
                                        )
                                    ),
                                    "total" to FieldValue.increment((order.itemPrice * order.quantity).toDouble())
                                )
                            ).addOnSuccessListener {
                                findNavController().navigate(R.id.action_fragmentInstrucciones_to_fragmentOrden)
                            }
                    } else {

                        it.documents[0].reference.update(
                            mapOf(
                                "platillos" to FieldValue.arrayUnion(
                                    mapOf(
                                        "detalles" to order.details,
                                        "platillo" to order.itemName
                                    )
                                ),
                                "total" to FieldValue.increment((order.itemPrice * order.quantity).toDouble())
                            )
                        ).addOnSuccessListener {
                            findNavController().navigate(R.id.action_fragmentInstrucciones_to_fragmentOrden)
                        }
                    }
                }
        }
    }

}