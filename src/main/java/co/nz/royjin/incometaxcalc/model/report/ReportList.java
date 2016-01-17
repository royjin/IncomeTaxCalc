package co.nz.royjin.incometaxcalc.model.report;

import java.io.Serializable;

import co.nz.royjin.incometaxcalc.model.money.Money;

public class ReportList implements Serializable {

	private static final long serialVersionUID = -1325475030522333260L;
	
	private static final String REPORT_DATE_FORMAT = "dd MMMM YYYY";
	
	private Integer order;
	
	private Person person;
	
	private Money grossIncome;
	
	private Money incomeTax;
	
	private Money netIncome;
	
	private Money superannuation;
	
	private MonthRange monthRange;
	
	public ReportList(Integer order, Person person, Money grossIncome, Money incomeTax, Money netIncome, Money superannuation, MonthRange monthRange) {
		this.order = order;
		this.person = person;
		this.grossIncome = grossIncome;
		this.incomeTax = incomeTax;
		this.netIncome = netIncome;
		this.superannuation = superannuation;
		this.monthRange = monthRange;
	}

	public Integer getOrder() {
		return order;
	}

	public Person getPerson() {
		return person;
	}

	public String getGrossIncome() {
		return grossIncome.format();
	}

	public String getIncomeTax() {
		return incomeTax.format();
	}

	public String getNetIncome() {
		return netIncome.format();
	}

	public String getSuperannuation() {
		return superannuation.format();
	}

	public String getPaymentMonth() {
		return monthRange.getStart().toString(REPORT_DATE_FORMAT) + " - " + monthRange.getEnd().toString(REPORT_DATE_FORMAT);
	}
}
