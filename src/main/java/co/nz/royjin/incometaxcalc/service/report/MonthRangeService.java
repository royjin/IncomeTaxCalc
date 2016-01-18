package co.nz.royjin.incometaxcalc.service.report;

import co.nz.royjin.incometaxcalc.model.report.MonthRange;

public interface MonthRangeService {

	/**
	 * Check whether the start date and end date are full calendar month.
	 * @param startDate start date
	 * @param endDate end date
	 * @return true if it is the full calendar month.
	 */
	boolean isValidMonthlyTaxPayRange(String startDate, String endDate);

	/**
	 * Parse the month range with start date and end date.
	 * @param startDate string start date.
	 * @param endDate string end date.
	 * @return month range.
	 */
	MonthRange parseMonthRange(String startDate, String endDate);

}
