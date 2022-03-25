package com.griffinryan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

	public static void main(String[] args) {
		System.out.println("\nLog file of session can be found" +
				"in '/log.txt' file.");
		new Calculator();

		System.out.println("\n\n Session has been terminated.");
		System.out.println("Please see log file for user history of session.");
	}

}
