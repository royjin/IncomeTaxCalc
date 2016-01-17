package co.nz.royjin.incometaxcalc.service.report;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.joda.time.IllegalFieldValueException;

import co.nz.royjin.incometaxcalc.service.config.AppConfigService;

@RunWith(MockitoJUnitRunner.class)
public class MonthRangeServiceImplTest {

	@Mock
	private AppConfigService appConfigService;
	
	private MonthRangeService monthRangeService;
	
	@Before
	public void init() {
		given(appConfigService.getTaxStartYear()).willReturn("01 July 2012");
		monthRangeService = new MonthRangeServiceImpl(appConfigService);
	}
	
	@Test
	public void validateMonthRange() {
		assertTrue(monthRangeService.isValidMonthlyTaxPayRange("01 March", "31 March"));
		assertTrue(monthRangeService.isValidMonthlyTaxPayRange("01 April", "30 April"));
		assertTrue(monthRangeService.isValidMonthlyTaxPayRange("01 February", "28 February"));
		assertFalse(monthRangeService.isValidMonthlyTaxPayRange("01 March", "03 March"));
		assertFalse(monthRangeService.isValidMonthlyTaxPayRange("01 February", "12 February"));
		assertFalse(monthRangeService.isValidMonthlyTaxPayRange("01 March", "03 April"));
	}
	
	@Test(expected = IllegalFieldValueException.class)
	public void invalidMonthRange() {
		assertTrue(monthRangeService.isValidMonthlyTaxPayRange("01 March", "32 March"));
	}
}
