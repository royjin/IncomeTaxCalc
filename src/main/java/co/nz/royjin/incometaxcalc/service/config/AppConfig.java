package co.nz.royjin.incometaxcalc.service.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import co.nz.royjin.incometaxcalc.model.money.Currency;

class AppConfig {
	
	private static final String APP_CONFIG_FILE = "incometaxconfig.properties";
	
	private static final String TAX_YEAR_START_DATE_FORMAT = "dd MMMM YYYY";
	
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
		LocalDateTime taxYear = LocalDateTime.parse(getTaxYear(), DateTimeFormat.forPattern(TAX_YEAR_START_DATE_FORMAT));
		Iterator<String> subKeyItr = propertiesConfig.getKeys("taxtable.level." + taxYear.getYear());
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
		return propertiesConfig.getString("tax.year").trim();
	}
}
