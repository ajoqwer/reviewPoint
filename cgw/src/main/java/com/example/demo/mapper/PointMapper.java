package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.review.ReviewVO;

@Mapper
public interface PointMapper {
	public ReviewVO getUserPoint(String userId);
	public void mergePoint(ReviewVO vo);
	
}
