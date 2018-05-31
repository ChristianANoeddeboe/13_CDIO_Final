package dto_test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dto.RaavareDTO;

public class TestDTO {
	private RaavareDTO raavareDTO;
	
	@Test
	public void createEmptyRaavareDTO() {
		raavareDTO = new RaavareDTO();
		assertNotNull(raavareDTO);
		
		assertTrue(isLaverandoerNull());
		
		assertTrue(isRaavareNavnNull());
		
		assertTrue(isRaavareIdZero());
	}

	private boolean isLaverandoerNull() {		
		return null == raavareDTO.getLeverandoer();
	}

	private boolean isRaavareNavnNull() {
		return null == raavareDTO.getRaavareNavn();
	}	

	private boolean isRaavareIdZero() {
		return 0 == raavareDTO.getRaavareId();
	}

	
	

}
