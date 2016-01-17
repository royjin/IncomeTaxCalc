package co.nz.royjin.incometaxcalc.service.tax;

import java.math.RoundingMode;
import java.util.Map;

import javax.inject.Inject;

import co.nz.royjin.incometaxcalc.model.money.Money;
import co.nz.royjin.incometaxcalc.model.tax.IncomeTaxLevel;
import co.nz.royjin.incometaxcalc.model.tax.IncomeTaxTable;
import co.nz.royjin.incometaxcalc.service.tax.exception.CalculateIncomeTaxException;
import co.nz.royjin.incometaxcalc.service.tax.rule.IncomeTaxRuleStrategy;

public class IncomeTaxCalculationServiceImpl implements IncomeTaxCalculationService {

	private IncomeTaxRuleStrategy incomeTaxRuleStrategy;
	
	private static final int NUMBER_OF_MONTHS = 12;
	
	@Inject
	public IncomeTaxCalculationServiceImpl(IncomeTaxRuleStrategy incomeTaxRuleStrategy) {
		this.incomeTaxRuleStrategy = incomeTaxRuleStrategy;
	}
	
	private void validateAnnualIncome(Money annualIncome) throws CalculateIncomeTaxException {
		if (annualIncome == null || annualIncome.isNegative()) {
			throw new CalculateIncomeTaxException("Invalid salary cannot be negative");
		}
	}
	
	@Override
	public Money calculateMonthlyIncomeTax(final Money annualIncome) throws CalculateIncomeTaxException {
		
		validateAnnualIncome(annualIncome);
		
		IncomeTaxTable taxTable = incomeTaxRuleStrategy.generateIncomeTaxTable();
		for (Map.Entry<Integer, IncomeTaxLevel> entryLevel : taxTable.getLevels().entrySet()) {
			IncomeTaxLevel currentLevel = entryLevel.getValue();
			IncomeTaxLevel previousLevel = taxTable.getLevel(currentLevel.getPreviousLevelOrder());
			
			if (currentLevel.isWithinRange(annualIncome)){ 
				if (currentLevel.isRuleSet() && currentLevel.hasPreviousLevel()) {
					Money result = annualIncome.subtract(previousLevel.getEndRange()).multiply(currentLevel.getTaxPercentage());
					if (currentLevel.getTaxBase() != null) {
						result = result.add(currentLevel.getTaxBase());
					}
					return result.divide(NUMBER_OF_MONTHS, 6, RoundingMode.HALF_UP).roundUp();
				}
				return new Money(annualIncome.getCurrency());
			}
		}
		
		throw new CalculateIncomeTaxException("Level was not matched with annual income: " + annualIncome.format());
	}

	@Override
	public Money calculateMonthlyGrossIncome(final Money annualIncome) throws CalculateIncomeTaxException {
		validateAnnualIncome(annualIncome);
		return annualIncome.divide(NUMBER_OF_MONTHS, 6, RoundingMode.UP).roundUp();
	}

	@Override
	public Money calculateMonthlyNetIncome(final Money annualIncome) throws CalculateIncomeTaxException {	
		Money incomeTax = this.calculateMonthlyIncomeTax(annualIncome);
		Money grossIncome = this.calculateMonthlyGrossIncome(annualIncome);
		return grossIncome.subtract(incomeTax).roundUp();
	}

	@Override
	public Money calculateMonthlySuper(Money annualIncome, Double percentage) throws CalculateIncomeTaxException {
		if (!(percentage >= 0 && percentage <= 0.5)) {
			throw new CalculateIncomeTaxException("Invalid super rate must be between 0 - 0.5 inclusive.");
		}
		Money grossIncome = this.calculateMonthlyGrossIncome(annualIncome);
		return grossIncome.multiply(percentage).roundUp();
	}

}
