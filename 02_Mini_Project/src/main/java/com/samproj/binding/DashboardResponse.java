package com.samproj.binding;

import lombok.Data;

@Data
public class DashboardResponse {

	private Integer totalEnquriesCnt;
	
	private Integer NewEnquiersCnt;

	private Integer enrolledCnt;

	private Integer lostCnt;

}
