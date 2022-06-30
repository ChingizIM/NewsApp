package com.example.newsapp.ui.home

import android.R
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ItemNewsBinding
import com.example.newsapp.models.News
import java.text.SimpleDateFormat
import java.util.*


class NewsAdapter(private val onClick:(position: Int) -> Unit): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    private val list = mutableListOf<News>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(list[position])
        holder.itemView.setOnClickListener{
            onClick(position)}

           if(position % 2 == 0)
            //{
                holder.itemView.setBackgroundColor(Color.GRAY);

                //holder.rootView.setBackgroundResource(R.color.black);
                //holder.setBackgroundColor(R.color.black)
                //myView.setBackgroundColor(R.color.black) }
            else
                //holder.rootView.setBackgroundColor(Color.WHITE);
                holder.itemView.setBackgroundColor(Color.WHITE);
    }




    override fun getItemCount() = list.size
    fun addItem(news: News) {

        list.add(0,news)
       notifyItemInserted(0)

        notifyItemInserted(list.indexOf(news))

       // notifyItemInserted(list.size - 1)
      // notifyItemInserted(list.indexOf(news))
    }

    fun getItem(pos: Int): News {
     return list[pos]
    }
    fun replaceItem(news: News,poss:Int){
        list.set(poss,news)
        notifyItemChanged(poss)
    }

    inner class ViewHolder(private var binding : ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {


            binding.textTitle.text = news.title

            binding.textLong.text = convertingToTime(news.createdAt)



        //if (adapterPosition%2==0){
          //  binding.itemNews.setBackgroundColor(Color.BLACK)
            //binding.textTitle.setTextColor(Color.WHITE)
            //binding.textLong.setTextColor(Color.WHITE)
        }

            }
        private fun convertingToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("HH:mm, dd MMM yyyy")
            return format.format(date)

        }

    }
