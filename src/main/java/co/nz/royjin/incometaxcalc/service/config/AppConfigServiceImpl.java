package co.nz.royjin.incometaxcalc.service.config;

import java.util.List;
import co.nz.royjin.incometaxcalc.model.money.Currency;


public class AppConfigServiceImpl implements AppConfigService {
	
	private AppConfig config = AppConfig.getInstance();

	@Override
	public List<String> getTaxLevels() {
		return config.getTaxLevels();
	}
	
	@Override
	public Currency getCurrency() {
		return config.getCurrency();
	}

	@Override
	public String getTaxStartYear() {
		return config.getTaxYear();
	}
	
	
}
