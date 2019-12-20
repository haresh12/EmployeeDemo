package com.broadcast.employeedemo.activity.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.broadcast.employeedemo.activity.ui.adapter.EmployeeListAdapter
import com.broadcast.employeedemo.activity.ui.model.EmployeeData
import com.broadcast.employeedemo.activity.ui.util.DataState
import com.broadcast.employeedemo.activity.ui.viewmodel.EmployeeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper


class EmployeeListActivity : AppCompatActivity(), EmployeeListAdapter.Interaction {

    private var mainRecyclerAdapter: EmployeeListAdapter? = null
    private lateinit var viewModel: EmployeeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.broadcast.employeedemo.R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        subScribeObserver()
        initRecyclerView()
        deleteEmployee()

        button_add_emp.setOnClickListener {
            startActivity(Intent(this, AddEditEmployeeActivity::class.java))
        }

    }

    private fun subScribeObserver() {
        viewModel.getEmployeeList().observe(this,
            Observer<DataState<List<EmployeeData>>> { dataState ->
                if (dataState.loading) {
                    progress_bar.visibility = View.VISIBLE
                } else if (dataState.data != null) {
                    progress_bar.visibility = View.GONE
                    mainRecyclerAdapter?.submitList(dataState.data!!)
                } else if (dataState.message != null) {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(this, dataState.message.toString(), Toast.LENGTH_LONG).show()
                }
            })

    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@EmployeeListActivity)
            mainRecyclerAdapter = EmployeeListAdapter(this@EmployeeListActivity)
            adapter = mainRecyclerAdapter
        }
    }

    private fun deleteEmployee() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                subScribeDeleteObserver(mainRecyclerAdapter!!.getEmpId(viewHolder.adapterPosition))
                mainRecyclerAdapter!!.notifyItemRemoved(viewHolder.adapterPosition)

            }
        }).attachToRecyclerView(recycler_view)
    }


    private fun subScribeDeleteObserver(empId: Int) {
        viewModel.deleteEmployee(empId).observe(this, Observer { dataState ->
            if (dataState.loading) {
                progress_bar.visibility = View.VISIBLE
            } else if (dataState.data != null) {
                progress_bar.visibility = View.GONE
                Toast.makeText(this, "Deleted successfully", Toast.LENGTH_LONG).show()
            } else if (dataState.message != null) {
                progress_bar.visibility = View.GONE
                Toast.makeText(this, dataState.message.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }


    override fun onItemSelected(position: Int, item: EmployeeData) {
        startActivity(
            Intent(this, AddEditEmployeeActivity::class.java)
                .putExtra("salary", item.salary)
                .putExtra("age", item.age.toString())
                .putExtra("name", item.name)

        )

    }
}
