package com.revature.dtos;

import com.revature.models.Profile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileLocationDTO {
    private String currentCity;
    private String currentCountry;

    private String bornCity;
    private String bornCountry;

    public ProfileLocationDTO (Profile profile) {
        this.setCurrentCity(profile.getCurrentCity());
        this.setCurrentCountry(profile.getCurrentCountry());
        this.setBornCity(profile.getBornCity());
        this.setCurrentCountry(profile.getCurrentCountry());
    }
}
