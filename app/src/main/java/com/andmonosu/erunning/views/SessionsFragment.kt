package com.andmonosu.erunning.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import com.andmonosu.erunning.R
import com.andmonosu.erunning.SessionObjectiveState
import com.andmonosu.erunning.databinding.FragmentSessionsBinding
import com.google.firebase.firestore.FirebaseFirestore
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
    private var email: String? = null

    private lateinit var binding: FragmentSessionsBinding
    private lateinit var monthTv:TextView
    private lateinit var pruebaTv:TextView
    private val currentMonth = YearMonth.now()
    private val monthCalendarView: CalendarView get() = binding.calendarView
    private var db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString("EMAIL_BUNDLE")
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
            pruebaTv = view.findViewById(R.id.pruebaTv)
        }
    }

    private fun initCalendar(view:View) {

        monthCalendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {

            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.calendarView = monthCalendarView
                container.day = data
                val textView = container.textView
                val selectedDate = container.selectedDate

                textView.text = data.date.dayOfMonth.toString()
                if (data.position == DayPosition.MonthDate) {
                    textView.visibility = View.VISIBLE
                        db.collection("users").document(email ?: "").collection("sessions")
                            .document(data.date.toString()).get().addOnSuccessListener { result ->
                            val state = (result.get("state") as String?)?.let {
                                SessionObjectiveState.valueOf(
                                    it
                                )
                            }
                                if (data.date == selectedDate&&state!=null) {
                                    textView.setTextColor(resources.getColor(R.color.white))
                                    textView.setBackgroundResource(R.drawable.session_selected_background)
                                    pruebaTv.text = (result.get("hola") as String?)
                                } else {
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
                                        else -> {}
                                    }
                                }
                            }
                }else {
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

        const val EMAIL_BUNDLE = ""

        @JvmStatic
        fun newInstance(email: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL_BUNDLE, email)
                }
            }
    }
}