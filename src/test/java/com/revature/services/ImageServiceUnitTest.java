package com.revature.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.dtos.ImagePostDTO;
import com.revature.exceptions.ImageNotFoundException;
import com.revature.models.Image;
import com.revature.repositories.ImageRepository;

@ExtendWith(SpringExtension.class)
class ImageServiceUnitTest {

	@Mock
	private ImageRepository imageRepository;
	@Mock
	private ImagePostDTO imagePostDTO;
	
	@InjectMocks
	ImageService imageService;
	
	@Test
	public void getImageResult() {
		int id = 0;
		Image image = new Image(0, "", "");
		when(imageRepository.findById(id)).thenReturn(Optional.of(image));
		try {
			Image result = imageService.getImage(id);
			assertNotNull(result);
		} catch(ImageNotFoundException e) {
			e.printStackTrace();
            fail();
		}
	}
	
	@Test
	public void getImageNoImageFound() {
		int id = 0;
		when(imageRepository.findById(id)).thenReturn(Optional.empty());
		try {
			imageService.getImage(id);
			fail();
		} catch(ImageNotFoundException e) {
			e.printStackTrace();
            return;
		}
	}
	
	@Test
	public void updateImageResult() {
		int id = 0;
		Image image = new Image(0, "", "");
		when(imageRepository.findById(id)).thenReturn(Optional.of(image));
		Image result = imageService.uploadImage(imagePostDTO);
		assertNull(result);
	}
	
	
}
