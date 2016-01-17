package co.nz.royjin.incometaxcalc.service.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;


import co.nz.royjin.incometaxcalc.model.money.Currency;

class AppConfig {
	
	private static final String APP_CONFIG_FILE = "incometaxconfig.properties";
	
	private static Configuration propertiesConfig;
	
	private static AppConfig appConfig = new AppConfig();
	
	static {
		try {
			propertiesConfig = new PropertiesConfiguration(APP_CONFIG_FILE);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static AppConfig getInstance() {
		return appConfig;
	}
	
	List<String> getTaxLevels() {
		List<String> levels = new ArrayList<String>();
		Iterator<String> subKeyItr = propertiesConfig.getKeys("taxtable.level");
		while (subKeyItr.hasNext()) {
			String subKey = subKeyItr.next();
			levels.add(propertiesConfig.getString(subKey));
		}
		return levels;
	}
	
	Currency getCurrency() {
		String localeFormat = propertiesConfig.getString("currency.locale");
		return Currency.find(localeFormat);
	}
	
	String getTaxYear() {
		return propertiesConfig.getString("tax.year");
	}
}
