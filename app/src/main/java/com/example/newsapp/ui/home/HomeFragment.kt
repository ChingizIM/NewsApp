package com.example.newsapp.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.models.News
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.concurrent.timer
import kotlin.math.log

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: NewsAdapter
    private var bolean:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = NewsAdapter {
            val news = adapter.getItem(it)
            val bundle = Bundle()
            bundle.putSerializable("news", news)
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
            bolean=true
            findNavController().navigate(R.id.newsFragment, bundle)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("Home", "onViewCreated")
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.newsFragment)
        }
        parentFragmentManager.setFragmentResultListener(
            "us_news",
            viewLifecycleOwner
        ) { requestkey, bundle ->
            val news = bundle.getSerializable("news") as News
            val poss:Int?=null
            if(bolean) {
                poss?.let { adapter.replaceItem(news, it) }
            }else {
                adapter.addItem(news)
                //val list1 = replaceItem(
                //  oldValue: String,
                // newValue: String,
                //ignoreCase: Boolean = false
                //): String
                //)
            }

            Log.e("Home", "text ${news.title} ${news.createdAt}")
            Toast.makeText(requireContext(), news.title, Toast.LENGTH_SHORT).show()
        }


        binding.recyclerview.adapter = adapter
    }
    //fun replaceItem(
      //  newItem: SVGTransform,
        //index: Int
    // ): SVGTransform

    override fun onDestroyView() {
        super.onDestroyView()
        //   binding = null
    }
}