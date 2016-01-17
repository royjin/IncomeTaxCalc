package co.nz.royjin.incometaxcalc.service.tax.rule;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.NumberUtils;

import co.nz.royjin.incometaxcalc.model.money.Currency;
import co.nz.royjin.incometaxcalc.model.money.Money;
import co.nz.royjin.incometaxcalc.model.tax.IncomeTaxLevel;
import co.nz.royjin.incometaxcalc.model.tax.IncomeTaxTable;
import co.nz.royjin.incometaxcalc.service.config.AppConfigService;

public class AustraliaIncomeTaxRuleStrategyImpl implements IncomeTaxRuleStrategy {
	
	private AppConfigService appConfigService;
	
	private static final String TAX_LEVEL_DELIMITER = "\\|";
	
	@Inject
	public AustraliaIncomeTaxRuleStrategyImpl(AppConfigService appConfigService) {
		this.appConfigService = appConfigService;
	}

	@Override
	public IncomeTaxTable generateIncomeTaxTable() {
		IncomeTaxTable incomeTaxTable = new IncomeTaxTable();
		for (String level : appConfigService.getTaxLevels()) {
			IncomeTaxLevel taxLevel = parseIncomeTaxLevel(level);
			Validate.notNull(taxLevel);
			incomeTaxTable.addLevel(taxLevel);
		}
		return incomeTaxTable;
	}
	
	private IncomeTaxLevel parseIncomeTaxLevel(String level) {
		if (isValidLevel(level)) {
			String[] attrs = level.trim().split(TAX_LEVEL_DELIMITER, -1);
			Currency currency = appConfigService.getCurrency();
			Integer order = parseInteger(attrs[0]);
			Money beginRange = parseMoney(attrs[1], currency);
			Money endRange = parseMoney(attrs[2], currency);
			Double taxPercentage = parseDouble(attrs[3]);
			Money taxBase = parseMoney(attrs[4], currency);
			return new IncomeTaxLevel(order, beginRange, endRange, taxPercentage, taxBase);
		}
		return null;
	}
	
	private boolean isValidLevel(final String level) {
		return !(StringUtils.isBlank(level) || level.trim().split(TAX_LEVEL_DELIMITER, -1).length != 5);
	}
	
	private boolean isNumber(String value) {
		return NumberUtils.isNumber(value);
	}
	
	private Integer parseInteger(String value) {
		if (isNumber(value)) {
			return Integer.parseInt(value);
		}
		return null;
	}
	
	private Double parseDouble(String value) {
		if (isNumber(value)) {
			return Double.parseDouble(value);
		}
		return null;
	}
	
	private Money parseMoney(String value, Currency currency) {
		Double doubleValue = parseDouble(value);
		if (doubleValue != null) {
			return new Money(doubleValue, currency);
		}
		return null;
	}

}
