package com.samproj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.samproj.binding.DashboardResponse;
import com.samproj.binding.EnquiryForm;
import com.samproj.binding.EnquirySearchCriteria;
import com.samproj.entity.StudentEnqEntity;
import com.samproj.service.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	
	@Autowired
	private EnquiryService enqService;

	@Autowired
	private HttpSession session;


	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}


	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		//logic to fetch data for dashboard
		Integer userId = (Integer) session.getAttribute("userId");
		DashboardResponse dashboardData = enqService.getDashboardData(userId);
		
		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";
	}
	
	
	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj, Model model) {
		
		boolean status = enqService.saveEnquriry(formObj);
		
		if(status) {
			model.addAttribute("succMsg", "Enquiry Added Successfully!!!");
		}else {
			model.addAttribute("errMsg", "Problem Occured!!!!");
		}
		
		return "add-enquiry";
	}
	
	

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		
	   initForm(model);

		return "add-enquiry";
	}
	
	
	private void initForm(Model model) {
		
	//get courses for drop down 
		
		List<String> courses = enqService.getCourses();
		
		//get enq status for drop down
		
		List<String> enqStatuses = enqService.getEnqStatuses();
		
		//create binding class obj
		
		EnquiryForm formObj = new EnquiryForm();
		
		//set data in model obj
		
		model.addAttribute("courseNames", courses);
		model.addAttribute("statusNames", enqStatuses);
		model.addAttribute("formObj", formObj);
		
	}
	
	@GetMapping("/enquires")
	public String viewEnquiryPage( Model model) {
		
		initForm(model);
		model.addAttribute("searchForm", new EnquirySearchCriteria());
		List<StudentEnqEntity> enquiries = enqService.getEnquiries();
		model.addAttribute("enquiries", enquiries);
		
		return "view-enquiries";
	}


}
