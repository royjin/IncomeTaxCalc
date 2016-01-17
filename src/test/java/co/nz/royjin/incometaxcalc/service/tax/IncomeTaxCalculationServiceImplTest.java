package co.nz.royjin.incometaxcalc.service.tax;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import co.nz.royjin.incometaxcalc.model.money.Currency;
import co.nz.royjin.incometaxcalc.model.money.Money;
import co.nz.royjin.incometaxcalc.model.tax.IncomeTaxLevel;
import co.nz.royjin.incometaxcalc.model.tax.IncomeTaxTable;
import co.nz.royjin.incometaxcalc.service.tax.exception.CalculateIncomeTaxException;
import co.nz.royjin.incometaxcalc.service.tax.rule.IncomeTaxRuleStrategy;

@RunWith(MockitoJUnitRunner.class)
public class IncomeTaxCalculationServiceImplTest {

	@Mock
	private IncomeTaxRuleStrategy taxRuleStrategy;
	
	private IncomeTaxTable incomeTaxTable;
	
	private IncomeTaxCalculationServiceImpl incomeTaxCalcService;
	
	private Currency currency = Currency.find("en_AU");
	
	@Before
	public void init() {
		incomeTaxTable = new IncomeTaxTable();
		incomeTaxTable.addLevel(new IncomeTaxLevel(1, new Money(currency), new Money(18200.00, currency), null, null));
		incomeTaxTable.addLevel(new IncomeTaxLevel(2, new Money(18201.00, currency), new Money(37000.00, currency), 0.19, null));
		incomeTaxTable.addLevel(new IncomeTaxLevel(3, new Money(37001.00, currency), new Money(80000.00, currency), 0.325, new Money(3572.00, currency)));
		incomeTaxTable.addLevel(new IncomeTaxLevel(4, new Money(80001.00, currency), new Money(180000.00, currency), 0.37, new Money(17547.00, currency)));
		incomeTaxTable.addLevel(new IncomeTaxLevel(5, new Money(180001.00, currency), null, 0.45, new Money(54457.00, currency)));
		
		incomeTaxCalcService = new IncomeTaxCalculationServiceImpl(taxRuleStrategy);
		
		// Given tax rule table provided
		given(taxRuleStrategy.generateIncomeTaxTable()).willReturn(incomeTaxTable);
	}
	
	@Test
	public void noMonthlyIncomeTaxWhenSalaryIsLevel1() throws CalculateIncomeTaxException {
		// when annual salary is between [0, - 18200]
		Money level1Salary = new Money(15000.00, currency);
		// then incomeTax should be 0.00 (no tax).
 		Money incomeTax = incomeTaxCalcService.calculateMonthlyIncomeTax(level1Salary);
 		assertEquals(new Money(currency), incomeTax);
 		
 		// when annual salary is 0
 		level1Salary = new Money(currency);
		// then incomeTax should be 0.00 (no tax).
 		incomeTax = incomeTaxCalcService.calculateMonthlyIncomeTax(level1Salary);
 		assertEquals(new Money(currency), incomeTax);
 		
 		// when annual salary is 18200
 		level1Salary = new Money(18200.00, currency);
		// then incomeTax should be 0.00 (no tax).
 		incomeTax = incomeTaxCalcService.calculateMonthlyIncomeTax(level1Salary);
 		assertEquals(new Money(currency), incomeTax);
	}
	
	@Test
	public void calculateMonthlyIncomeTaxWhenSalaryIsLevel2() throws CalculateIncomeTaxException {
		// when annual salary is between [18201, - 37000]
		Money level2Salary = new Money(25001.00, currency);
 		Money incomeTax = incomeTaxCalcService.calculateMonthlyIncomeTax(level2Salary);
 		assertEquals(new Money(108.00, currency), incomeTax);
 		
 		// when annual salary is 18201
 		level2Salary = new Money(18201.00, currency);
		// then incomeTax should be 0.
 		incomeTax = incomeTaxCalcService.calculateMonthlyIncomeTax(level2Salary);
 		assertEquals(new Money(currency), incomeTax);
 		
 		// when annual salary is 37000
 		level2Salary = new Money(37000.00, currency);
		// then incomeTax should be 3572 (no tax).
 		incomeTax = incomeTaxCalcService.calculateMonthlyIncomeTax(level2Salary);
 		assertEquals(new Money(298.00, currency), incomeTax);
	}
	
	@Test
	public void calculateMonthlyIncomeTaxWhenSalaryIsLevel3() throws CalculateIncomeTaxException {
		// when annual salary is between [37001, - 80000]
		Money level3Salary = new Money(55003.00, currency);
 		Money incomeTax = incomeTaxCalcService.calculateMonthlyIncomeTax(level3Salary);
 		assertEquals(new Money(785.00, currency), incomeTax);
 		
 		// when annual salary is 37001
 		level3Salary = new Money(37001.00, currency);
		// then incomeTax should be 298.
 		incomeTax = incomeTaxCalcService.calculateMonthlyIncomeTax(level3Salary);
 		assertEquals(new Money(298.00, currency), incomeTax);
 		
 		// when annual salary is 80000
 		level3Salary = new Money(80000.00, currency);
		// then incomeTax should be 1462 (no tax).
 		incomeTax = incomeTaxCalcService.calculateMonthlyIncomeTax(level3Salary);
 		assertEquals(new Money(1462.00, currency), incomeTax);
	}
	
	@Test
	public void calculateMonthlyIncomeTaxWhenSalaryIsLevel4() throws CalculateIncomeTaxException {
		// when annual salary is between [80001, - 180000]
		Money level4Salary = new Money(145053.00, currency);
 		Money incomeTax = incomeTaxCalcService.calculateMonthlyIncomeTax(level4Salary);
 		assertEquals(new Money(3468.00, currency), incomeTax);
 		
 		// when annual salary is 80001
 		level4Salary = new Money(80001.00, currency);
		// then incomeTax should be 1462.
 		incomeTax = incomeTaxCalcService.calculateMonthlyIncomeTax(level4Salary);
 		assertEquals(new Money(1462.00, currency), incomeTax);
 		
 		// when annual salary is 180000
 		level4Salary = new Money(180000.00, currency);
		// then incomeTax should be 4546 (no tax).
 		incomeTax = incomeTaxCalcService.calculateMonthlyIncomeTax(level4Salary);
 		assertEquals(new Money(4546.00, currency), incomeTax);
	}
	
	@Test
	public void calculateMonthlyIncomeTaxWhenSalaryIsLevel5() throws CalculateIncomeTaxException {
		// when annual salary is between [180001 - ]
		Money level5Salary = new Money(2500333.00, currency);
 		Money incomeTax = incomeTaxCalcService.calculateMonthlyIncomeTax(level5Salary);
 		assertEquals(new Money(91551.00, currency), incomeTax);
 		
 		// when annual salary is 180001
 		level5Salary = new Money(180001.00, currency);
		// then incomeTax should be 4538.
 		incomeTax = incomeTaxCalcService.calculateMonthlyIncomeTax(level5Salary);
 		assertEquals(new Money(4538.00, currency), incomeTax);
	}
	
	@Test
	public void calculateMonthlyGrossIncome() throws CalculateIncomeTaxException {
		// given salary is 135009.00
		Money salary = new Money(135009.00, currency);
		// when calculate the monthly gross income
		Money actualMontylyCrossIncome = incomeTaxCalcService.calculateMonthlyGrossIncome(salary);
		// then
		assertEquals(new Money(11251.00, currency), actualMontylyCrossIncome);
		
		// given no salary
		salary = new Money(currency);
		// when calculate the monthly gross income
		actualMontylyCrossIncome = incomeTaxCalcService.calculateMonthlyGrossIncome(salary);
		// then no gross income
		assertEquals(new Money(currency), actualMontylyCrossIncome);
	}
	
	@Test
	public void calculateMonthlyNetIncome() throws CalculateIncomeTaxException {
		// given salary is 75013.00
		Money salary = new Money(75013.00, currency);
		// when calculate the monthly net income
		Money actualMontlyNetIncome = incomeTaxCalcService.calculateMonthlyNetIncome(salary);
		// then
		assertEquals(new Money(4924.00, currency), actualMontlyNetIncome);
		
		// given no salary
		salary = new Money(currency);
		// when calculate the monthly net income
		actualMontlyNetIncome = incomeTaxCalcService.calculateMonthlyNetIncome(salary);
		// then no gross income
		assertEquals(new Money(currency), actualMontlyNetIncome);
	}
	
	@Test
	public void calculateMonthlySuper() throws CalculateIncomeTaxException {
		// given salary is 75013.00
		Money salary = new Money(75013.00, currency);
		// when calculate the monthly net income
		Money actualMontlyNetIncome = incomeTaxCalcService.calculateMonthlySuper(salary, 0.09);
		// then
		assertEquals(new Money(563.00, currency), actualMontlyNetIncome);
	}
	
	@Test(expected = CalculateIncomeTaxException.class)
	public void invalidCalculationWhenAnnaulIncomeIsNull() throws CalculateIncomeTaxException {
 		incomeTaxCalcService.calculateMonthlyIncomeTax(null);
	}
	
	@Test(expected = CalculateIncomeTaxException.class)
	public void invalidCalculationWhenAnnaulIncomeIsNegative() throws CalculateIncomeTaxException {
 		incomeTaxCalcService.calculateMonthlyIncomeTax(new Money(-100.00, currency));
	}
	
	@Test(expected = CalculateIncomeTaxException.class)
	public void invalidCalculationWhenTaxLevelConfigurationNotFound() throws CalculateIncomeTaxException {
		// Given tax rule table with empty levels
		given(taxRuleStrategy.generateIncomeTaxTable()).willReturn(new IncomeTaxTable());
		incomeTaxCalcService.calculateMonthlyIncomeTax(new Money(10000.00, currency));
	}
	
	@Test(expected = CalculateIncomeTaxException.class)
	public void invalidSuperRateWhenCalculateSuper() throws CalculateIncomeTaxException {
		incomeTaxCalcService.calculateMonthlySuper(new Money(200000.00, currency), 0.8);
	}
}
