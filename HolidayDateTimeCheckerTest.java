import org.junit.jupiter.api.Test;

import java.time.DateTimeException;

import static org.junit.jupiter.api.Assertions.*;

class HolidayDateTimeCheckerTest {
    private static final HolidayDateTimeChecker checker = new HolidayDateTimeChecker();

    @Test
    public void shouldThrowDateTimeExceptionNotWorkDay() {
        assertThrows(DateTimeException.class, () -> checker.notWorkDay(null));
        assertThrows(DateTimeException.class, () -> checker.notWorkDay(""));
        assertThrows(DateTimeException.class, () -> checker.notWorkDay("some text"));
        assertThrows(DateTimeException.class, () -> checker.notWorkDay("2024-5-01"));
        assertThrows(DateTimeException.class, () -> checker.notWorkDay("24-5-01"));
        assertThrows(DateTimeException.class, () -> checker.notWorkDay("2024-05-1"));
        assertThrows(DateTimeException.class, () -> checker.notWorkDay("2024 05 01"));
        assertThrows(DateTimeException.class, () -> checker.notWorkDay("01-05-2024"));
        assertThrows(DateTimeException.class, () -> checker.notWorkDay("2024-05-08T12:07:40"));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionNotWorkDay() {
        assertThrows(IllegalArgumentException.class, () -> checker.notWorkDay("2024-04-01"));
        assertThrows(IllegalArgumentException.class, () -> checker.notWorkDay("2023-05-01"));
        assertThrows(IllegalArgumentException.class, () -> checker.notWorkDay("2023-04-01"));
    }

    @Test
    public void shouldBeTrueNotWorkDay() {
        assertTrue(checker.notWorkDay("2024-05-01"));
        assertTrue(checker.notWorkDay("2024-05-09"));
        assertTrue(checker.notWorkDay("2024-05-10"));
        assertTrue(checker.notWorkDay("2024-05-11"));
        assertTrue(checker.notWorkDay("2024-05-12"));
    }

    @Test
    public void shouldBeFalseNotWorkDay() {
        assertFalse(checker.notWorkDay("2024-05-02"));
        assertFalse(checker.notWorkDay("2024-05-03"));
        assertFalse(checker.notWorkDay("2024-05-08"));
        assertFalse(checker.notWorkDay("2024-05-13"));
    }

    @Test
    public void shouldThrowDateTimeExceptionNotWorkDayTime() {
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime(null));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime(""));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("some text"));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("2024-05-08T12:07:40"));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("2024-05-08T12:07:40+3"));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("2024-05-08T12:07:40+0"));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("2024-05-08T12:7:55+03:00"));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("2024-05-08T12:07:5+03:00"));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("2024-05-08T30:17:55+03:00"));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("2024-05-08T12:90:55+03:00"));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("2024-05-08T12:17:90+03:00"));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("2024-05-08T12:17:55+18:01"));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("2024-05-08T12:17:55-18:01"));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("2024-05-08T12:17:55+20:00"));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("2024-05-08T12:17:55-20:00"));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("2024-05-08T12:17:55-20:00"));
        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("2024-05-08T12:17:55.5023174000+03:00"));

//        assertThrows(DateTimeException.class, () -> checker.notWorkZonedDateTime("2024-05-08T12:17:55+00:00[Asia/Calcutta]"));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionNotWorkDayTime() {
        assertThrows(IllegalArgumentException.class, () -> checker.notWorkZonedDateTime("2024-04-08T12:17:55+03:00"));
        assertThrows(IllegalArgumentException.class, () -> checker.notWorkZonedDateTime("2023-05-08T12:17:55+03:00"));
        assertThrows(IllegalArgumentException.class, () -> checker.notWorkZonedDateTime("2023-05-08T12:17:55+03:00"));
        assertThrows(IllegalArgumentException.class, () -> checker.notWorkZonedDateTime("2024-05-31T23:59:59+00:00"));
        assertThrows(IllegalArgumentException.class, () -> checker.notWorkZonedDateTime("2024-05-01T00:00:00+07:00"));
    }

    @Test
    public void shouldBeTrueNotWorkDayTime() {
        assertTrue(checker.notWorkZonedDateTime("2024-05-08T18:00:00+03:00"));
        assertTrue(checker.notWorkZonedDateTime("2024-05-08T08:59:59+03:00"));
        assertTrue(checker.notWorkZonedDateTime("2024-05-08T17:00:00+02:00"));
        assertTrue(checker.notWorkZonedDateTime("2024-05-09T10:00:00+03:00"));
        assertTrue(checker.notWorkZonedDateTime("2024-05-08T09:00:00-18:00"));
        assertTrue(checker.notWorkZonedDateTime("2024-05-13T09:00:00+18:00"));
        assertTrue(checker.notWorkZonedDateTime("2024-05-13T09:00:00+04:00"));
    }

    @Test
    public void shouldBeFalseNotWorkDayTime() {
        assertFalse(checker.notWorkZonedDateTime("2024-05-08T09:00:00+03:00"));
        assertFalse(checker.notWorkZonedDateTime("2024-05-08T17:59:59+03:00"));
        assertFalse(checker.notWorkZonedDateTime("2024-05-09T08:00:00+18:00"));
        assertFalse(checker.notWorkZonedDateTime("2024-05-09T08:00:00+18:00"));
        assertFalse(checker.notWorkZonedDateTime("2024-05-12T18:00:00-18:00"));
    }
}