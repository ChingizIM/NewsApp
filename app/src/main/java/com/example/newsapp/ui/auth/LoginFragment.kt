package com.example.newsapp.ui.auth

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.newsapp.databinding.FragmentLoginBinding
import com.example.newsapp.kotlinFile.sT
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var auth: FirebaseAuth
    private var timer: CountDownTimer? = null
    var verificationId = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser
        if (currentUser != null) {
            findNavController().navigateUp()
        }
        binding.btnContinue.setOnClickListener {
            login()
            binding.editPhone.visibility = View.GONE
            binding.btnContinue.visibility = View.GONE
            binding.editCode.visibility = View.INVISIBLE
            binding.btnCode.visibility = View.INVISIBLE
            binding.textTimer.visibility = View.VISIBLE
            startCountDownTimer(60000)
        }
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Log.e("Login", "onVerificationCompleted")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.e("Login", "onVerificationFailed: ${p0.message}")
                sT("Ошибка")
            }
            override fun onCodeAutoRetrievalTimeOut(p0: String) {
                super.onCodeAutoRetrievalTimeOut(p0)
                //Срабатывает после оканчания setTimeout
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                verificationId = p0
            }
        }
        binding.btnCode.setOnClickListener {
            val vCode = binding.editCode.text.toString().trim()
            if (vCode.isEmpty()) {
                sT("Введите Код")
            } else {
                authenticate()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }

            })

        binding.btnExit.setOnClickListener {
            requireActivity().finish()
        }
        binding.btnChangeNumber.setOnClickListener {
            binding.editPhone.visibility = View.VISIBLE
            binding.btnContinue.visibility = View.VISIBLE
            binding.editCode.visibility = View.GONE
            binding.btnCode.visibility = View.GONE
            binding.textTimer.visibility = View.GONE
            binding.btnChangeNumber.visibility = View.GONE
        }
    }

    private fun authenticate() {
        val verifNumber = binding.editCode.text.toString()
        val credential: PhoneAuthCredential =
            PhoneAuthProvider.getCredential(verificationId, verifNumber)
        signInWithPhoneAuthCredential(credential)
    }

    private fun login() {
        val number = binding.editPhone.text.toString().trim()
        if (number.isNotEmpty()) {
            requestSMS(number)
        } else {
            sT("Введите номер телефона")
        }
    }

    private fun requestSMS(number: String) {
        val phoneNumber = binding.editPhone.text.toString()
        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    sT("Успешная авторизация")
                    val user = task.result?.user
                    findNavController().navigateUp()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        sT("Ошибка авторизации")
                    }
                    // Update UI
                }
            }
    }

    private fun startCountDownTimer(timeMillis: Long) {
        timer?.cancel()
        timer = object : CountDownTimer(timeMillis, 1) {
            override fun onTick(p0: Long) {
                val time = p0 / 1000
                binding.textTimer.text = time.toString()
            }

            override fun onFinish() {
                sT("Время Истекло")
                binding.textTimer.text = "0"
                binding.textTimer.visibility = View.GONE
                binding.btnChangeNumber.visibility = View.VISIBLE
            }
        }.start()

    }
}
