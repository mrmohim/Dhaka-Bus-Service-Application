package com.example.dhakabusservice.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.dhakabusservice.HomeActivity
import com.example.dhakabusservice.R
import com.example.dhakabusservice.SharedData
import com.orhanobut.hawk.Hawk


class SignInFragment : Fragment() {
    private var userInfo: ArrayList<String> = ArrayList()
    private var isForgotPass = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_sign_in, container, false)
        val errorMsg: TextView = rootView.findViewById(R.id.errorMsg)
        val userEmailHeader: TextView = rootView.findViewById(R.id.userEmailHeader)
        val userPasswordHeader: TextView = rootView.findViewById(R.id.userPasswordHeader)
        val userEmail: EditText = rootView.findViewById(R.id.userEmail)
        val userPassword: EditText = rootView.findViewById(R.id.userPassword)
        val btnSignIn: Button = rootView.findViewById(R.id.btnSignIn)
        val btnCancel: Button = rootView.findViewById(R.id.btnCancel)
        val sampleText: TextView = rootView.findViewById(R.id.forgotPass)
        val rememberCheckBox: CheckBox = rootView.findViewById(R.id.rememberCheckBox)
        val rememberText: TextView = rootView.findViewById(R.id.rememberText)
        val progressBarLayout: ConstraintLayout = rootView.findViewById(R.id.progressBarLayout)

        errorMsg.visibility = View.INVISIBLE
        btnCancel.visibility = View.GONE

        btnSignIn.setOnClickListener {
            if (isForgotPass == 0) {
                if (userEmail.text.toString().isEmpty() || userPassword.text.toString().isEmpty() || Hawk.count() == 1L) {
                    errorMsg.visibility = View.VISIBLE
                } else if (!Hawk.contains(userEmail.text.toString())) {
                    errorMsg.visibility = View.VISIBLE
                } else {
                    userInfo = Hawk.get(userEmail.text.toString())

                    if (userInfo[2] == userPassword.text.toString()) {

                        SharedData.userInfo = userInfo

                        errorMsg.visibility = View.INVISIBLE
                        Toast.makeText(activity, "Successful", Toast.LENGTH_SHORT).show()

                        progressBarLayout.visibility = View.VISIBLE
                        btnCancel.visibility = View.GONE
                        btnSignIn.visibility = View.GONE
                        Handler().postDelayed({
                            val homeActivity = Intent(activity, HomeActivity::class.java)
                            startActivity(homeActivity)
                            activity!!.finish()
                        }, 300)
                    } else {
                        errorMsg.visibility = View.VISIBLE
                    }
                }
            } else if (isForgotPass == 1) {
                if (userEmail.text.toString().isEmpty() || userPassword.text.toString().isEmpty() || Hawk.count() == 1L) {
                    errorMsg.visibility = View.VISIBLE
                    errorMsg.text = "*Please enter valid email or answer."
                } else if (!Hawk.contains(userEmail.text.toString())) {
                    errorMsg.visibility = View.VISIBLE
                    errorMsg.text = "*Please enter valid email or answer."
                } else {
                    userInfo = Hawk.get(userEmail.text.toString())

                    if (userInfo[3] == userPassword.text.toString()) {
                        SharedData.userInfo = userInfo

                        setForgotPassSecondView(
                            errorMsg,
                            btnCancel,
                            userEmail,
                            userPassword,
                            btnSignIn,
                            userEmailHeader,
                            userPasswordHeader
                        )

                        progressBarLayout.visibility = View.VISIBLE
                        btnCancel.visibility = View.GONE
                        btnSignIn.visibility = View.GONE
                        Handler().postDelayed({
                            progressBarLayout.visibility = View.GONE
                            btnCancel.visibility = View.VISIBLE
                            btnSignIn.visibility = View.VISIBLE
                        }, 300)
                    } else {
                        errorMsg.visibility = View.VISIBLE
                        errorMsg.text = "*Please enter valid email or answer."
                    }
                }
            } else {
                if (userEmail.text.toString().isEmpty() || userPassword.text.toString().isEmpty() || Hawk.count() == 1L) {
                    errorMsg.visibility = View.VISIBLE
                    errorMsg.text = "*Password mismatch."
                } else if (userEmail.text.toString() != userPassword.text.toString()) {
                    errorMsg.visibility = View.VISIBLE
                    errorMsg.text = "*Password mismatch."
                } else {
                    SharedData.userInfo[2] = userPassword.text.toString()
                    Hawk.put(SharedData.userInfo[1], SharedData.userInfo)

                    progressBarLayout.visibility = View.VISIBLE
                    btnCancel.visibility = View.GONE
                    btnSignIn.visibility = View.GONE
                    Toast.makeText(activity, "Successful", Toast.LENGTH_SHORT).show()
                    Handler().postDelayed({
                        val homeActivity = Intent(activity, HomeActivity::class.java)
                        startActivity(homeActivity)
                        activity!!.finish()
                    }, 300)
                }
            }
        }

        sampleText.setOnClickListener {
            setForgotPassFirstView(
                errorMsg,
                rememberCheckBox,
                rememberText,
                sampleText,
                btnCancel,
                btnSignIn,
                userEmail,
                userPassword,
                userPasswordHeader
            )

            progressBarLayout.visibility = View.VISIBLE
            btnCancel.visibility = View.GONE
            btnSignIn.visibility = View.GONE
            Handler().postDelayed({
                progressBarLayout.visibility = View.GONE
                btnCancel.visibility = View.VISIBLE
                btnSignIn.visibility = View.VISIBLE
            }, 300)
        }

        btnCancel.setOnClickListener {
            setSignInView(
                errorMsg,
                rememberCheckBox,
                rememberText,
                sampleText,
                btnCancel,
                btnSignIn,
                userEmail,
                userPassword,
                userEmailHeader,
                userPasswordHeader
            )

            progressBarLayout.visibility = View.VISIBLE
            btnCancel.visibility = View.GONE
            btnSignIn.visibility = View.GONE
            Handler().postDelayed({
                progressBarLayout.visibility = View.GONE
                btnCancel.visibility = View.VISIBLE
                btnSignIn.visibility = View.VISIBLE
            }, 300)
        }
        return rootView
    }

    private fun setSignInView(
        errorMsg: TextView,
        rememberCheckBox: CheckBox,
        rememberText: TextView,
        sampleText: TextView,
        btnCancel: Button,
        btnSignIn: Button,
        userEmail: EditText,
        userPassword: EditText,
        userEmailHeader: TextView,
        userPasswordHeader: TextView
    ) {
        isForgotPass = 0
        errorMsg.visibility = View.INVISIBLE
        rememberCheckBox.visibility = View.VISIBLE
        rememberText.visibility = View.VISIBLE
        sampleText.visibility = View.VISIBLE
        btnCancel.visibility = View.GONE
        btnSignIn.text = resources.getString(R.string.sign_in)
        userEmail.text.clear()
        userEmail.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        userPassword.text.clear()
        userPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        userPassword.typeface = userEmail.typeface
        userEmailHeader.text = "Email:"
        userEmail.hint = "email@email.com (required)"
        userPasswordHeader.text = "Password:"
        userPassword.hint = "Password (required)"
    }

    private fun setForgotPassFirstView(
        errorMsg: TextView,
        rememberCheckBox: CheckBox,
        rememberText: TextView,
        sampleText: TextView,
        btnCancel: Button,
        btnSignIn: Button,
        userEmail: EditText,
        userPassword: EditText,
        userPasswordHeader: TextView
    ) {
        isForgotPass = 1
        errorMsg.visibility = View.INVISIBLE
        rememberCheckBox.visibility = View.GONE
        rememberText.visibility = View.GONE
        sampleText.visibility = View.GONE
        btnCancel.visibility = View.VISIBLE
        btnSignIn.text = "Continue"
        userEmail.text.clear()
        userEmail.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        userPassword.text.clear()
        userPassword.inputType = InputType.TYPE_CLASS_TEXT
        userPassword.typeface = userEmail.typeface
        userPasswordHeader.text = "What was your childhood nickname?"
        userPassword.hint = "Answer (required)"
    }

    private fun setForgotPassSecondView(
        errorMsg: TextView,
        btnCancel: Button,
        userEmail: EditText,
        userPassword: EditText,
        btnSignIn: Button,
        userEmailHeader: TextView,
        userPasswordHeader: TextView
    ) {
        isForgotPass = 2
        errorMsg.visibility = View.INVISIBLE
        btnCancel.visibility = View.VISIBLE
        userEmail.text.clear()
        userEmail.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        userPassword.text.clear()
        userEmail.typeface = errorMsg.typeface
        userPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        userPassword.typeface = errorMsg.typeface
        btnSignIn.text = "Confirm"
        userEmailHeader.text = "New Password:"
        userEmail.hint = "Password (required)"
        userPasswordHeader.text = "Confirm Password:"
        userPassword.hint = "Password (required)"
    }
}
