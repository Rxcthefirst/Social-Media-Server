package com.revature.controllers;

import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.exceptions.NotUsersFoundException;
import com.revature.models.Message;
import com.revature.models.Profile;
import com.revature.services.SearchService;

@RestController
@RequestMapping("/search")
@CrossOrigin(origins = {"http://localhost:4200","http://52.37.182.192:4200"}, allowCredentials = "true")
@AllowSysOut
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/profiles")
    public ResponseEntity<Object> getProfiles(@RequestParam(name = "limit", required = false) String limit) {
        try {
            // If limit provided, it is being used. Otherwise... what about 12?
            long limitResponseLength = limit != null? Long.parseLong(limit) : 12;

            List<Profile> profiles = searchService.getProfiles(limitResponseLength);
            return ResponseEntity.ok(profiles);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(new Message<>("Wrong format of the query. The limit parameter should be a number.", null));
        }
    }

    
    @GetMapping("/specific-profiles")
    public ResponseEntity<Object> getSpecificProfiles(@RequestParam(name = "firstName") String firstName,
                                                      @RequestParam(name = "lastName") String lastName) 
    {
        try {
            List<Profile> profiles = searchService.getSpecificProfiles(firstName, lastName);
            return ResponseEntity.ok(profiles);
        } catch (NotUsersFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
