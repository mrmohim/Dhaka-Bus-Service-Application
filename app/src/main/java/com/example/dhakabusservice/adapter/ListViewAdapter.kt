package com.example.dhakabusservice.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.dhakabusservice.R
import kotlinx.android.synthetic.main.fragment_home.*


class ListViewAdapter(
    private val activity: Activity,
    var dataList: ArrayList<String>,
    private val callMsg: String
) : BaseAdapter(), Filterable {
    var mDataList: ArrayList<String> = dataList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(activity, R.layout.layout_bus_list, null)

        val busName: TextView = view.findViewById(R.id.busName)
        busName.text = mDataList[position]

        busName.setOnClickListener {
            when (callMsg) {
                "AllBus" -> {
                    Toast.makeText(activity, "AllBus: ${mDataList[position]}", Toast.LENGTH_LONG)
                        .show()
                }
                "sourceListView" -> {
                    activity.searchSource.text = mDataList[position]
                    activity.sourceListView.visibility = View.GONE
                }
                else -> {
                    activity.searchDestination.text = mDataList[position]
                    activity.destinationListView.visibility = View.GONE
                }
            }
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return mDataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mDataList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                val filteredList: ArrayList<String> = ArrayList()
                mDataList = if (charString.isEmpty()) {
                    if(callMsg == "AllBus")
                        dataList
                    else
                        filteredList
                } else {
                    dataList.forEach {
                        if (it.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(it)
                        }
                    }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = mDataList
                filterResults.count = mDataList.size
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                mDataList = filterResults.values as ArrayList<String>

                activity.runOnUiThread {
                    notifyDataSetChanged()
                }
            }
        }
    }
}