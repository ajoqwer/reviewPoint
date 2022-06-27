package com.example.demo.review;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.Static;
import com.example.demo.mapper.PointHistMapper;
import com.example.demo.mapper.PointMapper;
import com.example.demo.mapper.ReviewMapper;

@Service
public class ReviewService {
	
	@Autowired
	ReviewMapper reviewMapper;
	
	@Autowired
	PointMapper pointMapper;
	
	@Autowired
	PointHistMapper pointHistMapper;
	
	@Transactional
	public HashMap<String,Object> addReview(ReviewVO vo) {
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		int point = 0;
		if(pkNullCheck(vo)) {
			resultMap.put("resultCode", Static.RESULT_ERROR_PARAM);
			return resultMap;
		}
		
		try {
			if(vo.getContent().length()>0) point += 1;
			if(vo.getAttachedPhotoIds().size() >0) point += 1;
			if(reviewMapper.cntPlaceReview(vo) == 0) point += 1;

			String convertSt = "";
			if(vo.getAttachedPhotoIds().size() > 0){
				for(String str :vo.getAttachedPhotoIds()) {
					convertSt += str +",";
				}
			}				
			vo.setAttachedPhotoId(convertSt);
			reviewMapper.addReview(vo);
			vo.setPoint(point);
			pointHistMapper.addPointHist(vo);
			int asisPoint = 0;
			ReviewVO pointVO = pointMapper.getUserPoint(vo.getUserId());
			if(pointVO != null){
				asisPoint = pointVO.getPoint();
			}
			if(asisPoint != 0) point += asisPoint;
			vo.setPoint(point);
			pointMapper.mergePoint(vo);
			resultMap.put("userPoint", pointMapper.getUserPoint(vo.getUserId()).getPoint());
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("resultCode", Static.RESULT_ERROR_DB);
			return resultMap;
		}
		resultMap.put("resultCode", Static.RESULT_SUCCESS);
		return resultMap;
	}
	
	
	
	@Transactional
	public HashMap<String,Object> modReview(ReviewVO vo) {
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		int point = 0;
		if(pkNullCheck(vo)) {
			resultMap.put("resultCode", Static.RESULT_ERROR_PARAM);
			return resultMap;
		}
		try {
			ReviewVO preReview = reviewMapper.getPreReview(vo);
			String[] tempPhotoIds = preReview.getAttachedPhotoId().split(",");
			List<String> attachedPhotoIds = Arrays.asList(tempPhotoIds);
			preReview.setAttachedPhotoIds(attachedPhotoIds);

			if(preReview.getContent().length() == 0 && vo.getContent().length() > 0) point += 1;
			if(preReview.getContent().length() > 0 && vo.getContent().length() == 0) point -= 1;
			if(preReview.getAttachedPhotoIds().size() == 0 && vo.getAttachedPhotoIds().size() > 0) point += 1;
			if(preReview.getAttachedPhotoIds().size() > 0 && vo.getAttachedPhotoIds().size() == 0) point -= 1;
			
			String convertSt = "";
			if(vo.getAttachedPhotoIds().size() > 0){
				for(String str :vo.getAttachedPhotoIds()) {
					convertSt += str;
				}
			}				
			vo.setAttachedPhotoId(convertSt);
			reviewMapper.modReview(vo);
			vo.setPoint(point);
			pointHistMapper.addPointHist(vo);
			int asisPoint = 0;
			ReviewVO pointVO = pointMapper.getUserPoint(vo.getUserId());
			if(pointVO != null){
				asisPoint = pointVO.getPoint();
			}
			if(asisPoint != 0) point += asisPoint;
			vo.setPoint(point);
			pointMapper.mergePoint(vo);
			
			resultMap.put("userPoint", pointMapper.getUserPoint(vo.getUserId()).getPoint());
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("resultCode", Static.RESULT_ERROR_DB);
			return resultMap;
		}
		resultMap.put("resultCode", Static.RESULT_SUCCESS);
		return resultMap;
	}
	
	
	
	
	@Transactional
	public HashMap<String,Object> deleteReview(ReviewVO vo) {
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		int point = 0;
		if(pkNullCheck(vo)) {
			resultMap.put("resultCode", Static.RESULT_ERROR_PARAM);
			return resultMap;
		}
		try {
			point -= pointHistMapper.getPlaceUserPoint(vo);
			reviewMapper.deleteReview(vo);
			vo.setPoint(point);
			pointHistMapper.addPointHist(vo);
			int asisPoint = 0;
			ReviewVO pointVO = pointMapper.getUserPoint(vo.getUserId());
			if(pointVO != null){
				asisPoint = pointVO.getPoint();
			}
			if(asisPoint != 0) point += asisPoint;
			vo.setPoint(point);
			pointMapper.mergePoint(vo);
			resultMap.put("userPoint", pointMapper.getUserPoint(vo.getUserId()).getPoint());				
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("resultCode", Static.RESULT_ERROR_DB);
			return resultMap;
		}
		resultMap.put("resultCode", Static.RESULT_SUCCESS);
		return resultMap;
	}
	
	
	
	
	
	boolean pkNullCheck(ReviewVO vo) {
		boolean checker = false;
		if(vo.getUserId() == null || vo.getPlaceId() == null || vo.getReviewId() == null) checker = true;
		return  checker;
	}


}
