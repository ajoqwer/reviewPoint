package com.example.demo.review;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
			if(reviewMapper.cntPlaceUserReview(vo) == 0) point += 1;
			//리뷰 등록
			reviewMapper.addReview(vo);
			// 포인트 관련구간
			int asisPoint = pointMapper.getUserPoint(vo.getUserId());
			if(asisPoint != 0) point += asisPoint;
			vo.setPoint(point);
			pointMapper.mergePoint(vo);
			resultMap.put("userPoint", pointMapper.getUserPoint(vo.getUserId()));
			pointHistMapper.addPointHist(vo);
			// 포인트 관련구간 끝
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("resultCode", Static.RESULT_ERROR_DB);
			return resultMap;
		}
		resultMap.put("resultCode", Static.RESULT_SUCCESS);
		return resultMap;
	}
	
	
	
	
	public HashMap<String,Object> modReview(ReviewVO vo) {
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		int point = 0;
		if(pkNullCheck(vo)) {
			resultMap.put("resultCode", Static.RESULT_ERROR_PARAM);
			return resultMap;
		}
		try {
			ReviewVO preReview = reviewMapper.getPreReview(vo);
			
			if(preReview.getContent().length() == 0 && vo.getContent().length() > 0) point += 1;
			if(preReview.getContent().length() > 0 && vo.getContent().length() == 0) point -= 1;
			if(preReview.getAttachedPhotoIds().size() == 0 && vo.getAttachedPhotoIds().size() > 0) point += 1;
			if(preReview.getAttachedPhotoIds().size() > 0 && vo.getAttachedPhotoIds().size() == 0) point -= 1;
			//리뷰 수정
			reviewMapper.modReview(vo);
			// 포인트 관련구간
			int asisPoint = pointMapper.getUserPoint(vo.getUserId());
			if(asisPoint != 0) point += asisPoint;
			vo.setPoint(point);
			pointMapper.mergePoint(vo);
			resultMap.put("userPoint", pointMapper.getUserPoint(vo.getUserId()));
			pointHistMapper.addPointHist(vo);
			// 포인트 관련구간 끝
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("resultCode", Static.RESULT_ERROR_DB);
			return resultMap;
		}
		resultMap.put("resultCode", Static.RESULT_SUCCESS);
		return resultMap;
	}
	
	
	
	
	
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
			// 포인트 관련구간
			int asisPoint = pointMapper.getUserPoint(vo.getUserId());
			if(asisPoint != 0) point += asisPoint;
			vo.setPoint(point);
			pointMapper.mergePoint(vo);
			resultMap.put("userPoint", pointMapper.getUserPoint(vo.getUserId()));
			pointHistMapper.addPointHist(vo);
			// 포인트 관련구간 끝
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("resultCode", Static.RESULT_ERROR_DB);
			return resultMap;
		}
		resultMap.put("resultCode", Static.RESULT_SUCCESS);
		return resultMap;
	}
	
	
	
	
	
	boolean pkNullCheck(ReviewVO vo) {
		boolean checker = true;
		if(vo.getUserId() == null || vo.getPlaceId() == null || vo.getReviewId() == null) checker = false;
		return  checker;
	}

}
