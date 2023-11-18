package com.angel.api.practice.dao;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
        private String clientCode;

        private String name;

        private String email;

        private String mobileNo;

        private String exchanges;

        private String products;

        private String lastLoginTime;

        private String brokerId;

}
