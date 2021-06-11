package com.robin.yelpreview.service;

import com.robin.yelpreview.enums.Constants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CallYelpService {
  private RestTemplate restTemplate;
  private HttpHeaders httpHeaders;
  private HttpEntity request;

  public CallYelpService(){
    this.restTemplate = new RestTemplate();
    this.httpHeaders = new HttpHeaders();
    this.httpHeaders.setBearerAuth(Constants.BEARER_AUTH.getValue());
    this.request = new HttpEntity(httpHeaders);
  }

  public List<Map> getBusinessList(String location){
    StringBuilder uri = new StringBuilder(Constants.URI_BASE.getValue())
      .append("/businesses")
      .append ("/search?location=")
      .append(location);

    return (List<Map>)getResponseEntity(uri).getBody().get("businesses");
  }

  public List<Map> getReviews(String businessId){
    StringBuilder uri = new StringBuilder(Constants.URI_BASE.getValue())
      .append("/businesses/")
      .append(businessId)
      .append("/reviews");

    return (List<Map>)getResponseEntity(uri).getBody().get("reviews");
  }

  private ResponseEntity<Map> getResponseEntity(StringBuilder uri){
    return restTemplate.exchange(
      uri.toString(),
      HttpMethod.GET,
      request,
      Map.class,
      1
    );
  }
}
