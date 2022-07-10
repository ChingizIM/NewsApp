package com.example.newsapp.ui.home

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.newsapp.App
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.models.News
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.Appendable
import java.text.FieldPosition
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.concurrent.timer
import kotlin.math.log

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter : NewsAdapter
    private var bolean:Boolean=false
    private var list = arrayListOf<News>()
    //private val adapter = NewsAdapter(this::onClick, this::onLongClock)

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

        val list= App.database.newsDao().getAll()
        // в list-е будут все записи, чтобы записи отображались, мы должны list передать NewsAdapter-у
        adapter.addItems(list)
        // Далее переходим в NewsAdapter
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
            val poss: Int? = null
            if (bolean) {
                poss?.let { adapter.replaceItem(news, it) }
            } else {
                adapter.addItem(news)
            }

            Log.e("Home", "text ${news.title} ${news.createdAt}")
            Toast.makeText(requireContext(), news.title, Toast.LENGTH_SHORT).show()
        }


        binding.recyclerView.adapter = adapter
        adapter.onItemLongClick = {
            createAlertDialog(it)

            binding.etSearch.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    list = App.database.newsDao().getSearch(s.toString()) as ArrayList<News>
                    adapter.addList(list)
                }
            })
            binding.recyclerView.adapter = adapter
            list = App.database.newsDao().sortAll() as ArrayList<News>
            adapter.addList(list)
        }
    }
    private fun createAlertDialog(it:Int) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Внимание!")
        builder.setMessage("Вы уверены, что хотите удалить?")
        builder.setPositiveButton("Удалить") { _, _ ->
            val news = adapter.getItem(it)
            adapter.deleteItem(it)
            App.database.newsDao().deleteItem(news)
            adapter.notifyDataSetChanged()
        }
        builder.setNegativeButton("Отмена") { _, _ ->
        }
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}