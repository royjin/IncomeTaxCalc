package co.nz.royjin.incometaxcalc.service.report;

import co.nz.royjin.incometaxcalc.model.report.Report;
import co.nz.royjin.incometaxcalc.service.tax.exception.CalculateIncomeTaxException;

public interface IncomeTaxReportService {

	Report generateReport(String fileInput) throws CalculateIncomeTaxException;
	
	void outputReportResult(Report report);
}
