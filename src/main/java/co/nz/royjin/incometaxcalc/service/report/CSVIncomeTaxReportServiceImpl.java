package co.nz.royjin.incometaxcalc.service.report;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import co.nz.royjin.incometaxcalc.model.report.Report;
import co.nz.royjin.incometaxcalc.model.report.ReportList;
import co.nz.royjin.incometaxcalc.service.tax.exception.CalculateIncomeTaxException;

public class CSVIncomeTaxReportServiceImpl implements IncomeTaxReportService {
	
	private ReportFactory reportFactory;
	
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	private static final String OUTPUT_FILE_NAME = "incometax_output_%s.csv";
	
	private static final Object [] FILE_HEADER = {"Name","Pay Period","Gross Income","Income Tax","Net Income","Super"};
	
	@Inject
	public CSVIncomeTaxReportServiceImpl(ReportFactory reportFactory) {
		this.reportFactory = reportFactory;
	}

	@Override
	public Report generateReport(String fileInput) throws CalculateIncomeTaxException {
		Reader in = null;
		List<InputRawData> inputRawData = new ArrayList<InputRawData>();
		try {
			in = new FileReader(fileInput);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().withIgnoreEmptyLines().parse(in);
			for (CSVRecord record : records) {
			    String firstName = record.get("FirstName");
			    String lastName = record.get("LastName");
			    String annualSalary = record.get("AnnualSalary");
			    String superRate = record.get("SuperRate");
			    String paymentMonth = record.get("PaymentMonth");
			    inputRawData.add(new InputRawData(firstName, lastName, annualSalary, superRate, paymentMonth));
			}
		} catch (FileNotFoundException fileNotFound) {
			fileNotFound.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return reportFactory.create(inputRawData);
	}
	
	@Override
	public void outputReportResult(Report report) {
		
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
				
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmyyHHmmss");
			String outputFileName = String.format(OUTPUT_FILE_NAME, dateFormat.format(new Date()));
			fileWriter = new FileWriter(outputFileName);
	        csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
	        csvFilePrinter.printRecord(FILE_HEADER);
			
			for (ReportList reportList : report.getReportLists()) {
				csvFilePrinter.printRecord(reportList.getPerson(),  reportList.getPaymentMonth(), reportList.getGrossIncome(),
						reportList.getIncomeTax(), reportList.getNetIncome(), reportList.getSuperannuation());
			}
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
                e.printStackTrace();
			}
		}
	}
}
