package co.nz.royjin.incometaxcalc.service.tax.rule;

import co.nz.royjin.incometaxcalc.model.tax.IncomeTaxTable;

public interface IncomeTaxRuleStrategy {
	
	/**
	 * Generate the income tax table based on the configuration.
	 * @return IncomeTaxTable.
	 */
	IncomeTaxTable generateIncomeTaxTable();
}
