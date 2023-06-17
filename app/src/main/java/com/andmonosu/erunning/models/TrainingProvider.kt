package com.andmonosu.erunning.models

import java.time.DayOfWeek

class TrainingProvider {
    companion object{
        val plan5km8weeks:MutableList<TrainingWeek> = mutableListOf(
            TrainingWeek(
                mutableListOf(
                    TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 2.5),
                    TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 2.5),
                    TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                    TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
                ) , "Semana 1"
            ),
            TrainingWeek(
                mutableListOf(
                    TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 3.0),
                    TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 3.0),
                    TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                    TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
                ), "Semana 2"
            ),
            TrainingWeek(
                mutableListOf(
                    TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 3.5),
                    TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 3.5),
                    TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                    TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
                ), "Semana 3"
            ),
            TrainingWeek(
                mutableListOf(
                    TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 3.5),
                    TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 3.5),
                    TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                    TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
                ), "Semana 4"
            ),
            TrainingWeek(
                mutableListOf(
                    TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 4.0),
                    TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 4.0),
                    TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                    TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
                ), "Semana 5"
            ),
            TrainingWeek(
                mutableListOf(
                    TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 4.5),
                    TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 4.5),
                    TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                    TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
                ), "Semana 6"
            ),
            TrainingWeek(
                mutableListOf(
                    TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 5.0),
                    TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 5.0),
                    TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                    TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
                ), "Semana 7"
            ),
            TrainingWeek(
                mutableListOf(
                    TrainingDay(DayOfWeek.MONDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.THURSDAY,SessionType.TROT, distance = 5.0),
                    TrainingDay(DayOfWeek.WEDNESDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.TUESDAY,SessionType.TROT, distance = 5.0),
                    TrainingDay(DayOfWeek.FRIDAY,SessionType.REST),
                    TrainingDay(DayOfWeek.SATURDAY,SessionType.REST_OR_TROT_WALK),
                    TrainingDay(DayOfWeek.SUNDAY,SessionType.WALK, time = 60)
                ), "Semana 8"
            )
        )

    }

}