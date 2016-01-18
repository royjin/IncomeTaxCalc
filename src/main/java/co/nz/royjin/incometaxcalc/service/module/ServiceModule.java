package co.nz.royjin.incometaxcalc.service.module;

import com.google.inject.AbstractModule;

import co.nz.royjin.incometaxcalc.service.config.AppConfigService;
import co.nz.royjin.incometaxcalc.service.config.AppConfigServiceImpl;
import co.nz.royjin.incometaxcalc.service.report.IncomeTaxReportService;
import co.nz.royjin.incometaxcalc.service.report.MonthRangeService;
import co.nz.royjin.incometaxcalc.service.report.MonthRangeServiceImpl;
import co.nz.royjin.incometaxcalc.service.report.ReportFactory;
import co.nz.royjin.incometaxcalc.service.report.CSVIncomeTaxReportServiceImpl;
import co.nz.royjin.incometaxcalc.service.tax.IncomeTaxCalculationService;
import co.nz.royjin.incometaxcalc.service.tax.IncomeTaxCalculationServiceImpl;
import co.nz.royjin.incometaxcalc.service.tax.rule.AustraliaIncomeTaxRuleStrategyImpl;
import co.nz.royjin.incometaxcalc.service.tax.rule.IncomeTaxRuleStrategy;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		
		bind(ReportFactory.class);
		
		bind(AppConfigService.class).to(AppConfigServiceImpl.class);
		bind(IncomeTaxReportService.class).to(CSVIncomeTaxReportServiceImpl.class);
		bind(IncomeTaxRuleStrategy.class).to(AustraliaIncomeTaxRuleStrategyImpl.class);
		bind(IncomeTaxCalculationService.class).to(IncomeTaxCalculationServiceImpl.class);
		bind(MonthRangeService.class).to(MonthRangeServiceImpl.class);
	}

}
