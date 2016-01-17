package co.nz.royjin.incometaxcalc.service.tax.rule;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.*;

import co.nz.royjin.incometaxcalc.model.money.Currency;
import co.nz.royjin.incometaxcalc.model.money.Money;
import co.nz.royjin.incometaxcalc.model.tax.IncomeTaxLevel;
import co.nz.royjin.incometaxcalc.model.tax.IncomeTaxTable;
import co.nz.royjin.incometaxcalc.service.config.AppConfigService;

@RunWith(MockitoJUnitRunner.class)
public class AustraliaIncomeTaxRuleStrategyImplTest {
	
	@Mock
	private AppConfigService appConfigService;
	
	private List<String> configTaxLevels = new ArrayList<String>();
	
	private AustraliaIncomeTaxRuleStrategyImpl auIncomeTaxStrategy;
	
	@Before
	public void init() {
		configTaxLevels.add("1|0|18200||");
		configTaxLevels.add("2|18201|37000|0.19|");
		configTaxLevels.add("3|37001|80000|0.325|3572");
		configTaxLevels.add("4|80001|180000|0.37|17547");
		configTaxLevels.add("5|180001||0.45|54457");
		
		// given
		given(appConfigService.getCurrency()).willReturn(Currency.find("en_AU"));
		given(appConfigService.getTaxLevels()).willReturn(configTaxLevels);
		
	}

	@Test
	public void evaluateTaxTableLevel1() {
		// when
		auIncomeTaxStrategy = new AustraliaIncomeTaxRuleStrategyImpl(appConfigService);
		IncomeTaxTable incomeTaxTable = auIncomeTaxStrategy.generateIncomeTaxTable();
		IncomeTaxLevel level1 = incomeTaxTable.getLevel(1);
		
		// Then
		assertEquals(new Money(Currency.find("en_AU")), level1.getBeginRange());
		assertEquals(new Money(18200.00, Currency.find("en_AU")), level1.getEndRange());
		assertNull(level1.getTaxBase());
		assertNull(level1.getTaxPercentage());
		assertNull(level1.getPreviousLevelOrder());
		assertFalse(level1.isRuleSet());
		assertFalse(level1.isMaxRange());
	}
	
	@Test
	public void evaluateTaxTableLevel2() {
		// when
		auIncomeTaxStrategy = new AustraliaIncomeTaxRuleStrategyImpl(appConfigService);
		IncomeTaxTable incomeTaxTable = auIncomeTaxStrategy.generateIncomeTaxTable();
		IncomeTaxLevel level2 = incomeTaxTable.getLevel(2);
		
		// Then
		assertEquals(new Money(18201.00, Currency.find("en_AU")), level2.getBeginRange());
		assertEquals(new Money(37000.00, Currency.find("en_AU")), level2.getEndRange());
		assertEquals(new Double(0.19), level2.getTaxPercentage());
		assertNull(level2.getTaxBase());
		assertEquals(new Integer(1), level2.getPreviousLevelOrder());
		assertTrue(level2.isRuleSet());
		assertFalse(level2.isMaxRange());
	}
	
	@Test
	public void evaluateTaxTableLevel3() {
		// when
		auIncomeTaxStrategy = new AustraliaIncomeTaxRuleStrategyImpl(appConfigService);
		IncomeTaxTable incomeTaxTable = auIncomeTaxStrategy.generateIncomeTaxTable();
		IncomeTaxLevel level3 = incomeTaxTable.getLevel(3);
		
		// Then
		assertEquals(new Money(37001.00, Currency.find("en_AU")), level3.getBeginRange());
		assertEquals(new Money(80000.00, Currency.find("en_AU")), level3.getEndRange());
		assertEquals(new Double(0.325), level3.getTaxPercentage());
		assertEquals(new Money(3572.00, Currency.find("en_AU")), level3.getTaxBase());
		assertEquals(new Integer(2), level3.getPreviousLevelOrder());
		assertTrue(level3.isRuleSet());
		assertFalse(level3.isMaxRange());
	}
	
	@Test
	public void evaluateTaxTableLevel4() {
		// when
		auIncomeTaxStrategy = new AustraliaIncomeTaxRuleStrategyImpl(appConfigService);
		IncomeTaxTable incomeTaxTable = auIncomeTaxStrategy.generateIncomeTaxTable();
		IncomeTaxLevel level4 = incomeTaxTable.getLevel(4);
		
		// Then
		assertEquals(new Money(80001.00, Currency.find("en_AU")), level4.getBeginRange());
		assertEquals(new Money(180000.00, Currency.find("en_AU")), level4.getEndRange());
		assertEquals(new Double(0.37), level4.getTaxPercentage());
		assertEquals(new Money(17547.00, Currency.find("en_AU")), level4.getTaxBase());
		assertEquals(new Integer(3), level4.getPreviousLevelOrder());
		assertTrue(level4.isRuleSet());
		assertFalse(level4.isMaxRange());
	}
	
	@Test
	public void evaluateTaxTableLevel5() {
		// when
		auIncomeTaxStrategy = new AustraliaIncomeTaxRuleStrategyImpl(appConfigService);
		IncomeTaxTable incomeTaxTable = auIncomeTaxStrategy.generateIncomeTaxTable();
		IncomeTaxLevel level5 = incomeTaxTable.getLevel(5);
		
		// Then
		assertEquals(new Money(180001.00, Currency.find("en_AU")), level5.getBeginRange());
		assertNull(level5.getEndRange());
		assertEquals(new Double(0.45), level5.getTaxPercentage());
		assertEquals(new Money(54457.00, Currency.find("en_AU")), level5.getTaxBase());
		assertEquals(new Integer(4), level5.getPreviousLevelOrder());
		assertTrue(level5.isRuleSet());
		assertTrue(level5.isMaxRange());
	}

}
