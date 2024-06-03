package utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CommUtil {
    companion object{
        fun strToLocalDate(date: String): LocalDate{
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }

        fun localDateToStr(localDate: LocalDate): String{
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return formatter.format(localDate)
        }
    }
}