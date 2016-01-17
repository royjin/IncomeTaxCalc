package co.nz.royjin.incometaxcalc.model.money;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Currency implements Serializable {

	private static final long serialVersionUID = -6448286025618527401L;

	private Locale locale;
	
	private String symbol;

	private static Map<String, Currency> currencies = new HashMap<String, Currency>();
	
	private static final String DEFAULT_LOCALE = "en_au";
	
	static {
		currencies.put("en_au", new Currency(new Locale("en", "AU"), "$"));
		currencies.put("en_nz", new Currency(new Locale("en", "NZ"), "$"));
	}
	
	private Currency(Locale locale, String symbol) {
		this.locale = locale;
		this.symbol = symbol;
	}

	public Locale getLocale() {
		return locale;
	}

	public String getSymbol() {
		return symbol;
	}
	
	public NumberFormat getFormat() {
		return NumberFormat.getCurrencyInstance(this.locale);
	}
	
	public static Currency find(String localeFormat) {
		Currency defaultCurrency = currencies.get(DEFAULT_LOCALE);
		if (StringUtils.isNotBlank(localeFormat) && currencies.containsKey(localeFormat.toLowerCase())) {
			return currencies.get(localeFormat.toLowerCase());
		}
		return defaultCurrency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
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
		Currency other = (Currency) obj;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}
	
	
	
}
