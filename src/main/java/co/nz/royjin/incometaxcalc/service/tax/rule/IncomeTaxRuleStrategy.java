package co.nz.royjin.incometaxcalc.service.tax.rule;

import co.nz.royjin.incometaxcalc.model.tax.IncomeTaxTable;

public interface IncomeTaxRuleStrategy {
	
	IncomeTaxTable generateIncomeTaxTable();
}
