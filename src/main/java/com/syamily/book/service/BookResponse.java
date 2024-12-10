package com.syamily.book.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BookResponse {

    @JsonProperty("volumeInfo")
    private VolumeInfo volumeInfo;

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public static class VolumeInfo {

        @JsonProperty("description")
        private String description;

        @JsonProperty("ratingsCount")
        private Integer ratingsCount;

        @JsonProperty("averageRating")
        private Double averageRating;

        @JsonProperty("authors")
        private List<String> authors;

        @JsonProperty("reviews")
        private List<Review> reviews;

        public String getDescription() {
            return description != null ? description : "No description available.";
        }

        public Integer getRatingsCount() {
            return ratingsCount != null ? ratingsCount : 0;
        }

        public Double getAverageRating() {
            return averageRating != null ? averageRating : 0.0;
        }

        public List<String> getAuthors() {
            return authors != null ? authors : List.of("Unknown Author");
        }

        public List<Review> getReviews() {
            return reviews != null ? reviews : List.of(); // Empty list if no reviews
        }
    }

    public static class Review {

        @JsonProperty("snippet")
        private String snippet;

        @JsonProperty("reviewer")
        private String reviewerName;  // Correct property name to match the JSON

        @JsonProperty("rating")
        private Double rating;

        public String getSnippet() {
            return snippet != null ? snippet : "No review snippet available.";
        }

        public String getReviewerName() {  // Change the getter method to match the field name
            return reviewerName != null ? reviewerName : "Anonymous";
        }

        public Double getRating() {
            return rating != null ? rating : 0.0;
        }

		public void setSnippet(String snippet) {
			this.snippet = snippet;
		}

		public void setReviewerName(String reviewerName) {
			this.reviewerName = reviewerName;
		}

		public void setRating(Double rating) {
			this.rating = rating;
		}
    }
}


