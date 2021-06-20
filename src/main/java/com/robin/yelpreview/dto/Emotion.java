package com.robin.yelpreview.dto;

import lombok.Data;

@Data
public class Emotion {
  private String joyLikelihood;
  private String sorrowLikelihood;
  private String angerLikelihood;
  private String surpriseLikelihood;

}
