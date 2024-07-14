package com.samproj.service;


import java.util.List;

import com.samproj.binding.DashboardResponse;
import com.samproj.binding.EnquiryForm;
import com.samproj.entity.StudentEnqEntity;

public interface EnquiryService {

	public DashboardResponse getDashboardData(Integer userId) ;
	
	public List<String> getCourses();
	
	public List<String> getEnqStatuses();
	
	public boolean saveEnquriry(EnquiryForm form);
	
	public List<StudentEnqEntity> getEnquiries();

}
