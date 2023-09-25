package com.revature.controllers;

import java.util.Base64;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.dtos.ImagePostDTO;
import com.revature.exceptions.ImageNotFoundException;
import com.revature.models.Image;
import com.revature.services.ImageService;

@RestController
@RequestMapping("/image")
@CrossOrigin(origins = {"http://localhost:4200","http://52.37.182.192:4200"}, allowCredentials = "true")
public class ImageController {


    @Autowired
    private ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int imageId) {
        try {
            Image image = imageService.getImage(imageId);
            byte[] byteImage = image.getContent().getBytes();
            byte[] decodedImage =  Base64.getDecoder().decode(byteImage);
            return ResponseEntity
                        .ok()
                        .contentType(MediaType.valueOf(image.getType()))
                        .body(decodedImage);
        } catch (ImageNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Authorized
    @PostMapping
    public ResponseEntity<?> postImage(@RequestBody ImagePostDTO imagePost, HttpSession httpSession) {
        return ResponseEntity.ok(imageService.uploadImage(imagePost));
    }
    
}
