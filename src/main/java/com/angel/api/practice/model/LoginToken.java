package com.angel.api.practice.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "login_token")
public class LoginToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "jwtToken", columnDefinition = "TEXT")
    private String jwtToken;

    @Column(name = "refreshToken", columnDefinition = "TEXT")
    private String refreshToken;

    @Column(name = "feedToken", columnDefinition = "TEXT")
    private String feedToken;
    
    @OneToOne(cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "login_status_id")
    private LoginStatus loginStatus;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

	
}
