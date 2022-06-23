package com.example.demo.review;

import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.Static;

@Controller
public class ReviewController {

	@Autowired
	ReviewService reviewService;
	
	@RequestMapping(value="/events",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,Object> getEvent(HttpServletRequest request){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		String type = request.getParameter("type");
		String action = request.getParameter("action");
		
		switch(type) {
			case Static.TYPE_REVIEW :
				switch(action) {
					case Static.ACTION_ADD :
						resultMap = addReview(request);
						break;
					case Static.ACTION_MOD :
						resultMap = modReview(request);
						break;
					case Static.ACTION_DELETE :
						resultMap = deleteReview(request);
						break;
					default : 
						resultMap.put("resultCode", Static.RESULT_ERROR_PARAM);
				}
			break;
			default : 
		}
		return resultMap;
	}
	
	public HashMap<String,Object> addReview(HttpServletRequest request) {
		
		ReviewVO vo = requestMapping(request);
		HashMap<String,Object> resultMap = reviewService.addReview(vo);
		return resultMap;
	}
	public HashMap<String,Object> modReview(HttpServletRequest request) {
		
		ReviewVO vo = requestMapping(request);
		HashMap<String,Object> resultMap = reviewService.modReview(vo);
		return resultMap;
	}
	public HashMap<String,Object> deleteReview(HttpServletRequest request) {
		 
		ReviewVO vo = requestMapping(request);
		HashMap<String,Object> resultMap =reviewService.deleteReview(vo);
		return resultMap;
	}
	
	ReviewVO requestMapping(HttpServletRequest request) {
		ReviewVO vo = new ReviewVO();
		vo.setUserId(request.getParameter("userId"));
		vo.setPlaceId(request.getParameter("placeId"));
		vo.setReviewId(request.getParameter("reviewId"));
		vo.setContent(request.getParameter("content"));
		vo.setAttachedPhotoIds(Arrays.asList(request.getParameter("attachedPhotoIds")));
		return vo;
	}
}
