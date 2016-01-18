package co.nz.royjin.incometaxcalc.model.money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang.Validate;

public class Money implements Serializable {

	private static final long serialVersionUID = -8626504937717521696L;
	
	private BigDecimal value;
	
	private Currency currency;
	
	public Money(Currency currency) {
		Validate.notNull(currency);
		
		this.value = BigDecimal.ZERO;
		this.currency = currency;
	}

	public Money(Double value, Currency currency) {
		Validate.notNull(value);
		Validate.notNull(currency);
		
		this.value = new BigDecimal(value);
		this.currency = currency;
	}
	
	public Money(BigDecimal value, Currency currency) {
		Validate.notNull(value);
		Validate.notNull(currency);
		
		this.value = value;
		this.currency = currency;
	}

	public BigDecimal getValue() {
		return this.value;
	}
	
	public Currency getCurrency() {
		return currency;
	}

	public String format() {
		return this.currency.getFormat().format(value);
	}
	
	public Money add(Money money) {
		validateMoney(money);
		
		BigDecimal result = this.value.add(money.getValue());
		return new Money(result, this.currency);
	}
	
	public Money subtract(Money money) {
		validateMoney(money);
		
		BigDecimal result = this.value.subtract(money.getValue());
		return new Money(result, this.currency);
	}
	
	public Money multiply(Money money) {
		validateMoney(money);
		
		BigDecimal result = this.value.multiply(money.getValue());
		return new Money(result, this.currency);
	}
	
	public Money divide(final Double val, final int scale, final RoundingMode roundingMode) {
		Validate.notNull(val);
		
		BigDecimal result = this.value.divide(BigDecimal.valueOf(val), scale, roundingMode);
		return new Money(result, this.currency);
	}
	
	public Money divide(final Integer val, final int scale, final RoundingMode roundingMode) {
		Validate.notNull(val);
		
		BigDecimal result = this.value.divide(BigDecimal.valueOf(val), scale, roundingMode);
		return new Money(result, this.currency);
	}
	
	public Money multiply(Double val) {
		Validate.notNull(val);
		
		BigDecimal result = this.value.multiply(BigDecimal.valueOf(val));
		return new Money(result, this.currency);
	}
	
	public Money roundUp() {
		this.value = this.value.setScale(0, BigDecimal.ROUND_HALF_UP);
		return this;
	}
	
	public Money roundDown() {
		this.value = this.value.setScale(0, BigDecimal.ROUND_DOWN);
		return this;
	}
	
	public boolean isGreaterThan(Money money) {
		validateMoney(money);
		return this.value.compareTo(money.getValue()) > 0;
	}
	
	public boolean isLessThan(Money money) {
		validateMoney(money);
		return this.value.compareTo(money.getValue()) < 0;
	}
	
	public boolean isEqualTo(Money money) {
		validateMoney(money);
		return this.value.compareTo(money.getValue()) == 0;
	}
	
	public boolean isGreaterThanOrEqual(Money money) {
		validateMoney(money);
		return !this.isLessThan(money);
	}
	
	public boolean isLessThanOrEqual(Money money) {
		validateMoney(money);
		return !this.isGreaterThan(money);
	}
	
	public boolean isNegative() {
		return this.getValue().compareTo(BigDecimal.ZERO) < 0;
	}
	
	private void validateMoney(Money money) {
		if (money == null || !this.currency.equals(money.getCurrency())) {
			throw new IllegalArgumentException("Money with different currency: " + money.getCurrency());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Money other = (Money) obj;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!(value.doubleValue() == other.value.doubleValue()))
			return false;
		return true;
	}
	
	
}
