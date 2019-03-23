package com.braccialli.hackathon_android

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.braccialli.hackathon_android.activities.GuestDetailActivity

/**
 * Created by tiagobraccialli on 3/23/19.
 */

class GuestsAdapter() : RecyclerView.Adapter<GuestsAdapter.MyViewHolder>() {

    private var guestsDataset: MutableList<AllGuestsQuery.GetAllGuest>? = null

    class MyViewHolder(val view:View, val context: Context) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val firsName: TextView = itemView.findViewById(R.id.guest_first_name)
        val lastName: TextView = itemView.findViewById(R.id.guest_last_name)
        val hostName: TextView = itemView.findViewById(R.id.guest_host_name)
        val locationName: TextView = itemView.findViewById(R.id.guest_location)

        var guestId: String? = null

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val it = Intent(context, GuestDetailActivity::class.java)
            it.putExtra("guestId", guestId)
            context.startActivity(it)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): GuestsAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.guest_view, parent, false)
        return MyViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.guestId = guestsDataset!![position].id
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