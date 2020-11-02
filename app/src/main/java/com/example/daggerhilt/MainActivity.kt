 package com.example.daggerhilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daggerhilt.adapter.BlogAdapter
import com.example.daggerhilt.models.Blog
import com.example.daggerhilt.retrofit.BlogRetrofit
import com.example.daggerhilt.utils.DataState
import com.example.daggerhilt.viewModel.MainStateEvent
import com.example.daggerhilt.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder
import javax.inject.Inject


 @AndroidEntryPoint
class MainActivity : AppCompatActivity() {


     lateinit var progressBar:ProgressBar
     lateinit var recyclerView: RecyclerView
     lateinit var blogAdapter: BlogAdapter

     private  val TAG = "MainActivity"
     private val viewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//         text=findViewById(R.id.text)
        progressBar=findViewById(R.id.progress_bar)

        initRecycler()

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogEvents)

    }

     private fun subscribeObservers(){
         viewModel.dataState.observe(this, Observer { dataState ->
             when (dataState) {
                 is DataState.Success<List<Blog>> -> {
                     displayProgressBar(false)
                     appendBlogs(dataState.data)
                 }
                 is DataState.Error -> {
                     displayProgressBar(false)
                     Log.e(TAG, "subscribeObservers: error found" )
                 }
                 is DataState.Loading -> {
                     displayProgressBar(true)

                 }
             }
         })
     }

     private fun initRecycler(){
         recyclerView=findViewById(R.id.recycler)
         blogAdapter= BlogAdapter(this,ArrayList())

         recyclerView.apply {
             setHasFixedSize(true)
             layoutManager=LinearLayoutManager(this@MainActivity)
             adapter=blogAdapter
         }
     }

     private fun displayProgressBar(isDisplayed:Boolean){
         progressBar.visibility= if(isDisplayed) View.VISIBLE else View.GONE
     }

     private fun appendBlogs(blogs:List<Blog>){
        blogAdapter.setData(blogs)
     }
}