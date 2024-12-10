package com.syamily.book.dto;

public class ReviewDTO {


	    private String reviewSnippet;
	    private String reviewerName;
	    private Double rating;

	    // Constructor
	    public ReviewDTO(String reviewSnippet, String reviewerName, Double rating) {
	        this.reviewSnippet = reviewSnippet;
	        this.reviewerName = reviewerName;
	        this.rating = rating;
	    }

	    // Getters and Setters
	    public String getReviewSnippet() {
	        return reviewSnippet;
	    }

	    public void setReviewSnippet(String reviewSnippet) {
	        this.reviewSnippet = reviewSnippet;
	    }

	    public String getReviewerName() {
	        return reviewerName;
	    }

	    public void setReviewerName(String reviewerName) {
	        this.reviewerName = reviewerName;
	    }

	    public Double getRating() {
	        return rating;
	    }

	    public void setRating(Double rating) {
	        this.rating = rating;
	    }
	}

	