package dev.carlos.paywallet.fragments.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dev.carlos.paywallet.R
import dev.carlos.paywallet.databinding.FragmentLoginBinding
import dev.carlos.paywallet.fragments.MainActivity
import dev.carlos.paywallet.fragments.SavedPreference
import dev.carlos.paywallet.fragments.dataClass.User
import dev.carlos.paywallet.fragments.removeMenu

class LoginFragment : Fragment() {

    //Database and user dataclass
    private lateinit var user: User
    private lateinit var auth: FirebaseAuth

    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText

    private lateinit var loginButton: MaterialButton
    private lateinit var registerButton: MaterialButton
    private lateinit var forgotPasswordButton: MaterialButton

    private lateinit var navController: NavController

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).removeMenu()

        //Firebase access
        auth = Firebase.auth
        var firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Firebase.firestore
        val preferences =
            requireActivity().getSharedPreferences(SavedPreference.toString(), Context.MODE_PRIVATE)


        loginButton.setOnClickListener {
            if (email.text.toString().isEmpty() || password.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT)
                    .show()
            } else {
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            db.collection("usuarios")
                                .whereEqualTo("noControl", email.text.toString())
                                .get().addOnSuccessListener { documents ->
                                    for (document in documents){
                                        user = User(
                                            document.data["control_num"].toString(),
                                            document.data["password"].toString(),
                                            document.data["names"].toString(),
                                            document.data["lastnames"].toString(),
                                            document.data["rfid"].toString()
                                        )
                                    }
                                    val user = auth.currentUser
                                    Toast.makeText(
                                        requireContext(),
                                        "Bienvenido ${user?.email}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    preferences.edit().putString("email", user?.email).apply()
                                    navController.navigate(R.id.action_login_to_menu)
                                }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Error al iniciar sesión",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}