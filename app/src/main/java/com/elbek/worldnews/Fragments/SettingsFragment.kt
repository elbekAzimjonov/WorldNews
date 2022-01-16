package com.elbek.worldnews.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.elbek.worldnews.R

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val switch = view?.findViewById<Switch>(R.id.switchDark)
        val sharedPreferences = this.requireActivity().getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
        val saveBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY",false)
        switch!!.isChecked = saveBoolean
        switch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveData(isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                saveData(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        loadData()
        return view
    }

    private fun loadData() {
        val sharedPreferences = this.requireActivity().getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
        val saveBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY",false)
        Log.v("TestShared","$saveBoolean")
    }

    private fun saveData(check:Boolean) {
        val sharedPreferences =
            this.requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.apply {
            putBoolean("BOOLEAN_KEY",check)
        }.apply()
    }
}