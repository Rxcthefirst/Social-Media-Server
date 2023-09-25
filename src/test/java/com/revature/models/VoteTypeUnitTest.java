package com.revature.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.exceptions.VoteNotFoundException;

@ExtendWith(SpringExtension.class)
class VoteTypeUnitTest {

	@Test
	public void testVoteLookupNoVote() {
		try {
			VoteType.lookup(-1);
			fail("Found a vote");
		}
		catch(VoteNotFoundException e)
		{
			e.printStackTrace();
            return;
		}
	}

}
