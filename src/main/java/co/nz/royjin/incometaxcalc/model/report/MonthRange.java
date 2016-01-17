package co.nz.royjin.incometaxcalc.model.report;

import org.apache.commons.lang.Validate;
import org.joda.time.LocalDateTime;

public class MonthRange {
	
	private LocalDateTime start;

	private LocalDateTime end;
	
	public MonthRange(LocalDateTime start, LocalDateTime end) {
		Validate.notNull(start);
		Validate.notNull(end);
		
		if (start.compareTo(end) > 0) {
			throw new IllegalArgumentException("start date must be less than end date");
		}
		
		this.start = start;
		this.end = end;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	private boolean isDateWithinTheStartYear(LocalDateTime date) {
		Validate.notNull(date);
		return date.getMonthOfYear() >= getStart().getMonthOfYear();
	}
	
	public int getYear(LocalDateTime date) {
		Validate.notNull(date);
		if (isDateWithinTheStartYear(date)) {
			return start.getYear();
		}
		return end.getYear();
	}
		
}
