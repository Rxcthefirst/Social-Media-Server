package com.revature.dtos;

import com.revature.models.Profile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GeneralInformationDTO {
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phoneNumber;
    private String dob;

    public GeneralInformationDTO (Profile profile) {
        this.setFirstName(profile.getOwner().getFirstName());
        this.setLastName(profile.getOwner().getLastName());
        this.setEmail(profile.getOwner().getEmail());
        this.setDob(profile.getDob());
        this.setPhoneNumber(profile.getPhoneNumber());
        this.setGender(profile.getGender());
    }
}
