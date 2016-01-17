package co.nz.royjin.incometaxcalc.model;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

import co.nz.royjin.incometaxcalc.model.money.Currency;

public class CurrencyTest {

	@Test
	public void findCurrency() {
		// given locale is Au
		String locale = "en_AU";
		// when find currency
		Currency actualCurrency = Currency.find(locale);
		// then get AU currency
		assertEquals(new Locale("en", "AU"), actualCurrency.getLocale());

		// given locale is NZ
		locale = "en_NZ";
		// when find currency
		actualCurrency = Currency.find(locale);
		// then get NZ currency
		assertEquals(new Locale("en", "NZ"), actualCurrency.getLocale());
	}
	
	@Test
	public void defaultCurrency() {
		// given locale is empty
		String locale = "";
		// when find currency
		Currency actualCurrency = Currency.find(locale);
		// then get AU currency
		assertEquals(new Locale("en", "AU"), actualCurrency.getLocale());

		// given locale is unknown en_BE
		locale = "en_BE";
		// when find currency
		actualCurrency = Currency.find(locale);
		// then get NZ currency
		assertEquals(new Locale("en", "AU"), actualCurrency.getLocale());
	}
	
	@Test
	public void compareCurrency() {
		Currency nzCurrency = Currency.find("en_NZ");
		Currency auCurrency = Currency.find("en_AU");
		assertFalse(nzCurrency.equals(auCurrency));
	}

}
