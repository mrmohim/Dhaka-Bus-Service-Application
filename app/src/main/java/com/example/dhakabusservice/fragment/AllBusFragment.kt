package com.example.dhakabusservice.fragment

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.dhakabusservice.R
import com.example.dhakabusservice.adapter.ListViewAdapter
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.fragment_all_bus.*


class AllBusFragment(private val activity: Activity, private val busList: ArrayList<String>) : Fragment(), MaterialSearchBar.OnSearchActionListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_all_bus, container, false)

        val listView: ListView = rootView.findViewById(R.id.mListView)
        val listViewAdapter = ListViewAdapter(activity, busList, "AllBus")
        listView.adapter = listViewAdapter

        val searchBar: MaterialSearchBar = rootView.findViewById(R.id.searchBar)
        searchBar.setOnSearchActionListener(this)

        searchBar.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listViewAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        return rootView
    }

    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode) {
            MaterialSearchBar.BUTTON_BACK -> {
                searchBar.disableSearch()
            }
        }
    }

    override fun onSearchStateChanged(enabled: Boolean) {
    }

    override fun onSearchConfirmed(text: CharSequence?) {
    }
}
