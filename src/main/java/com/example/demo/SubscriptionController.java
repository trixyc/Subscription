package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class SubscriptionController {

	@GetMapping("/selection")
	public String selection() {
		return "selection";
	}
	
	@PostMapping("/selection")
	public String selection(
	@RequestParam(name="amount") String amount,
	@RequestParam(name="subType") String subType,
	@RequestParam(name="week") String week,
	@RequestParam(name="month") String month,
	@RequestParam(name="startDate") String startDate,
	@RequestParam(name="endDate") String endDate,
	Model model) {
		System.out.println("amount " + amount);
		System.out.println("subType: "+subType);
		System.out.println("week: "+week);
		System.out.println("month: "+month);
		System.out.println("startDate: "+startDate.toString());
		System.out.println("endDate: "+endDate.toString());
		Date sDate, eDate;
		List<String> resultDates =  new ArrayList();
		
		try {
			    sDate = new SimpleDateFormat("ddMMyyyy").parse(startDate);
			    eDate = new SimpleDateFormat("ddMMyyyy").parse(endDate);
			    
			    if(subType.equals("W")){

					Calendar cal = Calendar.getInstance();
					cal.setTime(sDate);
			    	
			    	LocalDate localStart = LocalDate.parse(startDate);
			    	LocalDate localEnd = LocalDate.parse(endDate);
					LocalDate day = localStart.with(TemporalAdjusters.next(DayOfWeek.of(Integer.parseInt(week))));
					
					do {
			            // Loop to get every Sunday by adding Period.ofDays(7) the the current Sunday.
			            System.out.println(day.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			            resultDates.add(day.toString());
			            day = day.plus(Period.ofDays(7));
			        } while (day.isBefore(localEnd));
				
				} else if(subType.equals("M")){
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(sDate);
			    	
			    	LocalDate localStart = LocalDate.parse(startDate);
			    	LocalDate localEnd = LocalDate.parse(endDate);
					LocalDate day = localStart.withDayOfMonth(Integer.parseInt(month));
					
					do {
			            // Loop to get every Sunday by adding Period.ofDays(7) the the current Sunday.
			            System.out.println(day.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			            resultDates.add(day.toString());
			            day = day.plus(Period.ofMonths(1));
			        } while (day.isBefore(localEnd));
				
				} else{
				}
			    
				model.addAttribute("amount",amount );
				model.addAttribute("subType",subType );
				model.addAttribute("sDate",sDate );
				model.addAttribute("eDate",eDate );
				model.addAttribute("resultDates",resultDates );
System.out.println("===============================================================================================================================");
			} catch (Exception e) {
			    e.printStackTrace();
		}
		return "result";
	}

}