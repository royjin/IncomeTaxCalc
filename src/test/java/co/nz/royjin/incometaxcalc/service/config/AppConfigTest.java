package co.nz.royjin.incometaxcalc.service.config;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import co.nz.royjin.incometaxcalc.model.money.Currency;
import co.nz.royjin.incometaxcalc.service.config.AppConfig;

public class AppConfigTest {

	@Test
	public void loadTaxLevel() {
		List<String> levels = AppConfig.getInstance().getTaxLevels();
		assertTrue(levels.get(0).equals("1|0|18200||"));
		assertTrue(levels.get(1).equals("2|18201|37000|0.19|"));
		assertTrue(levels.get(2).equals("3|37001|80000|0.325|3572"));
		assertTrue(levels.get(3).equals("4|80001|180000|0.37|17547"));
		assertTrue(levels.get(4).equals("5|180001||0.45|54457"));
		assertEquals(5, levels.size());
	}
	
	@Test
	public void loadTaxYear() {
		assertEquals("01 July 2012", AppConfig.getInstance().getTaxYear());
	}
	
	@Test
	public void loadCurrencyLocale() {
		assertEquals(Currency.find("en_AU"), AppConfig.getInstance().getCurrency());
	}

}
