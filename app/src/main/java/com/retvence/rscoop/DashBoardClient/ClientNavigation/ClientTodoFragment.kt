package com.retvence.rscoop.DashBoardClient.ClientNavigation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.retvence.rscoop.ApiRequests.RetrofitBuilder
import com.retvence.rscoop.DashBoardClient.ClientTaskAdapter
import com.retvence.rscoop.DataCollections.GetTaskData
import com.retvence.rscoop.SharedStorage.SharedPreferenceManagerAdmin
import com.retvens.rscoop.R
import dev.joshhalvorson.calendar_date_range_picker.calendar.CalendarPicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class ClientTodoFragment : Fragment() {


    private lateinit var owner_id : String

    private lateinit var recyclerView: RecyclerView
    private lateinit var clientTaskAdapter: ClientTaskAdapter
    lateinit var shimmerFrameLayout: ShimmerFrameLayout

    private lateinit var calendarPicker: CalendarPicker
    private lateinit var get: CardView
    private lateinit var fd:String
    private lateinit var sd:String
    private lateinit var tool : androidx.appcompat.widget.Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        owner_id = SharedPreferenceManagerAdmin.getInstance(context!!).user.owner_id.toString()

        calendarPicker = view.findViewById(R.id.calendarPicker)

        tool = view.findViewById(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(tool as androidx.appcompat.widget.Toolbar?)

        get = view.findViewById(R.id.get_calendar)
        get.setOnClickListener {

        val selectedDates = calendarPicker.getSelectedDates()

        if (selectedDates != null) {
            fd = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date(selectedDates.first))
            sd = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date(selectedDates.second))
//            val firstDate = DateFormat.getDateInstance().format(Date(selectedDates.first))
//            val secondDate = DateFormat.getDateInstance().format(Date(selectedDates.second))
//            Toast.makeText(context,fd.toString() + " to " + sd.toString(),Toast.LENGTH_SHORT).show()
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

            val startDate = Instant.ofEpochMilli(selectedDates.first).atZone(ZoneId.systemDefault()).toLocalDate()


            val endDate = Instant.ofEpochMilli(selectedDates.second).atZone(ZoneId.systemDefault()).toLocalDate()

            val tasksInRange = mutableListOf<GetTaskData>()

                val getData = RetrofitBuilder.retrofitBuilder.getCTask(owner_id)
                getData.enqueue(object : Callback<List<GetTaskData>?> {
                    override fun onResponse(
                        call: Call<List<GetTaskData>?>,
                        response: Response<List<GetTaskData>?>
                    ) {

                        val responseList = response.body()
                        if (responseList != null && view != null) {
                            for (task in responseList) {
                                val dueDate = dateFormat.parse(task.Date)
                                if (dueDate != null) {
                                    val localDate = LocalDate.parse(task.Date, DateTimeFormatter.ofPattern("dd MMMM yyyy"))
                                    if (localDate >= startDate && localDate <= endDate) {
                                        tasksInRange.add(task)
                                    }
                                }
                            }
                            clientTaskAdapter = ClientTaskAdapter(context!!, tasksInRange)
                            recyclerView.adapter = clientTaskAdapter
                        }

                    }

                    override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {

                    }
                })


            }

        }


        recyclerView = view.findViewById(R.id.recycler_Tasks_todo_client)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        shimmerFrameLayout = view.findViewById(R.id.shimmer_tasks_container_todo_c)

        allTaskData()
    }

    private fun allTaskData() {
        val getData = RetrofitBuilder.retrofitBuilder.getCTask(owner_id)
        getData.enqueue(object : Callback<List<GetTaskData>?> {
            override fun onResponse(
                call: Call<List<GetTaskData>?>,
                response: Response<List<GetTaskData>?>
            ) {
                val response = response.body()!!
                if (view != null){
                    clientTaskAdapter = ClientTaskAdapter(context!!,response)
                    clientTaskAdapter.notifyDataSetChanged()
                    recyclerView.adapter = clientTaskAdapter

                    shimmerFrameLayout.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE

                }

            }

            override fun onFailure(call: Call<List<GetTaskData>?>, t: Throwable) {
                Toast.makeText(context,t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}