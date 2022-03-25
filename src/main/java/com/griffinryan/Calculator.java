package com.griffinryan;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.File;

public class Calculator implements ActionListener {
	double number;
	double answer;
	int calculation;
	PrintStream log = null;

	final JFrame frame;
	final JPanel panel;
	final JLabel label = new JLabel();
	final JTextField textField = new JTextField();
	final JButton buttonZero = new JButton("0");
	final JButton buttonOne = new JButton("1");
	final JButton buttonTwo = new JButton("2");
	final JButton buttonThree = new JButton("3");
	final JButton buttonFour = new JButton("4");
	final JButton buttonFive = new JButton("5");
	final JButton buttonSix = new JButton("6");
	final JButton buttonSeven = new JButton("7");
	final JButton buttonEight = new JButton("8");
	final JButton buttonNine = new JButton("9");
	final JButton buttonDot = new JButton(".");

	final JButton buttonClear = new JButton("C");
	final JButton buttonDelete = new JButton("DEL");
	final JButton buttonEqual = new JButton("=");
	final JButton buttonMul = new JButton("x");
	final JButton buttonDiv = new JButton("/");
	final JButton buttonPlus = new JButton("+");
	final JButton buttonMinus = new JButton("-");
	final JButton buttonSquare = new JButton("x\u00B2");
	final JButton buttonReciprocal = new JButton("1/x");
	final JButton buttonSqrt = new JButton("\u221A");
	final JRadioButton intRadioButton = new JRadioButton("int");
	final JRadioButton floatRadioButton = new JRadioButton("float");

	Calculator() {
		// Make a frame and configure it.
		frame = new JFrame();
		frame.setSize(350, 500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.BLACK);
		frame.setTitle("GUI Calculator");

		// Make a panel to add components in. Set layout to BorderLayout.
		panel = new JPanel();
		panel.setLayout(new BorderLayout());

		frame.add(panel);

		// gridlayout for output
		JPanel outPanel = new JPanel();
		outPanel.setBackground(Color.BLACK);
		outPanel.setLayout(new GridLayout(2,2));

		// Make new components for adding to panel.
		textField.setFont(new Font("Arial", Font.BOLD, 20));
		textField.setEditable(false);
		textField.setHorizontalAlignment(SwingConstants.RIGHT);

		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setForeground(Color.white);
		outPanel.add(label);
		outPanel.add(textField);

		panel.add(outPanel, BorderLayout.NORTH);

		// make a 'button panel' and configure it.
		var buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.BLACK);
		buttonPanel.setLayout(new GridLayout(6, 3, 10, 10));
		panel.add(buttonPanel, BorderLayout.CENTER);

		// Initialize and add button components in a loop.
		Font fontButton = new Font("Arial", Font.PLAIN, 20);
		buttonSeven.setFont(fontButton);
		buttonEight.setFont(fontButton);
		buttonNine.setFont(fontButton);
		buttonFour.setFont(fontButton);
		buttonFive.setFont(fontButton);
		buttonSix.setFont(fontButton);
		buttonOne.setFont(fontButton);
		buttonTwo.setFont(fontButton);
		buttonThree.setFont(fontButton);
		buttonDot.setFont(fontButton);
		buttonZero.setFont(fontButton);

		var radGroup = new JPanel();
		radGroup.setLayout(new GridLayout(2, 1));
		radGroup.setBackground(Color.BLACK);
		radGroup.add(floatRadioButton);
		radGroup.add(intRadioButton);
		buttonPanel.add(radGroup);
		//outPanel.add(floatRadioButton);
		//outPanel.add(intRadioButton);
		buttonPanel.add(buttonClear);
		buttonPanel.add(buttonDelete);

		buttonPanel.add(buttonReciprocal);
		buttonPanel.add(buttonSqrt);
		buttonPanel.add(buttonSquare);

		buttonPanel.add(buttonSeven);
		buttonPanel.add(buttonEight);
		buttonPanel.add(buttonNine);
		buttonPanel.add(buttonFour);
		buttonPanel.add(buttonFive);
		buttonPanel.add(buttonSix);
		buttonPanel.add(buttonOne);
		buttonPanel.add(buttonTwo);
		buttonPanel.add(buttonThree);
		buttonPanel.add(buttonZero);
		buttonPanel.add(buttonDot);

		/*
		JPanel finalPanel = new JPanel();
		finalPanel.setBackground(Color.BLACK);
		finalPanel.setLayout(new GridLayout(2,1));
		buttonPanel.add(finalPanel, BorderLayout.SOUTH);
		buttonClear.setForeground(Color.RED);
		buttonDelete.setForeground(Color.RED);
		finalPanel.add(buttonClear);
		finalPanel.add(buttonDelete);
		*/
		buttonEqual.setBackground(Color.ORANGE);
		buttonEqual.setForeground(Color.WHITE);
		buttonEqual.setOpaque(true);
		buttonEqual.setBorderPainted(true);

		// Make operationPanel and configure it.
		var operationPanel = new JPanel();
		operationPanel.setBackground(Color.darkGray);

		operationPanel.setLayout(new BorderLayout());
		var ePanel = new JPanel();
		ePanel.setLayout(new GridLayout(4,1));

		panel.add(operationPanel, BorderLayout.EAST);

		// Set operation button colors.
		setButtonColor(fontButton);
		intRadioButton.setForeground(Color.WHITE);
		floatRadioButton.setForeground(Color.WHITE);
		intRadioButton.setSelected(false);
		floatRadioButton.setSelected(true);

		// set Equals size
		Dimension highDim = new Dimension(40,160);
		Dimension otherDim = new Dimension(40, 60);
		buttonEqual.setPreferredSize(highDim);

		var sizePanel = new JPanel();
		sizePanel.setLayout(new GridLayout(4,1));
		buttonDiv.setPreferredSize(otherDim);
		sizePanel.add(buttonDiv);
		sizePanel.add(buttonMinus);
		sizePanel.add(buttonMul);
		sizePanel.add(buttonPlus);
		operationPanel.add(sizePanel, BorderLayout.NORTH);
		operationPanel.add(buttonEqual, BorderLayout.SOUTH);

		// Add ActionEvent
		addActionEvent();

		// Setup PrintStream for the log file.
		try {
			File logfile = new File("log.txt");
			if(!logfile.exists()){
				logfile.createNewFile();
			}
			log = new PrintStream(logfile);

		} catch (FileNotFoundException ex) {
			System.out.println("Cannot find/create log file!");
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		assert log != null;
		log.println("Beginning of calculator session:");

		// Make the window visible to user.
		frame.setVisible(true);
	}

	public void setButtonColor(Font fontButton){
		try {
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
		} catch (Exception e) {
			e.printStackTrace();
		}

		buttonPlus.setBackground(Color.ORANGE);
		buttonPlus.setForeground(Color.WHITE);
		buttonPlus.setOpaque(true);
		buttonPlus.setBorderPainted(true);
		buttonPlus.setFont(fontButton);
		buttonPlus.setPreferredSize(new Dimension(80, 40));

		buttonMinus.setBackground(Color.ORANGE);
		buttonMinus.setForeground(Color.WHITE);
		buttonMinus.setOpaque(true);
		buttonMinus.setBorderPainted(true);
		buttonMinus.setFont(fontButton);

		buttonMul.setBackground(Color.ORANGE);
		buttonMul.setForeground(Color.WHITE);
		buttonMul.setOpaque(true);
		buttonMul.setBorderPainted(true);
		buttonMul.setFont(fontButton);

		buttonDiv.setBackground(Color.ORANGE);
		buttonDiv.setForeground(Color.WHITE);
		buttonDiv.setOpaque(true);
		buttonDiv.setBorderPainted(true);
		buttonDiv.setFont(fontButton);

		buttonReciprocal.setFont(fontButton);
		buttonSqrt.setFont(fontButton);
		buttonSquare.setFont(fontButton);

		// buttonClear.setBackground(Color.RED);
		// buttonClear.setForeground(Color.WHITE);
		// buttonDelete.setBackground(Color.RED);
		// buttonDelete.setForeground(Color.WHITE);
	}

	public void addActionEvent() {
		// Add an ActionListen to each button.
		buttonClear.addActionListener(this);
		buttonDelete.addActionListener(this);
		buttonDiv.addActionListener(this);
		buttonSqrt.addActionListener(this);
		buttonSquare.addActionListener(this);
		buttonReciprocal.addActionListener(this);
		buttonMinus.addActionListener(this);
		buttonPlus.addActionListener(this);
		buttonMul.addActionListener(this);
		buttonEqual.addActionListener(this);
		buttonSeven.addActionListener(this);
		buttonEight.addActionListener(this);
		buttonNine.addActionListener(this);
		buttonFour.addActionListener(this);
		buttonFive.addActionListener(this);
		buttonSix.addActionListener(this);
		buttonOne.addActionListener(this);
		buttonTwo.addActionListener(this);
		buttonThree.addActionListener(this);
		buttonZero.addActionListener(this);
		buttonDot.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();

		if(source == buttonClear){
			label.setText("");
			textField.setText("");
		} else if(source == buttonDelete){
			int length = textField.getText().length();
			int number = length - 1;

			if(length > 0){
				StringBuilder back = new StringBuilder(textField.getText());
				back.deleteCharAt(number);
				textField.setText(back.toString());
			}
			if(textField.getText().endsWith(" ")){
				label.setText("");
			}
		} else if(source == buttonZero){
			if(textField.getText().equals("0")){
				return;
			} else {
				textField.setText(textField.getText() + "0");
			}
		} else if(source == buttonOne){
			textField.setText(textField.getText() + "1");
		} else if(source == buttonTwo){
			textField.setText(textField.getText() + "2");
		} else if(source == buttonThree){
			textField.setText(textField.getText() + "3");
		} else if(source == buttonFour){
			textField.setText(textField.getText() + "4");
		} else if(source == buttonFive){
			textField.setText(textField.getText() + "5");
		} else if(source == buttonSix){
			textField.setText(textField.getText() + "6");
		} else if(source == buttonSeven){
			textField.setText(textField.getText() + "7");
		} else if(source == buttonEight){
			textField.setText(textField.getText() + "8");
		} else if(source == buttonNine){
			textField.setText(textField.getText() + "9");
		} else if(source == buttonDot){
			if(textField.getText().contains(".")){
				return;
			} else {
				textField.setText(textField.getText() + ".");
			}
		} else if(source == buttonPlus){
			String str = textField.getText();
			number = Double.parseDouble(textField.getText());
			textField.setText("");
			label.setText(str + "+");
			calculation = 1;
		} else if (source == buttonMinus) {
			String str = textField.getText();
			number = Double.parseDouble(textField.getText());
			textField.setText("");
			label.setText(str + "-");
			calculation = 2;
		} else if (source == buttonMul) {
			String str = textField.getText();
			number = Double.parseDouble(textField.getText());
			textField.setText("");
			label.setText(str + "x");
			calculation = 3;
		}else if (source == buttonDiv) {
			String str = textField.getText();
			number = Double.parseDouble(textField.getText());
			textField.setText("");
			label.setText(str + "/");
			calculation = 4;
		} else if (source == buttonSqrt) {
			number = Double.parseDouble(textField.getText());
			double sqrt = Math.sqrt(number);
			textField.setText(Double.toString(sqrt));
			log.println("\n" + "\u221A" + number
					+ Double.parseDouble(textField.getText())
					+ " = " + answer + "\n");
		} else if (source == buttonSquare) {
			String str = textField.getText();
			number = Double.parseDouble(textField.getText());
			double square = Math.pow(number, 2);
			String string = Double.toString(square);
			if (string.endsWith(".0")) {
				textField.setText(string.replace(".0", ""));
			} else {
				textField.setText(string);
			}
			label.setText("(sqr)" + str);
			log.println("\n" + number + "x\u00B2"
					+ " = " + answer + "\n");
		} else if (source == buttonReciprocal) {
			number = Double.parseDouble(textField.getText());
			double reciprocal = 1 / number;
			String string = Double.toString(reciprocal);
			if (string.endsWith(".0")) {
				textField.setText(string.replace(".0", ""));
			} else {
				textField.setText(string);
			}
			log.println("\n" + "1" + " / "
					+ number
					+ " = " + answer + "\n");
		} else if (source == buttonEqual) {
			double operand;
			//Setting functionality for equal(=) button
			switch (calculation) {
				case 1:
					answer = number + Double.parseDouble(textField.getText());
					operand = Double.parseDouble(textField.getText());
					if (Double.toString(answer).endsWith(".0")) {
						textField.setText(Double.toString(answer).replace(".0", ""));
					} else {
						textField.setText(Double.toString(answer));
					}
					log.println("\n" + number + " + "
							+ operand
							+ " = " + answer + "\n");
					label.setText("");
					break;
				case 2:
					answer = number - Double.parseDouble(textField.getText());
					operand = Double.parseDouble(textField.getText());
					if (Double.toString(answer).endsWith(".0")) {
						textField.setText(Double.toString(answer).replace(".0", ""));
					} else {
						textField.setText(Double.toString(answer));
					}
					label.setText("");
					log.println("\n" + number + " - "
							+ operand
							+ " = " + answer + "\n");
					break;
				case 3:
					answer = number * Double.parseDouble(textField.getText());
					operand = Double.parseDouble(textField.getText());
					if (Double.toString(answer).endsWith(".0")) {
						textField.setText(Double.toString(answer).replace(".0", ""));
					} else {
						textField.setText(Double.toString(answer));
					}
					label.setText("");
					log.println("\n" + number + " x "
							+ operand
							+ " = " + answer + "\n");
					break;
				case 4:
					answer = number / Double.parseDouble(textField.getText());
					operand = Double.parseDouble(textField.getText());
					if (Double.toString(answer).endsWith(".0")) {
						textField.setText(Double.toString(answer).replace(".0", ""));
					} else {
						textField.setText(Double.toString(answer));
					}
					if (textField.getText().contains("NaN")){
						textField.setText("Can't divide by 0!");
					}
					label.setText("");
					log.println("\n" + number + " / "
							+ operand
							+ " = " + answer + "\n");
					break;
			}
		}
	}

	public static int intAdd(int a, int b){
		return a + b;
	}

	public static int intSubtract(int a, int b){
		return a - b;
	}

	public static int intDivide(int a, int b){
		return a / b;
	}

	public static int intMultiply(int a, int b){
		return a * b;
	}

	public static float floatAdd(float a, float b){
		return a + b;
	}

	public static float floatSubtract(float a, float b){
		return a - b;
	}

	public static float floatDivide(float a, float b){
		return a / b;
	}

	public static float floatMultiply(float a, float b){
		return a * b;
	}

}
