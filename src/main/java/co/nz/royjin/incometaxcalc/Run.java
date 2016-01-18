package co.nz.royjin.incometaxcalc;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Guice;
import com.google.inject.Injector;

import co.nz.royjin.incometaxcalc.model.report.Report;
import co.nz.royjin.incometaxcalc.service.module.ServiceModule;
import co.nz.royjin.incometaxcalc.service.tax.exception.CalculateIncomeTaxException;

public class Run {
	
	private static final String DEFAULT_INPUT_FILE = "personSalaryInput.csv";
	
	public static void main(String args[]) {
		Injector injector = Guice.createInjector(new ServiceModule());        
        IncomeTaxApplication app = injector.getInstance(IncomeTaxApplication.class);

        String fileName = DEFAULT_INPUT_FILE;
        if (args.length > 0 && StringUtils.isNotBlank(args[0])) {
        		fileName = args[0];
        }
        
		try {
			System.out.println("Start generating the report with input file: " + fileName);
			Report report = app.getReportService().generateReport(fileName);
			String outputFileName = app.getReportService().outputReportResult(report);
			System.out.println("Report was generated successfully and please check all the details in the " + outputFileName);
		} catch (CalculateIncomeTaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}
