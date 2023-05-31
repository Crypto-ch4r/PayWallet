package dev.carlos.paywallet.fragments.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.carlos.paywallet.R
import dev.carlos.paywallet.databinding.FragmentProductBinding
import dev.carlos.paywallet.fragments.MainActivity
import dev.carlos.paywallet.fragments.dataClass.User

class ProductFragment : Fragment() {

    //Database and user dataclass
    private lateinit var user: User
    private lateinit var auth: FirebaseAuth

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

        val db = Firebase.firestore


    }
}