package com.broadcast.employeedemo.activity.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.broadcast.employeedemo.R
import com.broadcast.employeedemo.activity.ui.model.EmployeeData
import kotlinx.android.synthetic.main.item_emp_list.view.*
import java.util.Arrays.asList
import java.util.*


class EmployeeListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EmployeeData>() {

        override fun areItemsTheSame(oldItem: EmployeeData, newItem: EmployeeData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EmployeeData, newItem: EmployeeData): Boolean {
            return oldItem == newItem

        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return EmployeeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_emp_list,
                parent,
                false
            ),
            interaction
        )
    }



    fun getEmpId(position: Int): Int {
       return differ.currentList[position].id
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EmployeeViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<EmployeeData>) {
        differ.submitList(list)
    }


    class EmployeeViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: EmployeeData) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            txt_emp_name.text = item.name
            txt_emp_age.text = "Age : ${item.age}"
            txt_emp_salary.text = "Salary : ${item.salary}"
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: EmployeeData)
    }
}
