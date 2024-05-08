import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.Month.MAY;

/**
 * Формат даты для первой функции:
 *      YYYY-MM-DD
 *
 * Формат даты для второй функции:
 *      1) YYYY-MM-DDThh:mm:ss+hh:mm (после '+' время не более 18:00)
 *      2) YYYY-MM-DDThh:mm:ss-hh:mm (после '-' время не более 18:00)
 *      3) YYYY-MM-DDThh:mm:ss=hh:mm[ZoneId]
 *      4) YYYY-MM-DDThh:mm:ss-hh:mm[ZoneId]
 *      5) Допустима несогласованность названия часового пояса и временного сдвига
 *
 * При неправильном формате либо null выбрасывается DateTimeException
 * При введеных месяце и годе, отличных от мая 2024 (МСК) выбрасывается IllegalArgumentException
 *
 * 18:00:00+03:00 в рабочие дни считается нерабочим
 * 09:00:00+03:00 в рабочие дни считается рабочим
 */
public class HolidayDateTimeChecker {
    private static final Set<LocalDate> holidays = Set.of(
            LocalDate.of(2024, MAY, 1),
            LocalDate.of(2024, MAY, 9),
            LocalDate.of(2024, MAY, 10)
    );
    private static final Set<DayOfWeek> weekends = Set.of(SATURDAY, SUNDAY);
    private static final LocalTime startWorkTime = LocalTime.of(9, 0);
    private static final LocalTime endWorkTime = LocalTime.of(18, 0);
    private static final ZoneId currentZone = ZoneId.of("Europe/Moscow");

    public boolean notWorkDay(String date) {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date);
        } catch (DateTimeException | NullPointerException exception) {
            throw new DateTimeException("Incorrect Format of Date");
        }
        validateDate(localDate);
        return weekends.contains(localDate.getDayOfWeek()) || holidays.contains(localDate);
    }

    public boolean notWorkZonedDateTime(String zonedDateTime) {
        ZonedDateTime zdt;
        try {
            zdt = ZonedDateTime.parse(zonedDateTime);
        } catch (DateTimeException | NullPointerException exception) {
            throw new DateTimeException("Incorrect Format of Date");
        }
        ZonedDateTime zdtMoscow = zdt.withZoneSameInstant(currentZone);
        LocalTime time = zdtMoscow.toLocalTime();
        boolean notWorkDay = notWorkDay(zdtMoscow.toLocalDate().toString());
        boolean notWorkTime = !(
                time.isBefore(endWorkTime)
                && (time.isAfter(startWorkTime) || time.equals(startWorkTime))
        );
        return notWorkDay || notWorkTime;
    }

    private void validateDate(LocalDate localDate) {
        if (!localDate.getMonth().equals(MAY)) {
            throw new IllegalArgumentException("Month not equals May");
        }
        if (localDate.getYear() != 2024) {
            throw new IllegalArgumentException("Year not equals 2024");
        }
    }
}