package com.report;

import net.sf.jasperreports.engine.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
public class SpringBootJasperReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJasperReportApplication.class, args);
	}


	@Bean
	CommandLineRunner init(){
		return args -> {
			String destinationPath = "src" + File.separator +
									 "main" + File.separator +
								     "resources" + File.separator +
								     "static" + File.separator +
									 "reportGenerate.pdf";

			String filePath = "src" + File.separator +
					"main" + File.separator +
					"resources" + File.separator +
					"templates" + File.separator +
					"report" + File.separator +
					"Report.jrxml";

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("VOUCHER_ID", "0000001234");
			parameters.put("CURRENT_DATE", getDateTime("dd-MM-yyyy"));
			parameters.put("CURRENT_HOUR", getDateTime("HH:mm:ss"));
			parameters.put("PAYMENT_VALUE", new BigDecimal(5000));
			parameters.put("HALF_PAYMENT", "Cash");
			parameters.put("GUARDIAN_NAME", "Gerson Benito");
			parameters.put("STUDENT_NAME", "Pepe Grillo");
			parameters.put("ELECTRONIC_SIGNATURE_NAME", "Gerson Deodanes Benito");
			parameters.put("IMAGE_DIR", "classpath:/static/images/");

			JasperReport report = JasperCompileManager.compileReport(filePath);
			JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(print, destinationPath);
			System.out.println("Report created successfully");
		};
	}

	static String getDateTime(String mask){
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(mask);
		return formatter.format(localDateTime);
	}
}
