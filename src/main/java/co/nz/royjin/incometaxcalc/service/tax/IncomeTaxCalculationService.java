package co.nz.royjin.incometaxcalc.service.tax;

import co.nz.royjin.incometaxcalc.model.money.Money;
import co.nz.royjin.incometaxcalc.service.tax.exception.CalculateIncomeTaxException;

public interface IncomeTaxCalculationService {

	Money calculateMonthlyIncomeTax(Money annualIncome) throws CalculateIncomeTaxException; 
	
	Money calculateMonthlyGrossIncome(Money annualIncome) throws CalculateIncomeTaxException;
	
	Money calculateMonthlyNetIncome(Money annualIncome) throws CalculateIncomeTaxException;
	
	Money calculateMonthlySuper(Money annualIncome, Double percentage) throws CalculateIncomeTaxException;
}
