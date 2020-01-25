package com.example.dhakabusservice.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.dhakabusservice.HomeActivity
import com.example.dhakabusservice.R
import com.example.dhakabusservice.SharedData
import com.orhanobut.hawk.Hawk

class SignUpFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_sign_up, container, false)
        var userInfo: ArrayList<String> = ArrayList()

        val iconMain: ImageView = rootView.findViewById(R.id.icon_main)
        val titleMain: TextView = rootView.findViewById(R.id.title_main)
        val errorMsg: TextView = rootView.findViewById(R.id.errorMsg)
        val userName: EditText = rootView.findViewById(R.id.userName)
        val userEmail: EditText = rootView.findViewById(R.id.userEmail)
        val userPassword: EditText = rootView.findViewById(R.id.userPassword)
        val userSecurity: EditText = rootView.findViewById(R.id.userSecurity)
        val btnSignUp: Button = rootView.findViewById(R.id.btnSignUp)

        errorMsg.visibility = View.INVISIBLE

        btnSignUp.setOnClickListener {
            if(userEmail.text.toString().isEmpty() || userPassword.text.toString().isEmpty() || userName.text.toString().isEmpty()) {
                errorMsg.visibility = View.VISIBLE
            } else {
                userInfo.add(userName.text.toString())
                userInfo.add(userEmail.text.toString())
                userInfo.add(userPassword.text.toString())
                userInfo.add(userSecurity.text.toString())
                Hawk.put(userEmail.text.toString(),userInfo)

                SharedData.userInfo = userInfo

                errorMsg.visibility = View.INVISIBLE
                Toast.makeText(activity, "Successful", Toast.LENGTH_SHORT).show()

                Handler().postDelayed({
                    val homeActivity = Intent(activity, HomeActivity::class.java)
                    startActivity(homeActivity)
                }, 500)
            }
        }
        return rootView
    }
}
