package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.review.ReviewVO;

@Mapper
public interface ReviewMapper {
	public void addReview(ReviewVO vo);
	public void modReview(ReviewVO vo);
	public void deleteReview(ReviewVO vo);
	
	public int cntPlaceReview(ReviewVO vo);
	public int cntPlaceUserReview(ReviewVO vo);
	public ReviewVO getPreReview(ReviewVO vo);
}
