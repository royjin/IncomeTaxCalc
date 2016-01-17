package co.nz.royjin.incometaxcalc.service.report;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import co.nz.royjin.incometaxcalc.model.money.Currency;
import co.nz.royjin.incometaxcalc.model.money.Money;
import co.nz.royjin.incometaxcalc.model.report.MonthRange;
import co.nz.royjin.incometaxcalc.model.report.Report;
import co.nz.royjin.incometaxcalc.service.config.AppConfigService;
import co.nz.royjin.incometaxcalc.service.tax.IncomeTaxCalculationService;
import co.nz.royjin.incometaxcalc.service.tax.exception.CalculateIncomeTaxException;

@RunWith(MockitoJUnitRunner.class)
public class ReportFactoryTest {
	
	@Mock
	private IncomeTaxCalculationService incomeTaxCalculationService;

	@Mock
	private AppConfigService appConfigService;
	
	@Mock
	private MonthRangeService monthRangeService;
	
	private ReportFactory reportFactory;
	
	@Before
	public void init() throws CalculateIncomeTaxException {
		Currency auCurrency = Currency.find("en_AU");
		
		LocalDateTime start = LocalDateTime.parse("01 March 2013", DateTimeFormat.forPattern("dd MMMM YYYY"));
		LocalDateTime end = LocalDateTime.parse("31 March 2013", DateTimeFormat.forPattern("dd MMMM YYYY"));
		
		given(appConfigService.getTaxStartYear()).willReturn("01 July 2012");
		given(appConfigService.getCurrency()).willReturn(auCurrency);
		given(incomeTaxCalculationService.calculateMonthlyGrossIncome(any(Money.class))).willReturn(new Money(4584.00, auCurrency));
		given(incomeTaxCalculationService.calculateMonthlyIncomeTax(any(Money.class))).willReturn(new Money(298.00, auCurrency));
		given(incomeTaxCalculationService.calculateMonthlyNetIncome(any(Money.class))).willReturn(new Money(4256.00, auCurrency));
		given(incomeTaxCalculationService.calculateMonthlySuper(any(Money.class), any(Double.class))).willReturn(new Money(413.00, auCurrency));
		given(monthRangeService.isValidMonthlyTaxPayRange(any(String.class), any(String.class))).willReturn(true);
		given(monthRangeService.parseMonthRange(any(String.class), any(String.class))).willReturn(new MonthRange(start, end));
		reportFactory = new ReportFactory(incomeTaxCalculationService, appConfigService, monthRangeService);
	}

	@Test
	public void generateReport() throws CalculateIncomeTaxException {
		List<InputRawData> rawData = new ArrayList<InputRawData>();
		rawData.add(0, new InputRawData("dummyFirstName", "dummyLastName", "55003", "9%", "01 March - 31 March"));
		Report report = reportFactory.create(rawData);
		assertEquals(1, report.getReportLists().size());
		assertEquals("dummyFirstName dummyLastName", report.getReportLists().get(0).getPerson().toString());
		assertEquals("$298.00", report.getReportLists().get(0).getIncomeTax());
		assertEquals("$4,584.00", report.getReportLists().get(0).getGrossIncome());
		assertEquals("$4,256.00", report.getReportLists().get(0).getNetIncome());
		assertEquals("$413.00", report.getReportLists().get(0).getSuperannuation());
		assertEquals("01 March 2013 - 31 March 2013", report.getReportLists().get(0).getPaymentMonth());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidPaymentPeriod() throws CalculateIncomeTaxException {
		given(monthRangeService.isValidMonthlyTaxPayRange(any(String.class), any(String.class))).willReturn(false);
		List<InputRawData> rawData = new ArrayList<InputRawData>();
		rawData.add(0, new InputRawData("dummyFirstName", "dummyLastName", "55003", "9%", "01 March - 31 March"));
		reportFactory.create(rawData);
	}

}
