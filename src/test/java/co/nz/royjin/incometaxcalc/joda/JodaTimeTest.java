package co.nz.royjin.incometaxcalc.joda;

import static org.junit.Assert.*;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

public class JodaTimeTest {

	private static final String DATE_FORMAT = "dd MMMM";
	
	@Test
	public void validateCalendarMonth() {
		LocalDateTime startDate = LocalDateTime.parse("01 March", DateTimeFormat.forPattern(DATE_FORMAT));
		LocalDateTime endDate = LocalDateTime.parse("31 March", DateTimeFormat.forPattern(DATE_FORMAT));
		assertTrue(startDate.compareTo(endDate.plusDays(1).minusMonths(1)) == 0);
	}

}
