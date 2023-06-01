package dev.carlos.paywallet.fragments.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.carlos.paywallet.R
import dev.carlos.paywallet.databinding.FragmentOrderBinding
import dev.carlos.paywallet.fragments.MainActivity
import dev.carlos.paywallet.fragments.dataClass.User


class OrderFragment : Fragment() {

    //Database and user dataclass
    private lateinit var user: User
    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentOrderBinding? = null
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
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db.collection("ordenes").whereEqualTo("cliente", user.rfid)
            .get().addOnSuccessListener {
                val productos = it.documents[0]["productos"] as ArrayList<Map<String, String>>

                productos.forEach {
                    it["detalles"]
                    it["platillo"]
                }
            }
    }
}