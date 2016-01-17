package co.nz.royjin.incometaxcalc.service.config;

import java.util.List;

import co.nz.royjin.incometaxcalc.model.money.Currency;

public interface AppConfigService {
	
	List<String> getTaxLevels();
	
	Currency getCurrency();
	
	String getTaxStartYear();
}
