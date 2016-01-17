package co.nz.royjin.incometaxcalc.service.report;

import javax.inject.Inject;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import co.nz.royjin.incometaxcalc.model.report.MonthRange;
import co.nz.royjin.incometaxcalc.service.config.AppConfigService;

public class MonthRangeServiceImpl implements MonthRangeService {
	
	private AppConfigService appConfigService;
	
	private static final String TAX_YEAR_START_DATE_FORMAT = "dd MMMM YYYY";
	
	private static final String TAX_MONTHLY_DATE_FORMAT = "dd MMMM";
	
	@Inject
	public MonthRangeServiceImpl(AppConfigService appConfigService) {
		this.appConfigService = appConfigService;		
	}
	
	@Override
	public boolean isValidMonthlyTaxPayRange(String startDate, String endDate) {
		MonthRange monthRange = parseMonthRange(startDate, endDate);
		return monthRange.getStart().compareTo(monthRange.getEnd().plusDays(1).minusMonths(1)) == 0;
	}
	
	@Override
	public MonthRange parseMonthRange(String startDate, String endDate) {
		LocalDateTime start = LocalDateTime.parse(startDate.trim(), DateTimeFormat.forPattern(TAX_MONTHLY_DATE_FORMAT));
		LocalDateTime end = LocalDateTime.parse(endDate.trim(), DateTimeFormat.forPattern(TAX_MONTHLY_DATE_FORMAT));
		int year = getTaxYear().getYear(start);
		LocalDateTime startWithYear = start.withYear(year);
		LocalDateTime endWithYear = end.withYear(year);
		return new MonthRange(startWithYear, endWithYear);
	}
	
	private MonthRange getTaxYear() {
		LocalDateTime start = LocalDateTime.parse(appConfigService.getTaxStartYear(), DateTimeFormat.forPattern(TAX_YEAR_START_DATE_FORMAT));
		LocalDateTime end = start.plusYears(1);
		return new MonthRange(start, end);
	}
}
