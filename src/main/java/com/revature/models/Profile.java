package com.revature.models;

import java.util.ArrayDeque;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profiles")
public class Profile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String backgroundImageUrl = "https://fthmb.tqn.com/vMHG2Hi44XBqddh93WTo3nkWESU=/5000x3000/filters:fill(auto,1)/low-poly-background-672623312-5a5a8563e258f800370a105a.jpg";

    private String currentCity;
    private String currentCountry;

    private String bornCity;
    private String bornCountry;

    private String dob;
    private String gender;
    private String maritalStatus;

    private String schoolName;

    private String jobTitle;

    private String companyName;
    private String companyUrl;

    private String phoneNumber;

    /**
     * Represents IDs of other users to which this user is subscribed.
     */
    @Column(length = 4_000)
    private LinkedList<Integer> subscriptionIds = new LinkedList<>();

    @Column(length = 4_000)
    private ArrayDeque<String> photoUrls = new ArrayDeque<>();


    @OneToOne
	private User owner;

    public Profile(int id, String backgroundImageUrl, String currentCity, String currentCountry, String bornCity,
            String bornCountry, String dob, String gender, String maritalStatus, String schoolName, String jobTitle,
            String companyName, String companyUrl, String phoneNumber, User owner) {
        this.id = id;
        this.backgroundImageUrl = backgroundImageUrl;
        this.currentCity = currentCity;
        this.currentCountry = currentCountry;
        this.bornCity = bornCity;
        this.bornCountry = bornCountry;
        this.dob = dob;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.schoolName = schoolName;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.companyUrl = companyUrl;
        this.phoneNumber = phoneNumber;
        this.owner = owner;
    }
}
