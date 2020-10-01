package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Program that implements a calculator with a GUI.
 * 
 * @author staver
 *
 */
public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Calculators screen
	 */
	private JLabel label = getCalcScreen();
	/**
	 * Calculator model
	 */
	private CalcModelImpl calcModel = new CalcModelImpl();
	/**
	 * Button for swapping functions with their inverse.
	 */
	private JCheckBox inverseBtn = new JCheckBox("Inv");

	/**
	 * Calculator constructor - initializes GUI.
	 */
	public Calculator() {
		calcModel.addCalcValueListener(l -> {
			label.setText(l.toString());
		});
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(700, 500);
		initGUI();
	}

	/**
	 * Initializes GUI - adds all buttons and labels to the content pane.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(10));
		cp.add(label, new RCPosition(1, 1));
		cp.add(inverseBtn, new RCPosition(5, 7));

		cp.add(new Btn("7", Util.insertDigit(7)), new RCPosition(2, 3));
		cp.add(new Btn("8", Util.insertDigit(8)), new RCPosition(2, 4));
		cp.add(new Btn("9", Util.insertDigit(9)), new RCPosition(2, 5));
		cp.add(new Btn("4", Util.insertDigit(4)), new RCPosition(3, 3));
		cp.add(new Btn("5", Util.insertDigit(5)), new RCPosition(3, 4));
		cp.add(new Btn("6", Util.insertDigit(6)), new RCPosition(3, 5));
		cp.add(new Btn("1", Util.insertDigit(1)), new RCPosition(4, 3));
		cp.add(new Btn("2", Util.insertDigit(2)), new RCPosition(4, 4));
		cp.add(new Btn("3", Util.insertDigit(3)), new RCPosition(4, 5));
		cp.add(new Btn("0", Util.insertDigit(0)), new RCPosition(5, 3));

		cp.add(new UnaryOperationBtn("sin", "arcsin", Util.sin(), Util.arcsin()), new RCPosition(2, 2));
		cp.add(new UnaryOperationBtn("cos", "arccos", Util.cos(), Util.arccos()), new RCPosition(3, 2));
		cp.add(new UnaryOperationBtn("tan", "arctan", Util.tan(), Util.arctan()), new RCPosition(4, 2));
		cp.add(new UnaryOperationBtn("ctg", "arcctg", Util.ctg(), Util.arcctg()), new RCPosition(5, 2));
		cp.add(new UnaryOperationBtn("log", "10^x", Util.log(), Util.expBase10()), new RCPosition(3, 1));
		cp.add(new UnaryOperationBtn("ln", "e^x", Util.ln(), Util.expBaseE()), new RCPosition(4, 1));

		cp.add(new BinaryOperationBtn("+", Util.addOperator()), new RCPosition(5, 6));
		cp.add(new BinaryOperationBtn("-", Util.subOperator()), new RCPosition(4, 6));
		cp.add(new BinaryOperationBtn("*", Util.mulOperator()), new RCPosition(3, 6));
		cp.add(new BinaryOperationBtn("/", Util.divOperator()), new RCPosition(2, 6));
		cp.add(new BinaryOperationBtn("x^n", "x^(1/n)", Util.powNOperator(), Util.nthRootOperator()),
				new RCPosition(5, 1));

		cp.add(new Btn("1/x", Util.reciprocal()), new RCPosition(2, 1));
		cp.add(new Btn("=", Util.equals()), new RCPosition(1,6));
		cp.add(new Btn("clr", Util.clr()), new RCPosition(1, 7));
		cp.add(new Btn("reset", Util.reset()), new RCPosition(2, 7));
		cp.add(new Btn("+/-", Util.swapSign()), new RCPosition(5, 4));
		cp.add(new Btn(".", Util.insertDecimalPoint()), new RCPosition(5, 5));
		cp.add(new Btn("push", Util.push()), new RCPosition(3, 7));
		cp.add(new Btn("pop", Util.pop()), new RCPosition(4, 7));
	}

	/**
	 * Class that models buttons that only execute the given operation when clicked.
	 * 
	 * @author staver
	 *
	 */
	private class Btn extends JButton {
		private static final long serialVersionUID = 1L;

		public Btn(String btnName, Operation operation) {
			this.setText(btnName);
			this.setBackground(Color.decode("#DCDCDC"));
			if (Character.isDigit(btnName.charAt(0)) && btnName.length() == 1) {
				this.setFont(this.getFont().deriveFont(30f));
			}

			this.addActionListener(l -> {
				operation.execute(calcModel);
			});
		}
	}

	/**
	 * Class that models unary operation buttons. These buttons perform an operation
	 * or an inverse operation based on the state of the inverse button check box.
	 * 
	 * @author staver
	 *
	 */
	private class UnaryOperationBtn extends JButton {

		private static final long serialVersionUID = 1L;

		/**
		 * UnaryOperationBtn constructor.
		 * 
		 * @param btnName          default button name
		 * @param inverseName      button name for inverse operations
		 * @param operation        operation to be executed on click if inverse button
		 *                         is not checked
		 * @param inverseOperation inverse operation to executed on click if inverse
		 *                         button is checked
		 */
		public UnaryOperationBtn(String btnName, String inverseName, Operation operation, Operation inverseOperation) {
			this.setText(btnName);
			this.setBackground(Color.decode("#DCDCDC"));

			inverseBtn.addActionListener(l -> {
				if (inverseBtn.isSelected()) {
					this.setText(inverseName);
				} else {
					this.setText(btnName);
				}
			});

			this.addActionListener(l -> {
				if (calcModel.hasFrozenValue()) {
					throw new CalculatorInputException("Calculator model has frozen value.");
				}
				if (inverseBtn.isSelected()) {
					inverseOperation.execute(calcModel);
				} else {
					operation.execute(calcModel);
				}
			});
		}
	}

	/**
	 * Class that models binary operation buttons. These buttons set the current
	 * value as an active operand and set the given operation or an inverse
	 * operation (based on the state of the inverse button check box) as a pending
	 * binary operation to be executed when operation equals is called.
	 * 
	 * @author staver
	 *
	 */
	private class BinaryOperationBtn extends JButton {

		private static final long serialVersionUID = 1L;
		DoubleBinaryOperator op = null; //operator
		DoubleBinaryOperator inverseOp = null; //inverse operator

		/**
		 * BinaryOperationBtn constructor.
		 * 
		 * @param btnName          default button name
		 * @param inverseName      button name for inverse operations
		 * @param operation        operation to be set as pending on click if inverse
		 *                         button is not checked
		 * @param inverseOperation inverse operation to be set as pending on click if
		 *                         inverse button is checked
		 */
		public BinaryOperationBtn(String btnName, String inverseName, DoubleBinaryOperator op,
				DoubleBinaryOperator inverseOp) {
			this.op = op;
			this.inverseOp = inverseOp;
			this.setText(btnName);
			this.setBackground(Color.decode("#DCDCDC"));

			inverseBtn.addActionListener(l -> {
				if (inverseBtn.isSelected()) {
					this.setText(inverseName);
				} else {
					this.setText(btnName);
				}
			});

			addListener();
		}

		/**
		 * BinaryOperationBtn constructor.
		 * 
		 * @param btnName default button name
		 * @param op      operation to be set as pending on click
		 */
		public BinaryOperationBtn(String btnName, DoubleBinaryOperator op) {
			this.op = op;
			this.setText(btnName);
			this.setBackground(Color.decode("#DCDCDC"));

			addListener();
		}

		/**
		 * Adds a listener to this button that sets the current value as an active
		 * operand and sets the given operation or an inverse operation if exists(based
		 * on the state of the inverse button check box) as a pending binary operation
		 * to be executed when operation equals is called.
		 */
		private void addListener() {
			this.addActionListener(l -> {
				calcModel.freezeValue(calcModel.toString());
				if (calcModel.isActiveOperandSet()) {
					double leftOperand = calcModel.getActiveOperand();
					double rightOperand = calcModel.getValue();
					calcModel.setActiveOperand(
							calcModel.getPendingBinaryOperation().applyAsDouble(leftOperand, rightOperand));

				} else {
					calcModel.setActiveOperand(calcModel.getValue());
				}

				if (inverseBtn.isSelected() && inverseOp != null) {
					calcModel.setPendingBinaryOperation(inverseOp); // only for x^n
				} else {
					calcModel.setPendingBinaryOperation(op);
				}
				calcModel.clear();
			});
		}

	}

	/**
	 * Returns a new JLabel to be used as a calculator screen.
	 * 
	 * @return a new JLabel to be used as a calculator screen.
	 */
	private JLabel getCalcScreen() {
		JLabel calcScreen = new JLabel("0", SwingConstants.RIGHT);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		calcScreen.setBackground(Color.decode("#E8E8E8"));
		calcScreen.setBorder(border);
		calcScreen.setOpaque(true);
		calcScreen.setFont(calcScreen.getFont().deriveFont(30f));
		return calcScreen;
	}

	/**
	 * Main method that gets called when the program starts. Invokes the Calculator
	 * and sets it's window to visible.
	 * 
	 * @param args command line arguments - not used here.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
}
