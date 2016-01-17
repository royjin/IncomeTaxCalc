package co.nz.royjin.incometaxcalc.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

import co.nz.royjin.incometaxcalc.model.money.Currency;
import co.nz.royjin.incometaxcalc.model.money.Money;

public class MoneyTest {

	@Test
	public void moneyInitalize() {
		Currency currency = Currency.find("en_AU");
		Money money = new Money(currency);
		assertEquals(money.getValue(), BigDecimal.ZERO);
		
		money = new Money(12.00, currency);
		assertEquals(money.getValue(), new BigDecimal(12.00));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidMoneyInitalize() {
		new Money(12.00, null);
	}
	
	@Test
	public void moneyCalculation() {
		Currency currency = Currency.find("en_AU");
		Money moneyOne = new Money(120.00, currency);
		Money moneyTwo = new Money(30.00, currency);
		
		assertEquals(new Money(150.00, currency), moneyOne.add(moneyTwo));
		assertEquals(new Money(90.00, currency), moneyOne.subtract(moneyTwo));
		assertEquals(new Money(3600.00, currency), moneyOne.multiply(moneyTwo));
		assertEquals(new Money(4.00, currency), moneyOne.divide(30.00, 1, RoundingMode.HALF_UP));
	}
	
	@Test
	public void compareMoney() {
		Currency currency = Currency.find("en_AU");
		Money moneyOne = new Money(120.00, currency);
		Money moneyTwo = new Money(30.00, currency);
		
		assertTrue(moneyOne.isGreaterThan(moneyTwo));
		assertTrue(moneyTwo.isLessThan(moneyOne));
		assertTrue(moneyOne.isGreaterThanOrEqual(moneyTwo));
		assertTrue(moneyTwo.isLessThanOrEqual(moneyOne));
		assertFalse(moneyTwo.isEqualTo(moneyOne));
		
		moneyOne = new Money(80.00, currency);
		moneyTwo = new Money(80.00, currency);
		assertTrue(moneyTwo.isEqualTo(moneyOne));
		assertTrue(moneyOne.isGreaterThanOrEqual(moneyTwo));
		assertTrue(moneyTwo.isLessThanOrEqual(moneyOne));
		
		moneyOne = new Money(-100.00, currency);
		assertTrue(moneyOne.isNegative());
	}
	
	@Test
	public void roundUp() {
		Currency auCurrency = Currency.find("en_AU");
		Money moneyOne = new Money(122.9801, auCurrency);
		assertEquals(new Money(123.00, auCurrency), moneyOne.roundUp());
		assertEquals(new Money(123.00, auCurrency), moneyOne.roundDown());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidMoneyCompare() {
		Currency auCurrency = Currency.find("en_AU");
		Currency nzCurrency = Currency.find("en_NZ");
		Money moneyOne = new Money(120.00, auCurrency);
		Money moneyTwo = new Money(30.00, nzCurrency);
		
		moneyOne.isGreaterThan(moneyTwo);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidMoneyCalculation() {
		Currency auCurrency = Currency.find("en_AU");
		Currency nzCurrency = Currency.find("en_NZ");
		Money moneyOne = new Money(120.00, auCurrency);
		Money moneyTwo = new Money(30.00, nzCurrency);
		
		moneyOne.add(moneyTwo);
	}

}
