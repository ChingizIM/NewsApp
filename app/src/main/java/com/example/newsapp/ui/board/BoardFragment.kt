package com.example.newsapp.ui.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapp.Prefs
import com.example.newsapp.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {

    private lateinit var binding: FragmentBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BoardAdapter {
                Prefs(requireContext()).saveState(toString())
            // Prefs(requireContext) - создали экземпляр класса
            // saveState() - вызвали его метод
            findNavController().navigateUp()
            // реакция на нажатие кнопки "START", "SKIP"
        }
        binding.viewPager.adapter = adapter //skip

        val dotsIndicator = binding.indicator
        val viewPager = binding.viewPager //skip
        dotsIndicator.setViewPager2(viewPager) // индикатор

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int //skip.. registerOnPageChangeCallback слушатель, какая страница сейчас открывается
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position == 2) {
                    binding.btnSkip.visibility = View.INVISIBLE //исчезает на третьей странице
                } else {
                    binding.btnSkip.visibility = View.VISIBLE // виден на первых двух страницах
                } // skip //

                //super.onPageScrollStateChanged(state)
            }
        })
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    requireActivity()
                }
            }
        )

        binding.btnSkip.setOnClickListener {
            findNavController().navigateUp() //skip //
        }
    }
}


