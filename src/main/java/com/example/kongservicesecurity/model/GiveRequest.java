package com.example.kongservicesecurity.model;

import lombok.Getter;

import java.util.List;

@Getter
public class GiveRequest {
    String user;
    List<String> authority;
}
