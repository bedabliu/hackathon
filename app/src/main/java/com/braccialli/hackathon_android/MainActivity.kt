package com.braccialli.hackathon_android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import okhttp3.OkHttpClient
import org.jetbrains.annotations.NotNull


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = GuestsAdapter()

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val okHttpClient = OkHttpClient()

        var apolloClient:ApolloClient = ApolloClient.builder().serverUrl(getString(R.string.server))
                .okHttpClient(okHttpClient)
                .build()
        apolloClient.query(
                AllGuestsQuery.builder().build()
        ).enqueue(object : ApolloCall.Callback<AllGuestsQuery.Data>(){
            override fun onResponse(response: Response<AllGuestsQuery.Data>) {
                if(response.data()!= null){
                    runOnUiThread {
                        (viewAdapter as GuestsAdapter).updateData(response.data()!!.getAllGuests)
                    }
                }
            }

            override fun onFailure(e: ApolloException) {
                e.printStackTrace()
            }

        })


    }
}
