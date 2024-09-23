package com.comestic.shop.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeocodingService {


    private final RestTemplate restTemplate;
    private final String apiKey = "YOUR_API_KEY";
    private final String geocodingUrl = "https://maps.googleapis.com/maps/api/geocode/json?address={address}&key={apiKey}";

    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Coordinates getCoordinates(String address) {
        Map<String, String> params = new HashMap<>();
        params.put("address", address);
        params.put("apiKey", apiKey);

        ResponseEntity<GeocodingResponse> response = restTemplate.getForEntity(geocodingUrl, GeocodingResponse.class, params);
        GeocodingResponse body = response.getBody();

        if (body != null && body.getResults().size() > 0) {
            Location location = body.getResults().get(0).getGeometry().getLocation();
            return new Coordinates(location.getLat(), location.getLng());
        }

        throw new RuntimeException("Unable to geocode address: " + address);
    }
}

class GeocodingResponse {
    private List<GeocodingResult> results;

    public List<GeocodingResult> getResults() {
        return results;
    }

    public void setResults(List<GeocodingResult> results) {
        this.results = results;
    }
}

class GeocodingResult {
    private Geometry geometry;

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}

class Geometry {
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

class Location {
    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}

class Coordinates {
    private double latitude;
    private double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

@Configuration
class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}