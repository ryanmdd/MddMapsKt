package io.iotdrive.mddmapskt.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import io.iotdrive.mddmapskt.R
import io.iotdrive.mddmapskt.viewmodels.UserViewModel

class LoginFragment : Fragment(R.layout.fragment_login), MavericksView {
    private val viewModel: UserViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_login).setOnClickListener {
//
            viewModel.signIn(
                view.findViewById<EditText>(R.id.edit_username).text.toString(),
                view.findViewById<EditText>(R.id.edit_password).text.toString()
            )
        }
    }

    override fun invalidate() {
        TODO("Not yet implemented")
    }
}