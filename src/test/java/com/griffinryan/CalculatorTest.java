package com.griffinryan;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

	@Test
	void actionPerformed() {
		assertEquals(10, Calculator.intAdd(5,5));
	}

	@Test
	void intAdd() {
		assertAll(() -> assertEquals(4, Calculator.intAdd(2,2)),
				() -> assertEquals(10, Calculator.intAdd(6,4)),
				() -> assertEquals(10000000, Calculator.intAdd(6000000,4000000)));
	}

	@Test
	void intSubtract() {
		assertAll(() -> assertEquals(2, Calculator.intSubtract(4, 2)),
				() -> assertEquals(16, Calculator.intSubtract(20,4)));
	}

	@Test
	void intDivide() {
		assertAll(() -> assertEquals(4, Calculator.intDivide(40, 10)),
				() -> assertEquals(10, Calculator.intDivide(40,4)));
	}

	@Test
	void intMultiply() {
		assertAll(() -> assertEquals(4, Calculator.intMultiply(2, 2)),
				() -> assertEquals(10, Calculator.intMultiply(5,2)));
	}

	@Test
	void floatAdd() {
		assertAll(() -> assertEquals(4, Calculator.floatAdd(2,2)),
				() -> assertEquals(10, Calculator.floatAdd(6,4)),
				() -> assertEquals(10000000, Calculator.floatAdd(6000000,4000000)));
	}

	@Test
	void floatSubtract() {
		assertAll(() -> assertEquals(2, Calculator.floatSubtract(4, 2)),
				() -> assertEquals(16, Calculator.floatSubtract(20,4)));
	}

	@Test
	void floatDivide() {
		assertAll(() -> assertEquals(4, Calculator.floatDivide(40, 10)),
				() -> assertEquals(10, Calculator.floatDivide(40,4)));
	}

	@Test
	void floatMultiply() {
		assertAll(() -> assertEquals(4, Calculator.floatMultiply(2, 2)),
				() -> assertEquals(10, Calculator.floatMultiply(5,2)));
	}
}
