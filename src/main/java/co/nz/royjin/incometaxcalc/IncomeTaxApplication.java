package co.nz.royjin.incometaxcalc;

import javax.inject.Inject;

import co.nz.royjin.incometaxcalc.service.report.IncomeTaxReportService;

public class IncomeTaxApplication {
	
	private IncomeTaxReportService reportService;
	
	@Inject
	public IncomeTaxApplication(IncomeTaxReportService reportService) {
		this.reportService = reportService;
	}

	public IncomeTaxReportService getReportService() {
		return reportService;
	}
}
