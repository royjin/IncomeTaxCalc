package co.nz.royjin.incometaxcalc.service.report;

import co.nz.royjin.incometaxcalc.model.report.Report;
import co.nz.royjin.incometaxcalc.service.tax.exception.CalculateIncomeTaxException;

public interface IncomeTaxReportService {

	/**
	 * Generate Report.
	 * @param fileInput file.
	 * @return Report model.
	 * @throws CalculateIncomeTaxException
	 */
	Report generateReport(String fileInput) throws CalculateIncomeTaxException;
	
	/**
	 * Output Report.
	 * @param report Report.
	 * @return String the output file name.
	 */
	String outputReportResult(Report report);
}
