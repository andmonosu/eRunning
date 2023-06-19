package com.andmonosu.erunning.views

import android.view.View
import com.andmonosu.erunning.databinding.CalendarDayLayoutBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.ViewContainer
import java.time.LocalDate

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
    var selectedDate: LocalDate? = null
    lateinit var day: CalendarDay
    lateinit var calendarView:CalendarView
    init {
        textView.setOnClickListener {
            if (day.position == DayPosition.MonthDate) {
                if (selectedDate == day.date) {
                    selectedDate = null
                    calendarView.notifyDateChanged(day.date)
                } else {
                    val oldDate = selectedDate
                    selectedDate = day.date
                    calendarView.notifyDateChanged(day.date)
                    oldDate?.let { calendarView.notifyDateChanged(oldDate) }
                }
            }
        }
    }
}