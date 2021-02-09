package com.example.bookhub.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookhub.R
import com.example.bookhub.adapter.DashboradRecycler
import com.example.bookhub.model.Book
import com.example.bookhub.util.ConnectionManger


class DashBoradFragment : Fragment() {

    lateinit var recyclerDash: RecyclerView
    lateinit var layoutManger: RecyclerView.LayoutManager

    lateinit var btnCheckInternet: Button

    lateinit var recycleAdaptor: DashboradRecycler


    val bookInfoList = arrayListOf<Book>(


    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dash_borad, container, false)

        recyclerDash = view.findViewById(R.id.recycleDash)

        btnCheckInternet = view.findViewById(R.id.btnCheckInternet)
        btnCheckInternet.setOnClickListener {
            if (ConnectionManger().checkConncetivity(activity as Context)) {
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("Internet Connection Found")
                dialog.setPositiveButton("Ok") { text, listener -> }
                dialog.setNegativeButton("Cancel") { text, listener -> }
                dialog.create()
                dialog.show()
            } else {
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Connection is not Found")
                dialog.setPositiveButton("Ok") { text, listener -> }
                dialog.setNegativeButton("Cancel") { text, listener -> }
                dialog.create()
                dialog.show()

            }

        }

        layoutManger = LinearLayoutManager(activity)


        val queue = Volley.newRequestQueue(activity as Context)

        val url = "http://13.235.250.119/v1/book/fetch_books/"

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                val success = it.getBoolean("success")
                if (success) {
                    val data = it.getJSONArray("data")
                    for (i in 0 until data.length()) {
                        val bookJsonObject = data.getJSONObject(i)
                        val bookObject = Book(
                            bookJsonObject.getString("book_id"),
                            bookJsonObject.getString("name"),
                            bookJsonObject.getString("author"),
                            bookJsonObject.getString("rating"),
                            bookJsonObject.getString("price"),
                            bookJsonObject.getString("image")
                        )
                        bookInfoList.add(bookObject)
                        recycleAdaptor = DashboradRecycler(activity as Context, bookInfoList)

                        recyclerDash.adapter = recycleAdaptor

                        recyclerDash.layoutManager = layoutManger

                        recyclerDash.addItemDecoration(
                            DividerItemDecoration(
                                recyclerDash.context, (layoutManger as LinearLayoutManager).orientation
                            )
                        )

                    }
                }
                else
                {
                    Toast.makeText(activity as Context , "Some Error Occurred!!", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                println("error is $it")
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"
                headers["token"] = "9bf534118365f1"
                return headers
            }
        }

        queue.add(jsonObjectRequest)

        return view
    }


}