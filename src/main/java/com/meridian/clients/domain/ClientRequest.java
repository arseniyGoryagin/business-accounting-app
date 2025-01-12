package com.meridian.clients.domain;


import lombok.Data;

@Data
public class ClientRequest {

    private String name;
    private String address;
    private String phoneNumber;
    private String floor;
    private String comment;

}
