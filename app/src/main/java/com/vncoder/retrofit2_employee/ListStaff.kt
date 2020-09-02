package com.vncoder.retrofit2_employee

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vncoder.retrofit2_employee.Adapter.ContactAdapter
import com.vncoder.retrofit2_employee.Model.Contact
import com.vncoder.retrofit2_employee.Model.JsonObject
import com.vncoder.retrofit2_employee.Retrofit2.RetrofitClient
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response


class ListStaff : AppCompatActivity() {
    private val ActivityRequestCode2 = 2
    private var list = ArrayList<Contact>()
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var rv_RecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_RecyclerView = findViewById<RecyclerView>(R.id.rv_recyclerView)
        var sw_refresh = findViewById<SwipeRefreshLayout>(R.id.sw_refresh)

        rv_RecyclerView.layoutManager = LinearLayoutManager(this)
        rv_RecyclerView.setHasFixedSize(true)
        rv_RecyclerView.itemAnimator = SlideInLeftAnimator()
        contactAdapter = ContactAdapter(onClickListener, list)
        rv_RecyclerView.adapter = contactAdapter

        sw_refresh.setOnRefreshListener {
            contactAdapter.notifyDataSetChanged()
            sw_refresh.isRefreshing = false
        }

        btn_addStaff.setOnClickListener {
            var intent = Intent(this, AddStaff::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ActivityRequestCode2 && resultCode == Activity.RESULT_OK) {
//            getData()
            Toast.makeText(this, "update success", Toast.LENGTH_LONG).show()
        } else {
//            Toast.makeText(this, "not update", Toast.LENGTH_LONG).show()
        }
    }

    fun getData() {
        RetrofitClient.instance.getdata().enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                shimmer.startShimmer()
                if (response!!.isSuccessful) {
                    contactAdapter.notifyDataSetChanged()
                    list = response.body().contacts as ArrayList<Contact>
                    contactAdapter.exampleList = list
                    rv_RecyclerView.setHasFixedSize(true)

                } else {
                    Toast.makeText(
                        applicationContext,
                        "not data " + response.code().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                shimmer.stopShimmer()
                shimmer.visibility = View.GONE
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                Toast.makeText(this@ListStaff, "onFailure", Toast.LENGTH_LONG).show()
            }
        })
        contactAdapter.notifyDataSetChanged()
    }

    private var onClickListener = object : ContactAdapter.OnItemClickListener {
        override fun onClickEmployee(contact: Contact) {

            val replyintent = Intent(this@ListStaff, DetailStaff::class.java)
            val bundle = Bundle()
            bundle.putSerializable("detailEmployee", contact)
            replyintent.putExtras(bundle)
            startActivityForResult(replyintent, ActivityRequestCode2)
        }

        override fun onLongClickEmployee(contact: Contact) {
            val builder = androidx.appcompat.app.AlertDialog.Builder(this@ListStaff)
            builder.setTitle("notification")
            builder.setMessage("Do you want delete item ?")
            builder.setCancelable(false)
            builder.setPositiveButton("Yes")
            { dialogInterface, i ->

                RetrofitClient.instance.deleteContact(contact.contact_id.toString()).enqueue(object :
                    retrofit2.Callback<Contact> {
                    override fun onResponse(call: Call<Contact>?, response: Response<Contact>?) {
                        if (response!!.isSuccessful) {
                            Log.d("error1", response.code().toString())
//                            contactAdapter.notifyDataSetChanged()
//                            rv_RecyclerView.itemAnimator = SlideInLeftAnimator()
                            getData()
                        } else {
                            Log.d("error2", response.code().toString())
                        }
                    }
                    override fun onFailure(call: Call<Contact>?, t: Throwable?) {
                        Toast.makeText(this@ListStaff, "delete Fail", Toast.LENGTH_LONG).show()
                    }
                })
            }
            builder.setNegativeButton("No")
            { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        var menuItem: MenuItem? = menu?.findItem(R.id.action_search)

        var searchView: SearchView = menuItem?.actionView as SearchView
        searchView.queryHint = "input text"
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                var listResult = if (query!!.isEmpty()) {
                    list
                } else {
                    contactAdapter.exampleList.filter {
                        it.Name?.toLowerCase()!!.contains(query.toString())
                    }
                }
                contactAdapter.exampleList = listResult as ArrayList<Contact>
                contactAdapter.notifyDataSetChanged()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                var listResult = if (newText!!.isEmpty()) {
                    list
                } else {
                    contactAdapter.exampleList.filter {
                        it.FirstName?.toLowerCase()!!.contains(
                            newText.toString()
                        )
                    }
                }
                contactAdapter.exampleList = listResult as ArrayList<Contact>
                contactAdapter.notifyDataSetChanged()

                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return when (id) {
            R.id.action_sort -> {
                list.sortBy { it.Email }
                contactAdapter.notifyDataSetChanged()
                 true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onResume() {
        shimmer.startShimmer()
        getData()
        shimmer.stopShimmer()
        super.onResume()
    }
}