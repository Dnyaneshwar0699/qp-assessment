package com.qb.assessment.grocery.booking.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseBodyListDto<T> {
	
	private ResponseObjectDto status;
	List<T> responseList = new ArrayList<>();

		public ResponseBodyListDto(ResponseObjectDto status, List<T> responseList) {
			super();
			this.status = status;
			this.responseList = responseList;
		}
		
		/**
		* ResponseBodyListDto providing the response object with status as constructor.
		* @param status status of the response with http code.
		* @param responseList response list.
		*/

		
		public ResponseObjectDto getStatus() {
			return status;
		}

		public void setStatus(ResponseObjectDto status) {
			this. status = status;
		}
		
		public List<T> getResponseList() {
			return responseList;
		}
		
		public void setResponseList(List<T> object) {
			this.responseList = object;

}
}
