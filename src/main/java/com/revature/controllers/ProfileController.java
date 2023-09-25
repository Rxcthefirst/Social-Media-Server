package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.dtos.ChangePasswordDTO;
import com.revature.dtos.GeneralInformationDTO;
import com.revature.dtos.ImageUrlDTO;
import com.revature.dtos.ProfileEducationDTO;
import com.revature.dtos.ProfileLocationDTO;
import com.revature.dtos.ProfileMaritalStatusDTO;
import com.revature.dtos.ProfileWorkDTO;
import com.revature.exceptions.EmailReservedException;
import com.revature.exceptions.ImageNotFoundException;
import com.revature.exceptions.NoNameException;
import com.revature.exceptions.ProfileNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.WrongIdsFormatException;
import com.revature.exceptions.WrongPasswordException;
import com.revature.models.Message;
import com.revature.models.Profile;
import com.revature.models.User;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.services.ProfileService;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = {"http://localhost:4200","http://52.37.182.192:4200"}, allowCredentials = "true")
@AllowSysOut
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    
    @Authorized
    @GetMapping
    public ResponseEntity<Object> getOwnProfile(HttpSession session) {
        User user = (User) session.getAttribute("user");

        try {            
            return ResponseEntity.ok(profileService.getProfileByUser(user));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProfileNotFoundException e) {
            // If a user exists in one table and does not exist in another, it means it was created manually in data.sql
            return ResponseEntity.ok(profileService.registerProfile(user));
        }  
    }

    
    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProfile(@PathVariable int id) {
        try {
            Profile profile = profileService.getProfile(id);

            return ResponseEntity.ok(profile);
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Authorized
    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, HttpSession session) {
        User user = (User) session.getAttribute("user");

        try {
            user = profileService.changePassword(changePasswordDTO, user);
            session.setAttribute("user", user);
            return ResponseEntity.ok().body(new Message<User>("The password is successfully updated", user));
        } catch (WrongPasswordException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EmailReservedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Authorized
    @GetMapping("/general-information")
    public ResponseEntity<Object> getGeneralInformation(HttpSession session) {
        User user = (User) session.getAttribute("user");

        try {
            GeneralInformationDTO generalInformation = profileService.getGeneralInformation(user);
            return ResponseEntity.ok().body(generalInformation);
        } catch (ProfileNotFoundException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Authorized
    @PostMapping("/general-information")
    public ResponseEntity<Object> updateGeneralInformation(@RequestBody GeneralInformationDTO generalInfo, HttpSession session) {
        User user = (User) session.getAttribute("user");
      
        try {
            Profile profile = profileService.updateGeneralInformation(generalInfo, user);

            session.setAttribute("user", profile.getOwner());
            return ResponseEntity.ok().body(new Message<Profile>("The profile is successfully updated.", profile));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoNameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EmailReservedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Authorized
    @GetMapping("/profile-location")
    public ResponseEntity<Object> getProfileLocation(HttpSession session) {
        User user = (User) session.getAttribute("user");

        try {
            ProfileLocationDTO profileLocation = profileService.getProfileLocation(user);
            return ResponseEntity.ok().body(profileLocation);
        } catch (ProfileNotFoundException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Authorized
    @PostMapping("/profile-location")
    public ResponseEntity<Object> updateProfileLocation(@RequestBody ProfileLocationDTO profileLocation, HttpSession session) {
        User user = (User) session.getAttribute("user");
      
        try {
            Profile profile = profileService.updateProfileLocation(profileLocation, user);

            return ResponseEntity.ok().body(new Message<Profile>("The profile is successfully updated.", profile));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }

    @Authorized
    @GetMapping("/profile-education")
    public ResponseEntity<Object> getProfileEducation(HttpSession session) {
        User user = (User) session.getAttribute("user");

        try {
            ProfileEducationDTO profileLocation = profileService.getProfileEducation(user);

            return ResponseEntity.ok().body(profileLocation);
        } catch (ProfileNotFoundException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Authorized
    @PostMapping("/profile-education")
    public ResponseEntity<Object> updateProfileEducation(@RequestBody ProfileEducationDTO profileLocation, HttpSession session) {
        User user = (User) session.getAttribute("user");
      
        try {
            Profile profile = profileService.updateProfileEducation(profileLocation, user);

            return ResponseEntity.ok().body(new Message<Profile>("The profile is successfully updated.", profile));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }


    @Authorized
    @GetMapping("/profile-work")
    public ResponseEntity<Object> getProfileWork(HttpSession session) {
        User user = (User) session.getAttribute("user");

        try {
            ProfileWorkDTO profileWork = profileService.getProfileWork(user);

            return ResponseEntity.ok().body(profileWork);
        } catch (ProfileNotFoundException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Authorized
    @PostMapping("/profile-work")
    public ResponseEntity<Object> updateProfileWork(@RequestBody ProfileWorkDTO profileLocation, HttpSession session) {
        User user = (User) session.getAttribute("user");
      
        try {
            Profile profile = profileService.updateProfileWork(profileLocation, user);

            return ResponseEntity.ok().body(new Message<Profile>("The profile is successfully updated.", profile));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }

    @Authorized
    @GetMapping("/profile-marital-status")
    public ResponseEntity<Object> getProfileMaritalStatus(HttpSession session) {
        User user = (User) session.getAttribute("user");

        try {
            ProfileMaritalStatusDTO profileMaritalStatus = profileService.getProfileMaritalStatus(user);

            return ResponseEntity.ok().body(profileMaritalStatus);
        } catch (ProfileNotFoundException e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Authorized
    @PostMapping("/profile-marital-status")
    public ResponseEntity<Object> updateProfileMaritalStatus(@RequestBody ProfileMaritalStatusDTO profileLocation, HttpSession session) {
        User user = (User) session.getAttribute("user");
      
        try {
            Profile profile = profileService.updateProfileMaritalStatus(profileLocation, user);

            return ResponseEntity.ok().body(new Message<Profile>("The profile is successfully updated.", profile));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }


    @Authorized
    @PostMapping("/profile-background")
    public ResponseEntity<Object> updateProfileBackground(@RequestBody ImageUrlDTO profileBackground, HttpSession session) {
        User user = (User) session.getAttribute("user");
      
        try {
            System.out.println(profileBackground);
            Profile profile = profileService.updateProfileBackground(profileBackground, user);

            return ResponseEntity.ok().body(new Message<Profile>("The profile is successfully updated.", profile));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }

    
    @Authorized
    @PostMapping("/profile-avatar")
    public ResponseEntity<Object> updateProfileAvatar(@RequestBody ImageUrlDTO avatar, HttpSession session) {
        User user = (User) session.getAttribute("user");
      
        try {
            Profile profile = profileService.updateProfileAvatar(avatar, user);
            User updatedUser = profile.getOwner();

            session.setAttribute("user", updatedUser);

            return ResponseEntity.ok().body(new Message<Profile>("The profile is successfully updated.", profile));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }

    @Authorized
    @GetMapping("/all")
    public ResponseEntity<Object> getAllProfilesByIds(@RequestParam(name = "ids") String ids,
                                                      @RequestParam(name = "limit", required = false) String limit,
                                                      @RequestParam(name = "shuffle", required = false) String shuffle) 
    {
        long parsedLimit;
        boolean parsedShuffle;
        List<Profile> profiles;

        try {
            if (limit != null && shuffle != null) {
                parsedLimit = Long.parseLong(limit);
                parsedShuffle = Boolean.parseBoolean(shuffle);
                profiles = profileService.getAllProfilesByIds(ids, parsedLimit, parsedShuffle);
            } else if (limit != null && shuffle == null) {
                parsedLimit = Long.parseLong(limit);
                profiles = profileService.getAllProfilesByIds(ids, parsedLimit);
            } else 
                profiles = profileService.getAllProfilesByIds(ids);

            return ResponseEntity.ok(profiles);
        } catch (WrongIdsFormatException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(new Message<>("Wrong format of the query. The limit parameter should be a number.", null));
        }
    }


    @Authorized
    @PutMapping("/update-photos")
    public ResponseEntity<Object> updatePhotos(@RequestBody ImageUrlDTO imageUrlDTO, HttpSession session) {
        User user = (User) session.getAttribute("user");
        
        try {
            return ResponseEntity.ok(profileService.updatePhotos(imageUrlDTO, user));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Authorized
    @PatchMapping("/update-photos")
    public ResponseEntity<Object> removePhoto(@RequestBody ImageUrlDTO imageUrlDTO, HttpSession session) {
        User user = (User) session.getAttribute("user");
        
        try {
            return ResponseEntity.ok(profileService.removePhoto(imageUrlDTO, user));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ImageNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
