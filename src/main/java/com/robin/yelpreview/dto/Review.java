package com.robin.yelpreview.dto;

import lombok.Data;

@Data
public class Review {
  private User user;
  private String review;
  private Emotion emotion;

}
