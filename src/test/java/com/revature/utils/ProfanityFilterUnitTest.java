package com.revature.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProfanityFilterUnitTest {
	
	
	ProfanityFilter profanityFilter = new ProfanityFilter();
	
	@Test
	public void hasProfanityFalse() {
		String text = "arsenic";
		try{
			boolean value = profanityFilter.hasProfanity(text);
			assertEquals(false,value);
		   }catch (IOException e){
			   e.printStackTrace();
	           fail();
		}
	}
	
	@Test
	public void hasProfanityTrue() {
		String text = "fuck";
		try{
			boolean value = profanityFilter.hasProfanity(text);
			assertEquals(true,value);
		   }catch (IOException e){
			   e.printStackTrace();
	           fail();
		}
	}
	
}
