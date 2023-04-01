package com.andmonosu.erunning.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.andmonosu.erunning.R
import com.andmonosu.erunning.SessionObjectiveState
import com.andmonosu.erunning.databinding.FragmentSessionsBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class SessionsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentSessionsBinding
    private lateinit var monthTv:TextView
    private val currentMonth = YearMonth.now()
    private val monthCalendarView: CalendarView get() = binding.calendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sessions, container, false)
        binding = FragmentSessionsBinding.bind(view)
        initComponents(view)
        initCalendar(view)
        return view
    }

    private fun initComponents(view: View?) {
        if (view != null) {
            monthTv = view.findViewById(R.id.monthTv)
        }
    }

    private fun initCalendar(view:View) {

        monthCalendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                val day = data
                val textView = container.textView
                textView.text = day.date.dayOfMonth.toString()
                if (day.position == DayPosition.MonthDate) {
                    val state = SessionObjectiveState.valueOf("SUCCESS")

                    when (state) {
                        SessionObjectiveState.SUCCESS -> {
                            textView.setTextColor(resources.getColor(R.color.white))
                            textView.setBackgroundResource(R.drawable.session_succed_background)
                        }
                        SessionObjectiveState.NOT_SUCCESS -> {
                            textView.setTextColor(resources.getColor(R.color.white))
                            textView.setBackgroundResource(R.drawable.session_not_succed_background)
                        }
                        SessionObjectiveState.NOT_TRAINED -> {
                            textView.setTextColor(resources.getColor(R.color.white))
                            textView.setBackgroundResource(R.drawable.session_not_trained_background)
                        }
                    }
                } else {
                    textView.visibility = View.INVISIBLE
                }
            }
        }
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY)
        initCalendarTitles(view,daysOfWeek)
        initMonth(currentMonth)
        monthCalendarView.setup(startMonth, endMonth, daysOfWeek.first())
        monthCalendarView.scrollToMonth(currentMonth)
        monthCalendarView.monthScrollListener = { updateMonth()}
    }

    private fun updateMonth() {
        val month = monthCalendarView.findFirstVisibleMonth()?.yearMonth
        if (month != null) {
            monthTv.text = month.month.getDisplayName(TextStyle.FULL,Locale.ENGLISH)
        }
    }

    private fun initMonth(currentMonth: YearMonth) {
        monthTv.text = currentMonth.month.getDisplayName(TextStyle.FULL,Locale.ENGLISH)
    }

    private fun initCalendarTitles(view: View, daysOfWeek: List<DayOfWeek>) {
        class MonthViewContainer(view: View) : ViewContainer(view) {
            val titlesContainer = view as ViewGroup
        }



        monthCalendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                if (container.titlesContainer.tag == null) {
                    container.titlesContainer.tag = data.yearMonth

                    container.titlesContainer.children.map { it as TextView }
                        .forEachIndexed { index, textView ->
                            val dayOfWeek = daysOfWeek[index]
                            val title = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                            textView.text = title
                        }
                }
            }
        }
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SessionsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}