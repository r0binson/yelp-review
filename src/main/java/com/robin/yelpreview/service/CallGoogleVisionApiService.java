package com.robin.yelpreview.service;

import com.robin.yelpreview.dto.Emotion;
import com.robin.yelpreview.enums.Constants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CallGoogleVisionApiService {
  private RestTemplate restTemplate;
  private HttpHeaders httpHeaders;
  private HttpEntity request;


  public CallGoogleVisionApiService() {
    this.restTemplate = new RestTemplate();
    this.httpHeaders = new HttpHeaders();
    this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    this.request = new HttpEntity(httpHeaders);
  }

  public Emotion getEmotions(String imageUri) {
    Emotion emotion = new Emotion();

    StringBuilder uri = new StringBuilder(Constants.VISION_API_URI_BASE.getValue())
      .append("?key=")
      .append(Constants.VISION_API_KEY.getValue());
    LinkedHashMap visionApiResponse = getVisionApiResponse(uri, imageUri);
    LinkedHashMap faceAnnotations = getFaceAnnotations(visionApiResponse);

    if (null != faceAnnotations) {
      emotion.setAngerLikelihood((String) faceAnnotations.get("angerLikelihood"));
      emotion.setJoyLikelihood((String)faceAnnotations.get("joyLikelihood"));
      emotion.setSurpriseLikelihood((String) faceAnnotations.get("surpriseLikelihood"));
      emotion.setSorrowLikelihood((String)faceAnnotations.get("sorrowLikelihood"));
    }

    return emotion;
  }

  private LinkedHashMap getFaceAnnotations (LinkedHashMap visionApiResonse) {
    List responses = (List)visionApiResonse.get("responses");
    LinkedHashMap features = (LinkedHashMap)responses.get(0);
    List faceAnnotationsList = (List)features.get("faceAnnotations");

    if(null!=faceAnnotationsList && faceAnnotationsList.size()>0){
      return (LinkedHashMap)faceAnnotationsList.get(0);
    }

    return null;
  }


  public String getVisionApiPostRequestBody(String imageUri){
    return "{\n" +
      "  'requests': [\n" +
      "    {\n" +
      "      'features': [\n" +
      "        {\n" +
      "          'maxResults': 10,\n" +
      "          'type': 'FACE_DETECTION'\n" +
      "        }\n" +
      "      ],\n" +
      "      'image': {\n" +
      "        'source': {\n" +
      "          'imageUri': '" +  imageUri + "'\n" +
      "        }\n" +
      "      }\n" +
      "    }\n" +
      "  ]\n" +
      "}";
  }


  private LinkedHashMap getVisionApiResponse(StringBuilder uri, String imageUrl) {
    HttpEntity<String> entity = new HttpEntity<String>(getVisionApiPostRequestBody(imageUrl), this.httpHeaders);
    LinkedHashMap response = (LinkedHashMap) restTemplate.postForObject(uri.toString(), entity, LinkedHashMap.class);
    return response;
  }

}
