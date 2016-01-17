package co.nz.royjin.incometaxcalc.service.config;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import co.nz.royjin.incometaxcalc.service.config.AppConfig;

public class AppConfigTest {

	@Test
	public void loadTaxLevel() {
		List<String> levels = AppConfig.getInstance().getTaxLevels();
		assertTrue(levels.get(0).equals("1|0|18200||"));
		assertEquals(5, levels.size());
	}

}
