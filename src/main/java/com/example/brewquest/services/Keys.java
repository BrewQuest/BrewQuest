package com.example.brewquest.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Keys {

    @Value("${mapbox_token}")
    private String MAPBOX_TOKEN;

    public String getMAPBOX_TOKEN(){
        return MAPBOX_TOKEN;
    }

    public void setMAPBOX_KEY(String MAPBOX_TOKEN) {
        this.MAPBOX_TOKEN = MAPBOX_TOKEN;
    }

}
