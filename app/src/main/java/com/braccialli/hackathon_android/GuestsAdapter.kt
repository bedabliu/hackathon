package com.braccialli.hackathon_android

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by tiagobraccialli on 3/23/19.
 */

class GuestsAdapter() :
        RecyclerView.Adapter<GuestsAdapter.MyViewHolder>() {

    var guestsDataset: MutableList<AllGuestsQuery.GetAllGuest>? = null


    class MyViewHolder(val view:View) : RecyclerView.ViewHolder(view){
        val firsName: TextView = itemView.findViewById<TextView>(R.id.guest_first_name)
        val lastName: TextView = itemView.findViewById<TextView>(R.id.guest_last_name)
        val hostName: TextView = itemView.findViewById<TextView>(R.id.guest_host_name)
        val locationName: TextView = itemView.findViewById<TextView>(R.id.guest_location)
    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): GuestsAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.guest_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.firsName.text = guestsDataset!![position].firstname
        holder.lastName.text = guestsDataset!![position].lastname
        holder.hostName.text = guestsDataset!![position].host!!.get(0).fragments().hostFields.firstname()
        holder.locationName.text = guestsDataset!![position].location!!.fragments().locationFields.name()
    }

    override fun getItemCount(): Int { if(guestsDataset!=null) return guestsDataset!!.size else return 0 }

    fun updateData(guestsDataset: MutableList<AllGuestsQuery.GetAllGuest>){
        this.guestsDataset = guestsDataset
        notifyDataSetChanged()
    }
}