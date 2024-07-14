package com.samproj.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samproj.binding.DashboardResponse;
import com.samproj.binding.EnquiryForm;
import com.samproj.entity.CourseEntity;
import com.samproj.entity.EnqStatusEntity;
import com.samproj.entity.StudentEnqEntity;
import com.samproj.entity.UserDtlsEntity;
import com.samproj.repo.CourseRepo;
import com.samproj.repo.EnqStatusRepo;
import com.samproj.repo.StudentEnqRepo;
import com.samproj.repo.UserDtlsRepo;

import jakarta.servlet.http.HttpSession;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private StudentEnqRepo enqRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo statusRepo;
	
	@Autowired
	private HttpSession session;

	@Override
	public DashboardResponse getDashboardData(Integer userId) {

		DashboardResponse response = new DashboardResponse();

		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);

		if (findById.isPresent()) {
			UserDtlsEntity userEntity = findById.get();

			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();
			Integer totalCnt = enquiries.size();
			
			Integer enreolledCntNew = enquiries.stream().filter(e -> e.getEnqStatus().equals("New"))
					.collect(Collectors.toList()).size();

			Integer enreolledCnt = enquiries.stream().filter(e -> e.getEnqStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			Integer lostCnt = enquiries.stream().filter(e -> e.getEnqStatus().equals("Lost"))
					.collect(Collectors.toList()).size();

			response.setTotalEnquriesCnt(totalCnt);
			response.setEnrolledCnt(enreolledCntNew);
			response.setEnrolledCnt(enreolledCnt);
			response.setLostCnt(lostCnt);

		}

		return response;
	}
	
	
	      @Override
	    public List<String> getCourses() {
	    	  
	    	  List<CourseEntity> findAll = courseRepo.findAll();
	    	  
	    	  List<String> names = new ArrayList();
	    	  
	    	  for(CourseEntity entity : findAll) {
	    		  names.add(entity.getCourseName());
	    	  }
	    	  
	    	return names;
	    }
	      
	      @Override
	    public List<String> getEnqStatuses() {
	    	  
	    	List<EnqStatusEntity> findAll = statusRepo.findAll();  
	    	
	    	List<String> statusList = new ArrayList<>();
	    	
	    	for(EnqStatusEntity entity : findAll) {
	    		statusList.add(entity.getStatusName());
	    	}
	    	  return statusList;
	    }
	
	
	@Override
	public boolean saveEnquriry(EnquiryForm form) {
		
		StudentEnqEntity enqEntity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, enqEntity);
		
		Integer userId =(Integer) session.getAttribute("userId");
		
		UserDtlsEntity userEntity = userDtlsRepo.findById(userId).get();
		enqEntity.setUser(userEntity);
		
		enqRepo.save(enqEntity);
		
		return true;
	}
	
	
	@Override
	public List<StudentEnqEntity> getEnquiries() {
		
		Integer userId = (Integer)session.getAttribute("userId");
		 Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		 if(findById.isPresent()) {
			 UserDtlsEntity userDtlsEntity = findById.get();
			 List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			 return enquiries;
		 }
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
