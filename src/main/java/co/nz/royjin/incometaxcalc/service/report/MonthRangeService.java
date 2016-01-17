package co.nz.royjin.incometaxcalc.service.report;

import co.nz.royjin.incometaxcalc.model.report.MonthRange;

public interface MonthRangeService {

	boolean isValidMonthlyTaxPayRange(String startDate, String endDate);

	MonthRange parseMonthRange(String startDate, String endDate);

}
