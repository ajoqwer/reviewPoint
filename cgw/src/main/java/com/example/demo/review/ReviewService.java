package com.example.demo.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
