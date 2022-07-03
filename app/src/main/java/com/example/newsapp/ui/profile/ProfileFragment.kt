package com.example.newsapp.ui.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.newsapp.Prefs
import com.example.newsapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var change: Boolean = false
    private lateinit var text: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnImageView.setOnClickListener {
            getContent.launch("image/*")
        }
            // binding.editTextProfile.addTextChangedListener(object : TextWatcher {
            //override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            //}

            //override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            //}

            //override fun afterTextChanged(p0: Editable?) {
              //  text = binding.editTextProfile.text.toString()
                //Prefs(requireContext()).saveState(text)
            //}
        //})
        //binding.editTextProfile.setText(Prefs(requireContext()).toString())
    }

    override fun onPause() {
        super.onPause()
        text = binding.editTextProfile.text.toString()
        Prefs(requireContext()).saveState(text)
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            Glide.with(binding.btnImageView).load(uri).centerCrop().into(binding.btnImageView)
        }
}






