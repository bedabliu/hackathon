package com.braccialli.hackathon_android.models

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.braccialli.hackathon_android.GuestQuery
import okhttp3.OkHttpClient

class GuestModel {

    fun getGuestDetail(server: String, id: String, callback: (GuestQuery.Guest?, ApolloException?) -> Unit ) {
        val apolloClient: ApolloClient = ApolloClient.builder().serverUrl(server)
                .okHttpClient(OkHttpClient())
                .build()
        apolloClient.query(
                GuestQuery.builder().guest(id).build()
        ).enqueue(object : ApolloCall.Callback<GuestQuery.Data>(){
            override fun onResponse(response: Response<GuestQuery.Data>) {
                val guest = response.data()?.guest()
                callback(guest, null)
            }

            override fun onFailure(e: ApolloException) {
                callback(null, e)
            }
        })
    }

}