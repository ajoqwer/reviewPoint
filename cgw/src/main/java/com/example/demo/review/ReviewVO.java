package com.example.demo.review;

import java.util.List;

import lombok.Data;

@Data
public class ReviewVO {

	String reviewId;
	String userId;
	String placeId;
	List<String> attachedPhotoIds;
	String content;
	int point;
	
}
