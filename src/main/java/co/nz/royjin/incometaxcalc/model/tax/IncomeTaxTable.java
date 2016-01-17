package co.nz.royjin.incometaxcalc.model.tax;

import java.util.HashMap;
import java.util.Map;

public class IncomeTaxTable {

	private Map<Integer, IncomeTaxLevel> levels = new HashMap<Integer, IncomeTaxLevel>();
	
	public Map<Integer, IncomeTaxLevel> getLevels() {
		return levels;
	}

	public void addLevel(final IncomeTaxLevel level) {
		this.getLevels().put(level.getOrder(), level);
	}
	
	public boolean hasLevel(final Integer order) {
		return order != null && levels.containsKey(order);
	}
	
	public IncomeTaxLevel getLevel(final Integer order) {
		if (hasLevel(order)) {
			return levels.get(order);
		}
		return null;
	}
}
