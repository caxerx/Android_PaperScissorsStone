package com.caxerx.android.paperscissorsstone

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragement_profile.*
import kotlinx.android.synthetic.main.fragment_game_log.*
import java.util.*
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.content.Intent


class ProfileFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        loadData(this.context!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loadData(this.context!!)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragement_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadData(view.context)

        etBirth.setOnFocusChangeListener { view, focus ->
            if (focus) {
                val c = Calendar.getInstance()
                var mYear = c.get(Calendar.YEAR)
                var mMonth = c.get(Calendar.MONTH)
                var mDay = c.get(Calendar.DAY_OF_MONTH)

                DatePickerDialog(this@ProfileFragment.context, DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    etBirth.setText("$year/${month + 1}/$day")
                }, mYear, mMonth, mDay).show()
            }
        }

        btnCancel.setOnClickListener {
            loadData(view.context)
        }
        btnSave.setOnClickListener {
            var pref = view.context.getSharedPreferences("gameProfile", 0)
            var editor = pref.edit()
            editor.putString("name", etName.text.toString())
            editor.putString("birth", etBirth.text.toString())
            editor.putString("mail", etMail.text.toString())
            editor.putString("phone", etPhone.text.toString())
            editor.apply()
            loadData(view.context)
            AlertDialog.Builder(view.context).setMessage("Profile Saved.").setPositiveButton("OK", null).create().show()
        }
    }

    fun loadData(context: Context) {
        var pref = context.getSharedPreferences("gameProfile", 0)
        etName.setText(pref.getString("name", ""))
        etBirth.setText(pref.getString("birth", ""))
        etMail.setText(pref.getString("mail", ""))
        etPhone.setText(pref.getString("phone", ""))
    }
}
