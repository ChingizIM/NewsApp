package com.example.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.models.News


class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private var news: News? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        news = arguments?.getSerializable("news") as News?

        news?.let {
            binding.editText.setText(it.title)
        }

        binding.btnSave.setOnClickListener {
            save()
        }
    }
    private fun save(){
        val text = binding.editText.text.toString().trim()

        if (news == null) {
            news = News(text, System.currentTimeMillis())
        }else{
                news?.title=text
        }
        // val news = News(text, System.currentTimeMillis())
        val bundle = Bundle()
        bundle.putSerializable("news", news)
       // findNavController().navigate(R.id.navigation_home,bundle)
    parentFragmentManager.setFragmentResult("us_news", bundle)
        findNavController().navigateUp()

    }
}
