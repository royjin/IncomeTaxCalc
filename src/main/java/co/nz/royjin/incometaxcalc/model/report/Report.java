package co.nz.royjin.incometaxcalc.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Report implements Serializable {

	private static final long serialVersionUID = 8284210163314903620L;
	
	private List<ReportList> reportLists = new ArrayList<ReportList>();
	
	public void addReportList(ReportList reportList) {
		reportLists.add(reportList);
	}

	public List<ReportList> getReportLists() {
		sort();
		return this.reportLists;
	}
	
	private void sort() {
		Collections.sort(this.reportLists, new Comparator<ReportList>() {

			@Override
			public int compare(ReportList o1, ReportList o2) {
				return o1.getOrder().compareTo(o2.getOrder());
			}
		});
	}
}
