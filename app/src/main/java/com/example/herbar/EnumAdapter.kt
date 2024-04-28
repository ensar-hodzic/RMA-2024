package com.example.herbar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.herbar.MedicinskaKorist

class EnumAdapter<T>(
    context: Context,
    private val resource: Int,
    private val enumList: List<T>,
    private val selectedList: MutableList<T>,
    private val getEnumDescription: (T)-> String
) : ArrayAdapter<T>(context, resource, enumList) {

    private var multiple: Boolean = true

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val enumItem:T = enumList[position]
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
        checkBox.text = getEnumDescription(enumItem)
        checkBox.isChecked = selectedList.contains(enumItem)

        if (multiple) {
            checkBox.isChecked = selectedList.contains(enumItem)
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (!selectedList.contains(enumItem)) {
                        selectedList.add(enumItem)
                    }
                } else {
                    selectedList.remove(enumItem)
                }
            }
        } else {
            checkBox.isChecked = enumItem == selectedList.firstOrNull()
            checkBox.setOnClickListener {
                if (checkBox.isChecked) {
                    selectedList.clear()
                    selectedList.add(enumItem)
                    notifyDataSetChanged()
                }
            }
        }
        return view
    }

    fun setAllowMultipleSelection(bool: Boolean) {
        multiple = bool
    }
}
