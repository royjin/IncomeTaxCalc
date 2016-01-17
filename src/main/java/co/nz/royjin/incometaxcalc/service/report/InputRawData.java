package co.nz.royjin.incometaxcalc.service.report;

public class InputRawData {
	private String firstName;

	private String lastName;

	private String annualIncome;

	private String superRate;

	private String month;

	public InputRawData(String firstName, String lastName, String annualIncome, String superRate, String month) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.annualIncome = annualIncome;
		this.superRate = superRate;
		this.month = month;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAnnualIncome() {
		return annualIncome;
	}

	public String getSuperRate() {
		return superRate;
	}

	public String getPaymentMonth() {
		return month;
	}
}
