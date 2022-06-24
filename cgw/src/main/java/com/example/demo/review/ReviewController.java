package com.example.demo.review;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
	public HashMap<String,Object> getEvent(@RequestBody ReviewVO vo){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		switch(vo.getType()) {
			case Static.TYPE_REVIEW :
				switch(vo.getAction()) {
					case Static.ACTION_ADD :
						resultMap = addReview(vo);
						break;
					case Static.ACTION_MOD :
						resultMap = modReview(vo);
						break;
					case Static.ACTION_DELETE :
						resultMap = deleteReview(vo);
						break;
					default : 
						resultMap.put("resultCode", Static.RESULT_ERROR_PARAM);
				}
			break;
			default : 
		}
		return resultMap;
	}
	
	public HashMap<String,Object> addReview(ReviewVO vo) {
		
		HashMap<String,Object> resultMap = reviewService.addReview(vo);
		return resultMap;
	}
	public HashMap<String,Object> modReview(ReviewVO vo) {
		
		HashMap<String,Object> resultMap = reviewService.modReview(vo);
		return resultMap;
	}
	public HashMap<String,Object> deleteReview(ReviewVO vo) {
		 
		HashMap<String,Object> resultMap =reviewService.deleteReview(vo);
		return resultMap;
	}
	
}
