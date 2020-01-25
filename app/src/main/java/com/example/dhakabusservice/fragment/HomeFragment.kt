package com.example.dhakabusservice.fragment

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast

import com.example.dhakabusservice.R
import com.example.dhakabusservice.adapter.ListViewAdapter
import com.mancj.materialsearchbar.MaterialSearchBar
import androidx.core.view.isVisible

class HomeFragment(private val activity: Activity, private val busList: ArrayList<String>) :
    Fragment(), MaterialSearchBar.OnSearchActionListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val sourceListView: ListView = rootView.findViewById(R.id.sourceListView)
        val destinationListView: ListView = rootView.findViewById(R.id.destinationListView)

        val sourceListViewAdapter = ListViewAdapter(activity, busList, "sourceListView")
        sourceListView.adapter = sourceListViewAdapter
        val destinationListViewAdapter = ListViewAdapter(activity, busList, "destinationListView")
        destinationListView.adapter = destinationListViewAdapter

        val sourceLocation: MaterialSearchBar = rootView.findViewById(R.id.searchSource)
        sourceLocation.setOnSearchActionListener(this)

        val destinationLocation: MaterialSearchBar = rootView.findViewById(R.id.searchDestination)
        destinationLocation.setOnSearchActionListener(this)

        sourceLocation.addTextChangeListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                activity.runOnUiThread {
                    sourceListView.visibility = View.VISIBLE
                    destinationListView.visibility = View.GONE
                    sourceListViewAdapter.filter.filter(s)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
        })

        destinationLocation.addTextChangeListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                activity.runOnUiThread {
                    if (!destinationListView.isVisible) {
                        destinationListView.visibility = View.VISIBLE
                        sourceListView.visibility = View.GONE
                    }
                    destinationListViewAdapter.filter.filter(s)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
        })

        return rootView
    }

    override fun onButtonClicked(buttonCode: Int) {
        Toast.makeText(getActivity(), "presed : $buttonCode", Toast.LENGTH_LONG).show()
        when (buttonCode) {
            MaterialSearchBar.BUTTON_BACK -> {
                Log.e("MohimBack","Pressed")
/*                searchSource.disableSearch()
                searchDestination.disableSearch()*/
            }
        }
    }

    override fun onSearchStateChanged(enabled: Boolean) {
    }

    override fun onSearchConfirmed(text: CharSequence?) {
    }
}
