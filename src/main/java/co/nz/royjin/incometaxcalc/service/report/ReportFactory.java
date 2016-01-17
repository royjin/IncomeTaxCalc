package co.nz.royjin.incometaxcalc.service.report;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import co.nz.royjin.incometaxcalc.model.money.Money;
import co.nz.royjin.incometaxcalc.model.report.MonthRange;
import co.nz.royjin.incometaxcalc.model.report.Person;
import co.nz.royjin.incometaxcalc.model.report.Report;
import co.nz.royjin.incometaxcalc.model.report.ReportList;
import co.nz.royjin.incometaxcalc.service.config.AppConfigService;
import co.nz.royjin.incometaxcalc.service.tax.IncomeTaxCalculationService;
import co.nz.royjin.incometaxcalc.service.tax.exception.CalculateIncomeTaxException;

public class ReportFactory {
	
	private IncomeTaxCalculationService incomeTaxCalculationService;

	private AppConfigService appConfigService;
	
	private MonthRangeService monthRangeService;
	
	@Inject
	public ReportFactory(IncomeTaxCalculationService incomeTaxCalculationService,
			AppConfigService appConigService, MonthRangeService monthRangeService) {
		this.incomeTaxCalculationService = incomeTaxCalculationService;
		this.appConfigService = appConigService;
		this.monthRangeService = monthRangeService;
	}
	
	public Report create(List<InputRawData> inputRawData) throws CalculateIncomeTaxException {
		Report report = new Report();
		int count = 0;
		for (InputRawData rawData : inputRawData) {
			report.addReportList(createReportList(count++, rawData));
		}
		return report;
	}

	private ReportList createReportList(Integer order, InputRawData inputRawData) throws CalculateIncomeTaxException {
		Person person = new Person(inputRawData.getFirstName(), inputRawData.getLastName());
		Money annualIncomeMoney = parseMoney(inputRawData.getAnnualIncome());
		Double superRate = parseSuperRate(inputRawData.getSuperRate());
		
		Money grossIncome = this.incomeTaxCalculationService.calculateMonthlyGrossIncome(annualIncomeMoney);
		Money incomeTax = this.incomeTaxCalculationService.calculateMonthlyIncomeTax(annualIncomeMoney);
		Money netIncome = this.incomeTaxCalculationService.calculateMonthlyNetIncome(annualIncomeMoney);
		Money superannuation = this.incomeTaxCalculationService.calculateMonthlySuper(annualIncomeMoney, superRate);
		
		String paymentMonth = inputRawData.getPaymentMonth();
		MonthRange monthRange = parsePaymentMonth(paymentMonth);
		
		return new ReportList(order, person, grossIncome, incomeTax, netIncome, superannuation, monthRange);
	}
	
	private Money parseMoney(String value) {
		return new Money(Double.parseDouble(value), appConfigService.getCurrency());
	}
	
	private Double parseSuperRate(String superRate) {
		return new BigDecimal(superRate.trim().replace("%", "")).divide(BigDecimal.valueOf(100)).doubleValue();
	}
	
	private MonthRange parsePaymentMonth(String paymentMonth) {
		if (StringUtils.isNotBlank(paymentMonth) && paymentMonth.split("-").length == 2) {
			String [] periods = paymentMonth.split("-");
			String startDate = periods[0].trim();
			String endDate = periods[1].trim();
			if (!monthRangeService.isValidMonthlyTaxPayRange(startDate, endDate)) {
				throw new IllegalArgumentException("Invalid pay periods. The format should be e.g. 01 March - 31 March. The curren format is: " + paymentMonth);
			}
			
			return monthRangeService.parseMonthRange(startDate, endDate);
		}
		throw new IllegalArgumentException("Invalid pay periods. The format should be e.g. 01 March - 31 March");
	}
}
