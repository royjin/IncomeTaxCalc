# Description

@Given user provides the monthly annual income list file  
@When IncomeTaxCalc app runs  
@Then users' monthly tax report will be generated  

# Requirements and Assumption
* The IncomeTaxCalc app will validate the payment periods in the input CSV file and output the specific year appended with payment periods.
e.g. 
@Given the tax year is 01 July 2012  
@When the report monthly tax period is 01 March - 31 March  
@Then the output monthly tax period is 01 March 2013 - 31 March 2013  

@Given the tax year is 01 July 2012  
@When the report monthly tax period is 01 August - 31 August  
@Then the output monthly tax period is 01 March 2013 - 31 March 2013  

@Given the tax year is 01 July 2012  
@When the report monthly tax period is 01 December - 28 December    
@Then the output is failed due to not valid calendar month period  


# Design
## High level
* This is a command-line app developed by JAVA
* Dependency injection design pattern is the foundation of this app to make sure the requirement can be flexible change with implementation switch, code testable and maintainable. For example, IncomeTaxRuleStrategy is binding with AustraliaIncomeTaxRuleStrategyImpl, which can be switched to NZIncomeTaxRuleStrategyImpl in the future if the requirement was extended.  
* Layer design: service, model and client can be easily to migrate to different system, e.g. command-line app to web app, or web API.  
* Tax rule is configurable in the system properties file with following format. The configuration design is for maintaining and reusing purpose.  
```
tax.year=01 July 2012  
taxtable.level.2012.1=1|0|18200||
taxtable.level.2012.2=2|18201|37000|0.19|
taxtable.level.2012.3=3|37001|80000|0.325|3572
taxtable.level.2012.4=4|80001|180000|0.37|17547
taxtable.level.2012.5=5|180001||0.45|54457 
```
* TDD and BDD development driven: mock the service avoid integration testing and make sure testing as first approach to validate the object oriented design. The unit test was developed upon the Mockito mock test framework and use BDD approach to describe the test cases.  
```
// Given tax rule table provided  
given(taxRuleStrategy.generateIncomeTaxTable()).willReturn(incomeTaxTable);  
// when annual salary is between [0, - 18200]  
Money level1Salary = new Money(15000.00, currency);  
// then incomeTax should be 0.00 (no tax).  
Money incomeTax = incomeTaxCalcService.calculateMonthlyIncomeTax(level1Salary);  
assertEquals(new Money(currency), incomeTax);  
```
![alt tag](https://raw.githubusercontent.com/royjin/IncomeTaxCalc/master/CalculateIncomeTax.png)

## Detail
### Service Relationship
![alt tag](https://raw.githubusercontent.com/royjin/IncomeTaxCalc/master/servicediagram.png)
### Model
![alt tag](https://raw.githubusercontent.com/royjin/IncomeTaxCalc/master/modelrelationship.png)


# Build
## Environment
* Download and install JDK1.8 latest version https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html  
* Download and install Maven https://maven.apache.org/install.html  
* Download and install Eclipse http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/mars1
* Download the source code https://github.com/royjin/IncomeTaxCalc/tree/master  

## Build eclipse project
* Create folder called IncomeTaxCalc and git clone source code to this folder
* Open the command console, change directory to IncomeTaxCalc directory (pom.xml exits), type: mvn eclipse:eclipse  
* Create a workspace folder, Open Eclipse and switch to the new created workspace, then import the generated project IncomeTaxCalc  

## Generate runnable jar file
* Open the command console, under the IncomeTaxCalc (pom.xml exits), type: mvn install
* Go to the target directory under the IncomeTaxCalc, you should see the generated jar file called: incometaxcalc-prod-jar-with-dependencies.jar  


# Run
## CSV file input

* Input file csv file must have header. Salary should be number, super percentage should be number% format, and pay periods format should be 01 March - 31 March. Fields validation was enabled.  
* CSV input file sample is called personSalaryInput.csv located under the root source folder.    

```
FirstName,LastName,AnnualSalary,SuperRate,PaymentMonth  
David,Rudd,60050,9%,01 March - 31 March  
Ryan,Chen,120000,10%,01 December - 31 December  
Roy,Sun,125000,9%,01 April - 30 April  
Michael,Smith,1500,10%,01 February - 28 February  
```

**Make sure input csv file should be the same location with your jar file.**  
**If the input csv file is not appended upon the command line, the default file name called: personSalaryInput.csv and make sure personSalaryInput.csv is under the same location with the jar file.**     


* Run from generated jar based on above steps
* Or simply use the existing jar file under the dist folder. Exiting runnable jar file called: incometaxcalc-prod-jar-with-dependencies.jar and run the following command line:  
```
java -jar incometaxcalc-prod-jar-with-dependencies.jar <input file name.csv>
```

## CSV file ouput
After successfully run the above command, the output file will be generated: incometax_output_datetime.csv. Please see the sample output CSV file in your project directory called: incometax_output_171216231250.csv 

```
Name,Pay Period,Gross Income,Income Tax,Net Income,Super  
David Rudd,01 March 2013 - 31 March 2013,"$5,004.00",$922.00,"$4,082.00",$450.00  
Ryan Chen,01 December 2012 - 31 December 2012,"$10,000.00","$2,696.00","$7,304.00","$1,000.00"  
Roy Sun,01 April 2013 - 30 April 2013,"$10,417.00","$2,850.00","$7,567.00",$938.00  
Michael Smith,01 February 2013 - 28 February 2013,$125.00,$0.00,$125.00,$13.00  
``` 
