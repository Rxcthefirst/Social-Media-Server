package com.revature.controllers;

import javax.servlet.http.HttpSession;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.dtos.SubscriptionDTO;
import com.revature.exceptions.NoSuchSubscriberException;
import com.revature.exceptions.ProfileAlreadySubscribedException;
import com.revature.exceptions.ProfileNotFoundException;
import com.revature.exceptions.SelfSubscriptionException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.services.SubscriptionService;

@RestController
@RequestMapping("/subscription")
@CrossOrigin(origins = {"http://localhost:4200","http://52.37.182.192:4200"}, allowCredentials = "true")
@AllowSysOut
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Authorized
    @PatchMapping
    public ResponseEntity<Object> patchSubscription(@RequestBody SubscriptionDTO subscriptionDTO, HttpSession session) {
        User user = (User) session.getAttribute("user");

        try {
            return ResponseEntity.ok(subscriptionService.patchSubscription(subscriptionDTO, user));
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ProfileAlreadySubscribedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SelfSubscriptionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @Authorized
    @PatchMapping("/unsubscribe")
    public ResponseEntity<Object> patchUnsubscribe(@RequestBody SubscriptionDTO subscriptionDTO, HttpSession session) {
        User user = (User) session.getAttribute("user");

        try {
            return ResponseEntity.ok(subscriptionService.patchUnsubscribe(subscriptionDTO, user));
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchSubscriberException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
