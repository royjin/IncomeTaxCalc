package co.nz.royjin.incometaxcalc.model.tax;

import co.nz.royjin.incometaxcalc.model.money.Money;

public class IncomeTaxLevel {
	
	private Integer order;
	
	private Money beginRange;
	
	private Money endRange;
	
	private Double taxPercentage;
	
	private Money lump;

	public IncomeTaxLevel(Integer order, Money beginRange, Money endRange, Double taxPercentage, Money lump) {
		this.order = order;
		this.beginRange = beginRange;
		this.endRange = endRange;
		this.taxPercentage = taxPercentage;
		this.lump = lump;
	}

	public Integer getOrder() {
		return order;
	}

	public Money getBeginRange() {
		return beginRange;
	}

	public Money getEndRange() {
		return endRange;
	}

	public Double getTaxPercentage() {
		return taxPercentage;
	}

	public Money getLump() {
		return lump;
	}
	
	public boolean isRuleSet() {
		return taxPercentage != null;
	}
	
	public boolean isMaxRange() {
		return this.endRange == null;
	}
	
	public boolean isWithinRange(Money money) {
		if (isMaxRange()) {
			return money.isGreaterThanOrEqual(beginRange);
		}
		return money.isGreaterThanOrEqual(beginRange) && money.isLessThanOrEqual(endRange);
	}
	
	public Integer getPreviousLevelOrder() {
		if (this.hasPreviousLevel()) {
			return this.order - 1;
		}
		return null;
	}
	
	public boolean hasPreviousLevel() {
		return this.order - 1 > 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
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
		IncomeTaxLevel other = (IncomeTaxLevel) obj;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		return true;
	}
}
