package com.robin.yelpreview.enums;

public enum Constants {
    BEARER_AUTH("AvJaJWdL_QoNB9OeWd8mXt1ioXiEg-2y5ozm2OLIcm01mqIugrBM0Elyhf6p7h1ZjwgoyQkaUMN0zmItQlC6e-pFWSpS3efhev425jb7NhgRJ8o9ZylCG1Nl_9G-YHYx"),
    URI_BASE("https://api.yelp.com/v3");

  private final String value;

  Constants(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
