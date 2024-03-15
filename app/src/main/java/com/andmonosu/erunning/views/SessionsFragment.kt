package com.andmonosu.erunning.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.andmonosu.erunning.R
import com.andmonosu.erunning.SessionObjectiveState
import com.andmonosu.erunning.databinding.FragmentSessionsBinding
import com.andmonosu.erunning.data.model.SessionType
import com.google.firebase.firestore.DocumentSnapshot
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
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class SessionsFragment : Fragment() {
    private var email: String? = null

    private lateinit var binding: FragmentSessionsBinding
    private lateinit var monthTv:TextView
    private lateinit var tvTypeSessionTitle:TextView
    private lateinit var tvTypeSession:TextView
    private lateinit var tvDistanceSessionTitle:TextView
    private lateinit var tvDistanceSession:TextView
    private lateinit var tvPaceSessionTitle:TextView
    private lateinit var tvPaceSession:TextView
    private lateinit var tvTimeSessionTitle:TextView
    private lateinit var tvTimeSession:TextView

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
            tvTypeSessionTitle = view.findViewById(R.id.tvTypeSessionTitle)
            tvTypeSession = view.findViewById(R.id.tvTypeSession)
            tvDistanceSessionTitle = view.findViewById(R.id.tvDistanceSessionTitle)
            tvDistanceSession = view.findViewById(R.id.tvDistanceSession)
            tvPaceSessionTitle = view.findViewById(R.id.tvPaceSessionTitle)
            tvPaceSession = view.findViewById(R.id.tvPaceSession)
            tvTimeSessionTitle = view.findViewById(R.id.tvTimeSessionTitle)
            tvTimeSession = view.findViewById(R.id.tvTimeSession)
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
                        if(result.exists()){
                            val state = (result.get("state") as String?)?.let {
                                SessionObjectiveState.valueOf(
                                    it
                                )
                            }
                            val type = (result.get("type") as String?)?.let {
                                SessionType.valueOf(
                                    it
                                )
                            }
                            if (data.date == selectedDate&&state!=null) {
                                textView.setTextColor(resources.getColor(R.color.white))
                                textView.setBackgroundResource(R.drawable.session_selected_background)
                                if (type != null) {
                                    tvPaceSessionTitle.text = getString(R.string.pace_session)
                                    tvDistanceSessionTitle.text = getString(R.string.distance_session)
                                    tvTimeSessionTitle.text = getString(R.string.time_session)
                                    fillTextViews(type,result)
                                }
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
                                    SessionObjectiveState.REST -> {
                                        textView.setTextColor(resources.getColor(R.color.white))
                                        textView.setBackgroundResource(R.drawable.session_rest_background)
                                    }
                                    SessionObjectiveState.PARTIAL_SUCCESS -> {
                                        textView.setTextColor(resources.getColor(R.color.white))
                                        textView.setBackgroundResource(R.drawable.session_partial_success_background)
                                    }
                                    else -> {}
                                }
                            }
                        }else{
                            if(data.date.isBefore(LocalDate.now())||data.date.isEqual(LocalDate.now())){
                                if (data.date == selectedDate) {
                                    textView.setTextColor(resources.getColor(R.color.white))
                                    textView.setBackgroundResource(R.drawable.session_selected_background)
                                    tvTypeSession.text = "No has entrenado"
                                    tvTimeSessionTitle.isVisible = false
                                    tvTimeSession.isVisible = false
                                    tvDistanceSessionTitle.isVisible = false
                                    tvDistanceSession.isVisible = false
                                    tvPaceSession.isVisible = false
                                    tvPaceSessionTitle.isVisible = false
                                    tvTimeSession.text = result.get("time").toString()
                                }else{
                                    textView.setTextColor(resources.getColor(R.color.white))
                                    textView.setBackgroundResource(R.drawable.session_not_trained_background)
                                }
                            }else if(data.date.isAfter(LocalDate.now())){
                                db.collection("users").document(email ?: "").collection("programmed sessions")
                                    .document(data.date.toString()).get().addOnSuccessListener { programmedSession ->
                                        if(programmedSession.exists()){
                                            val type = (programmedSession.get("type") as String?)?.let {
                                                SessionType.valueOf(
                                                    it
                                                )
                                            }
                                            if (data.date == selectedDate) {
                                                textView.setTextColor(resources.getColor(R.color.white))
                                                textView.setBackgroundResource(R.drawable.session_selected_background)
                                                if (type != null) {
                                                    tvPaceSessionTitle.text = getString(R.string.details_pace)
                                                    tvDistanceSessionTitle.text = getString(R.string.details_distance)
                                                    tvTimeSessionTitle.text = getString(R.string.details_time)
                                                    fillTextViews(type,programmedSession)
                                                }
                                            } else {
                                                textView.setTextColor(resources.getColor(R.color.white))
                                                textView.setBackgroundResource(R.drawable.session_not_trained_yet_background)
                                            }
                                        }
                                    }
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

    private fun fillTextViews(type: SessionType, result:DocumentSnapshot){
        when (type) {
            SessionType.REST->{
                tvTypeSession.text = "Descanso"
                tvTimeSessionTitle.isVisible = false
                tvTimeSession.isVisible = false
                tvDistanceSessionTitle.isVisible = false
                tvDistanceSession.isVisible = false
                tvPaceSession.isVisible = false
                tvPaceSessionTitle.isVisible = false
            }
            SessionType.WALK->{
                tvTypeSession.text = "Caminar"
                tvTimeSessionTitle.isVisible = true
                tvTimeSession.isVisible = true
                tvDistanceSessionTitle.isVisible = false
                tvDistanceSession.isVisible = false
                tvPaceSession.isVisible = false
                tvPaceSessionTitle.isVisible = false
                tvTimeSession.text = result.get("time").toString()
            }
            SessionType.PROGRESSIVE->{
                tvTypeSession.text = "Carrera progresiva"
                tvTimeSessionTitle.isVisible = true
                tvTimeSession.isVisible = true
                tvDistanceSessionTitle.isVisible = false
                tvDistanceSession.isVisible = false
                tvPaceSession.isVisible = false
                tvPaceSessionTitle.isVisible = false
                tvTimeSession.text = result.get("time").toString()
            }
            SessionType.TROT->{
                tvTypeSession.text = "Trote"
                tvDistanceSessionTitle.isVisible = true
                tvDistanceSession.isVisible = true
                tvPaceSession.isVisible = false
                tvPaceSessionTitle.isVisible = false
                tvTimeSessionTitle.isVisible = false
                tvTimeSession.isVisible = false
                tvDistanceSession.text = result.get("distance").toString()
            }
            SessionType.CC->{
                tvTypeSession.text = "Carrera continua"
                tvDistanceSessionTitle.isVisible = true
                tvDistanceSession.isVisible = true
                tvDistanceSession.text = result.get("distance").toString()
                tvTimeSessionTitle.isVisible = true
                tvTimeSession.isVisible = true
                tvTimeSession.text = result.get("time").toString()
                tvPaceSession.isVisible = true
                tvPaceSessionTitle.isVisible = true
                tvPaceSession.text = result.get("pace").toString()
            }
            SessionType.REST_OR_TROT_WALK->{
                tvTypeSession.text = "Descanso o trote-caminata"
                tvTimeSessionTitle.isVisible = false
                tvTimeSession.isVisible = false
                tvDistanceSessionTitle.isVisible = false
                tvDistanceSession.isVisible = false
                tvPaceSession.isVisible = false
                tvPaceSessionTitle.isVisible = false
            }
            SessionType.INTERVALS-> {
                tvTypeSession.text = "Carrera por intervalos"
                tvTimeSessionTitle.isVisible = false
                tvTimeSession.isVisible = false
                tvDistanceSessionTitle.isVisible = false
                tvDistanceSession.isVisible = false
                tvPaceSession.isVisible = false
                tvPaceSessionTitle.isVisible = false
            }
        }
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
            SessionsFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL_BUNDLE, email)
                }
            }
    }
}