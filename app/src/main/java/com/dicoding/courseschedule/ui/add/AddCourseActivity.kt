package com.dicoding.courseschedule.ui.add

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import androidx.core.view.get
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.home.HomeActivity
import com.dicoding.courseschedule.ui.home.HomeViewModel
import com.dicoding.courseschedule.ui.home.HomeViewModelFactory
import com.dicoding.courseschedule.util.DayName
import com.dicoding.courseschedule.util.TimePickerFragment

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var courseName: EditText
    private lateinit var day: Spinner
    var startTime: String = "08:00"
    var endTime: String = "09:00"
    private lateinit var lecturer: EditText
    private lateinit var note: EditText
    private lateinit var buttonStart: ImageButton
    private lateinit var buttonEnd: ImageButton
    private lateinit var addCourseViewModel: AddCourseViewModel
    private lateinit var startText: TextView
    private lateinit var endText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        courseName = findViewById(R.id.ed_course_name)
        day = findViewById(R.id.spinner_day)
        lecturer = findViewById(R.id.ed_lecturer)
        note = findViewById(R.id.ed_note)
        startText = findViewById(R.id.tv_start_time)
        endText = findViewById(R.id.tv_end_time)
        buttonStart = findViewById(R.id.ib_start_time)
        buttonEnd = findViewById(R.id.ib_end_time)
        val factory = HomeViewModelFactory.createFactory(this)
        addCourseViewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]


        buttonStart.setOnClickListener {
            val timePickerFragment = TimePickerFragment()
            timePickerFragment.show(supportFragmentManager, "date_start")
        }

        buttonEnd.setOnClickListener {
            val timePickerFragment = TimePickerFragment()
            timePickerFragment.show(supportFragmentManager, "date_end")
        }

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.action_insert -> {
                val dayValue = DayName.getByDay(day.getSelectedItem().toString())


                addCourseViewModel.insertCourse(
                    courseName = courseName.text.toString(),
                    day = dayValue - 1,
                    startTime = startTime,
                    endTime = endTime,
                    lecturer = lecturer.text.toString(),
                    note = note.text.toString()
                )

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            }

            else -> {
                onBackPressed()
            }
        }

        return true
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        var minuteValue:String = minute.toString()
        var hourValue:String = hour.toString()

        if (tag == "date_start") {

            if (minute < 10) {
                minuteValue = "0${minute}"
            }

            if (hour < 10) {
                hourValue = "0${hour}"
            }

            startTime = "${hourValue}:${minuteValue}"
            startText.text = startTime
        }
        if (tag == "date_end") {

            if (minute < 10) {
                minuteValue = "0${minute}"
            }

            if (hour < 10) {
                hourValue = "0${hour}"
            }

            endTime = "${hourValue}:${minuteValue}"
            endText.text = endTime
        }
    }
}

