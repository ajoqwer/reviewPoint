package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.review.ReviewVO;

@Mapper
public interface PointHistMapper {
	public void addPointHist(ReviewVO vo);
	public int getPlaceUserPoint(ReviewVO vo);
}
