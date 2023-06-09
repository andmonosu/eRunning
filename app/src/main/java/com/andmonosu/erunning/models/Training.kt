package com.andmonosu.erunning.models

import java.time.DayOfWeek

data class Training(var trainingWeeks:List<TrainingWeek> = listOf()) {
    private var plan5km8weeks:List<TrainingWeek> = listOf(
        TrainingWeek(
            listOf(
                TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 2.5),
                TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 2.5),
                TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
            )
        ),
        TrainingWeek(
            listOf(
                TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 3.0),
                TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 3.0),
                TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
            )
        ),
        TrainingWeek(
            listOf(
                TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 3.5),
                TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 3.5),
                TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
            )
        ),
        TrainingWeek(
            listOf(
                TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 3.5),
                TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 3.5),
                TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
            )
        ),
        TrainingWeek(
            listOf(
                TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 4.0),
                TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 4.0),
                TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
            )
        ),
        TrainingWeek(
            listOf(
                TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 4.5),
                TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 4.5),
                TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
            )
        ),
        TrainingWeek(
            listOf(
                TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 5.0),
                TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 5.0),
                TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
            )
        ),
        TrainingWeek(
            listOf(
                TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 5.0),
                TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 5.0),
                TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
            )
        )
    )

    fun setPlan5Km8Weeks(){
        trainingWeeks = plan5km8weeks
    }
}