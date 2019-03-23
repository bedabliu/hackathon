package com.braccialli.hackathon_android

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.TextView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import okhttp3.OkHttpClient
import org.jetbrains.annotations.NotNull


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val okHttpClient = OkHttpClient()

        var apolloClient:ApolloClient = ApolloClient.builder().serverUrl("http://172.16.24.155:3000/graphql")
                .okHttpClient(okHttpClient)
                .build()
        apolloClient.query(
                HostQuery.builder().build()
        ).enqueue(object : ApolloCall.Callback<HostQuery.Data>(){
            override fun onResponse(response: Response<HostQuery.Data>) {
                for(host in response.data()!!.allHosts){
                    println(host.firstname)
                }
            }

            override fun onFailure(e: ApolloException) {
                e.printStackTrace()//To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}
