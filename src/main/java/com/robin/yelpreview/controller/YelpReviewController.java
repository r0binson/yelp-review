package com.robin.yelpreview.controller;

import com.robin.yelpreview.dto.BusinessDTO;
import com.robin.yelpreview.dto.Review;
import com.robin.yelpreview.dto.User;
import com.robin.yelpreview.service.CallYelpService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/yelp-review")
public class YelpReviewController {
  private final CallYelpService callYelpService;


  @GetMapping("/search-business/{location}")
  @ApiOperation(value = "Returns a List of Businesses")
  public ResponseEntity findBusiness(@PathVariable String location) {
    List<Map> businesses = callYelpService.getBusinessList(location);
    List<BusinessDTO> listOfBusinesses = businesses.stream().map(
      biz -> {
        BusinessDTO businessDTO = new BusinessDTO();
        businessDTO.setId((String) biz.get("id"));
        businessDTO.setAlias((String) biz.get("alias"));
        businessDTO.setReviewCount((Integer)biz.get("review_count"));
        return businessDTO;
    }).collect(Collectors.toList());

    return ResponseEntity.ok(listOfBusinesses);
  }


  @GetMapping("/reviews/{businessId}")
  @ApiOperation(value = "Returns a List of reviews for the biz")
  public ResponseEntity getReviews(@PathVariable String businessId) {
    List<Map> reviewsRaw = callYelpService.getReviews(businessId);
    List<Review> reviews = reviewsRaw.stream().map(rev -> {
      Map usr = (Map)rev.get("user");
      User user = new User();
      user.setId((String) usr.get("id"));
      user.setName((String) usr.get("name"));
      user.setImageUrl((String) usr.get("image_url"));
      user.setProfileUrl((String) usr.get("profile_url"));
      Review review = new Review();
      review.setUser(user);
      review.setReview((String) rev.get("text"));
      return review;
    }).collect(Collectors.toList());
    return ResponseEntity.ok(reviews);
  }
}
