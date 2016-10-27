package com.cleansweep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cleansweep.dataobjects.EmptyMeIndicator;

public class EmptyMeIndicatorTest {

	@Test
	public void testEmptyMeIndicator() {
		EmptyMeIndicator em = new EmptyMeIndicator();
		assertNotNull(em);
		assertTrue(em.getEmptyMeIndicator() == false);
	}

	@Test
	public void testTurnOnEmptyMeIndicator() {
		EmptyMeIndicator em = new EmptyMeIndicator();
		em.turnOnEmptyMeIndicator();
		assertTrue(em.getEmptyMeIndicator() == true);
	}

	@Test
	public void testTurnOffEmptyMeIndicator() {
		EmptyMeIndicator em = new EmptyMeIndicator();
		em.turnOffEmptyMeIndicator();
		assertTrue(em.getEmptyMeIndicator() == false);
	}

	@Test
	public void testGetEmptyMeIndicator() {
		EmptyMeIndicator em = new EmptyMeIndicator();
		em.turnOnEmptyMeIndicator();
		assertTrue(em.getEmptyMeIndicator() == true);
	}

	@Test
	public void testSetEmptyMeIndicator() {
		EmptyMeIndicator em = new EmptyMeIndicator();
		assertTrue(em.getEmptyMeIndicator() == false);
		em.setEmptyMeIndicator(true);
		assertTrue(em.getEmptyMeIndicator() == true);
	}

}
