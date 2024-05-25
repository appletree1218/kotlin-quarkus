package utils

import com.google.protobuf.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class CommUtil {
    companion object{
        fun timestampToLocalDate(timestamp: Timestamp): LocalDate{
            val instant = Instant.ofEpochSecond(timestamp.seconds, timestamp.nanos.toLong())
            return instant.atZone(ZoneId.systemDefault()).toLocalDate()
        }

        fun localDateToTimestamp(localDate: LocalDate): Timestamp{
            val instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
            return Timestamp.newBuilder().setSeconds(instant.epochSecond).setNanos(instant.nano).build()
        }
    }
}