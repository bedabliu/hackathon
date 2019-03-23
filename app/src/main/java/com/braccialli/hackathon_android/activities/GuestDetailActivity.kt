package com.braccialli.hackathon_android.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.apollographql.apollo.exception.ApolloException
import com.braccialli.hackathon_android.GuestQuery
import com.braccialli.hackathon_android.R
import com.braccialli.hackathon_android.models.GuestModel
import kotlinx.android.synthetic.main.activity_guest_detail.*
import org.jetbrains.anko.toast

class GuestDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_detail)

        val it = intent
        val guestId = it.getStringExtra("guestId")

        GuestModel().getGuestDetail(getString(R.string.server), guestId) { guest: GuestQuery.Guest?, error: ApolloException? ->
            guestCallback(guest, error)
        }
    }

    private fun guestCallback(guest: GuestQuery.Guest?, error: ApolloException?) {
        if (error != null) {
            toast(error.localizedMessage)
            return
        }

        if (guest == null) {
            toast(getString(R.string.no_quest))
            return
        }

        txtFirstname.setText(guest.firstname())
        txtLastname.setText(guest.lastname())
        txtEmail.setText(guest.email())
        txtCompany.setText(guest.company())

        guest.location().let {
            txtLocationName.setText(it?.fragments()?.locatFields()?.name())
            txtLocationName.setText(it?.fragments()?.locatFields()?.address())
        }

        guest.host().let {
            if (!it!!.isEmpty()) {
                txtHostName.setText(it[0].fragments().hostsFields().firstname())
                txtHostEmail.setText(it[0].fragments().hostsFields().email())
            }
        }

    }

}
