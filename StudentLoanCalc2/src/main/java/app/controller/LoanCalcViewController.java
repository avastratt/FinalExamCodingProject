package app.controller;

import app.StudentCalc;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import org.apache.poi.ss.formula.functions.*;

public class LoanCalcViewController implements Initializable   {

	private StudentCalc SC = null;
	
	@FXML
	private TableView<Input> table;
	@FXML
	private TextField LoanAmount;
	@FXML
	private TextField InterestRate;
	@FXML
	private TextField NumOfYears;
	@FXML
	private TextField AdditionalPayment;
	@FXML
	private Label lblTotalPayemnts;
	@FXML
	private Label lblTotalInterest;
	@FXML
	private DatePicker PaymentStartDate;
	@FXML
	private TableColumn<Input, Integer> PaymentNumCol;
	@FXML
	private TableColumn<Input, LocalDate> DueDateCol;
	@FXML
	private TableColumn<Input, Double> PaymentCol;
	@FXML
	private TableColumn<Input, Double> AddPaymentCol;
	@FXML
	private TableColumn<Input, Double> InterestCol;
	@FXML
	private TableColumn<Input, Double> PrincipleCol;
	@FXML
	private TableColumn<Input, Double> BalanceCol;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PaymentNumCol.setCellValueFactory(new PropertyValueFactory<Input, Integer>("Payment #"));
		DueDateCol.setCellValueFactory(new PropertyValueFactory<Input, LocalDate>("Due Date"));
		PaymentCol.setCellValueFactory(new PropertyValueFactory<Input, Double>("Payment"));
		AddPaymentCol.setCellValueFactory(new PropertyValueFactory<Input, Double>("Additonal Payment"));
		InterestCol.setCellValueFactory(new PropertyValueFactory<Input, Double>("Interest"));
		PrincipleCol.setCellValueFactory(new PropertyValueFactory<Input, Double>("Principle"));
		BalanceCol.setCellValueFactory(new PropertyValueFactory<Input, Double>("Balance"));
	}

	public void setMainApp(StudentCalc sc) {
		this.SC = sc;
	}
	
	/**
	 * btnCalcLoan - Fire this event when the button clicks
	 * 
	 * @version 1.0
	 * @param event
	 */
	@FXML
	private void btnCalcLoan(ActionEvent event) {
		
		System.out.println("Payment #, Due Date, Payment, Additional Payment, Interest, Principle, Balance");
		ObservableList<Input> Inputs = FXCollections.observableArrayList();
		
		boolean off = false;
		
		double dLoanAmount = Double.parseDouble(LoanAmount.getText());
		System.out.println("Amount is " + dLoanAmount);	
		
		double dInterestRate = Double.parseDouble(InterestRate.getText()) /100;
		System.out.println("Interest Rate is " + dInterestRate);
		
		double dNumOfYears = Double.parseDouble(NumOfYears.getText());
		System.out.println("Term is " + dNumOfYears);
		
		double dAdditionalPayment = Double.parseDouble(AdditionalPayment.getText());
		System.out.println("Additional Payment is " + dAdditionalPayment);
		
		LocalDate localDate = PaymentStartDate.getValue().minusMonths(1);
		System.out.println("FirstPayment Date is " + localDate);
		
		double PMT = Math.abs(FinanceLib.pmt(dInterestRate / 12, dNumOfYears * 12, dLoanAmount, 0, false));
		double TotalPayments = 0;
		double TotalInterest = 0;
		double Payment = PMT;
		double interest;
		double principle;
		double balance = dLoanAmount;
		
		
		for (int row = 1; row <= (dNumOfYears * 12); row++) {
			interest = dInterestRate / 12 * balance;
			principle = PMT - interest + dAdditionalPayment;
			if (balance - principle < 0) {
				principle = balance;
				off = true;
			}
			balance -= principle;
			localDate = localDate.plusMonths(1);
			TotalInterest += interest;
			

			Inputs.add(new Input(row,localDate, Math.round(Payment * 100.0) / 100.0, dAdditionalPayment,
					Math.round(interest * 100.0) / 100.0, Math.round(principle * 100.0) / 100.0,
					Math.round(balance * 100.0) / 100.0));
			System.out.println(row + ",\t" + localDate + ", " + Math.round(Payment * 100.0) / 100.0 + ", " + dAdditionalPayment
					+ ", " + Math.round(interest * 100.0) / 100.0 + ", " + Math.round(principle * 100.0) / 100.0 + ", "
					+ Math.round(balance * 100.0) / 100.0);
			if (off) {
				dNumOfYears = row / 12;
				break;
			}
		}
		table.setItems(Inputs);
		
		TotalPayments = TotalInterest + dLoanAmount;

		lblTotalInterest.setText(String.format("%.02f", TotalInterest));
		lblTotalPayemnts.setText(String.format("%.02f", TotalPayments));

	}
}
