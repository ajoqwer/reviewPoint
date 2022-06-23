package com.example.demo.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ReviewController {

	@Autowired
	ReviewService reviewService;
	
}
