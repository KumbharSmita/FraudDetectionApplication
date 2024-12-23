//package com.project.fraud.detection.v1.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.project.fraud.detection.v1.model.TransactionData;
//import com.project.fraud.detection.v1.service.TransactionService;
//
//@RestController
//public class TransactionController {
//
//	@Autowired
//	private TransactionService transactionService;
//
//	@GetMapping("/save-transactions")
//	public String saveTransactions() {
//		transactionService.fetchAndSaveTransactions();
//		return "Transaction saved successfully!";
//	}
//
//	@GetMapping("/get-transactions")
//	public List<TransactionData> getTransactions() {
//		List<TransactionData> transactions = transactionService.getAllTransactions();
//		return transactions;
//	}
//
//	@GetMapping("/flaggedByAmount")
//	public ResponseEntity<List<TransactionData>> getFlaggedTransactions() {
//		List<TransactionData> flaggedTransactions = transactionService.getFlaggedTransactions(getTransactions());
//		return ResponseEntity.ok(flaggedTransactions);
//	}
//
//	@GetMapping("/flaggedByIp")
//	public ResponseEntity<List<TransactionData>> getFlaggedTransactionsByIp() {
//		List<TransactionData> flaggedTransactions = transactionService.getFlaggedTransactionsByIp(getTransactions());
//		return ResponseEntity.ok(flaggedTransactions);
//	}
//
//	@GetMapping("/flaggedByAccount")
//	public ResponseEntity<List<TransactionData>> getFlaggedTransactionsByAccount() {
//		List<TransactionData> flaggedTransactions = transactionService.getFlaggedTransactionsByAccount(getTransactions());
//		return ResponseEntity.ok(flaggedTransactions);
//	}
//
//
////	@GetMapping("/flaggedByTime")
////	public ResponseEntity<List<TransactionData>> getFlaggedTransactionsByTime() {
////	    List<TransactionData> flaggedTransactions = transactionService.getFlaggedTransactionsByTime(getTransactions());
////	    return ResponseEntity.ok(flaggedTransactions);
////	}
//
//}
//


//package com.project.fraud.detection.v1.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.project.fraud.detection.v1.model.TransactionData;
//import com.project.fraud.detection.v1.service.TransactionService;
//
//@RestController
//@RequestMapping("/transactions")
//public class TransactionController {
//
//	@Autowired
//	private TransactionService transactionService;
//
//	// Endpoint to process a single transaction
//	@PostMapping("/process")
//	public ResponseEntity<TransactionData> processTransaction(@RequestBody TransactionData transaction) {
//		TransactionData result = transactionService.processTransaction(transaction);
//		return ResponseEntity.ok(result);
//	}
//
//	// Endpoint to get flagged transactions (Admin only)
//	@GetMapping("/admin/flagged-transactions")
//	public ResponseEntity<List<TransactionData>> getFlaggedTransactions() {
//		List<TransactionData> flaggedTransactions = transactionService.getFlaggedTransactions();
//		return ResponseEntity.ok(flaggedTransactions);
//	}
//}


package com.project.fraud.detection.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.fraud.detection.v1.model.FraudResponse;
import com.project.fraud.detection.v1.model.TransactionData;
import com.project.fraud.detection.v1.service.TransactionService;

import java.util.List;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

//	@PostMapping("/transactions/process")
//	public ResponseEntity<?> processTransaction(@RequestBody TransactionData transactionData) {
//		// Apply fraud detection logic
//		if (transactionData.getAmount() > 100000) {
//			return ResponseEntity.ok(new FraudResponse("Fraud", "Transaction amount exceeds ₹1,00,000"));
//		} else if (!"India".equals(transactionData.getCountry())) {
//			return ResponseEntity.ok(new FraudResponse("Fraud", "Transaction initiated from outside India"));
//		} else if (List.of("123456", "9229520619").contains(transactionData.getAccountNumber())) {
//			return ResponseEntity.ok(new FraudResponse("Fraud", "Transaction initiated from blacklisted account"));
//		}
//		return ResponseEntity.ok(new FraudResponse("Success", "Transaction is valid"));
//	}


	@PostMapping("/transactions/process")
	public ResponseEntity<?> processTransaction(@RequestBody TransactionData transactionData) {
		try {
			// Apply fraud detection logic here
			if (transactionData.getAmount() > 100000) {
				return ResponseEntity.ok(new FraudResponse("Fraud", "Transaction amount exceeds ₹1,00,000"));
			} else if (!"India".equals(transactionData.getCountry())) {
				return ResponseEntity.ok(new FraudResponse("Fraud", "Transaction initiated from outside India"));
			} else if (List.of("123456", "9229520619").contains(transactionData.getAccountNumber())) {
				return ResponseEntity.ok(new FraudResponse("Fraud", "Transaction initiated from blacklisted account"));
			}
			return ResponseEntity.ok(new FraudResponse("Success", "Transaction is valid"));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(new FraudResponse("Error", "Internal Server Error"));
		}
	}


	@GetMapping("/admin/flagged-transactions")
	public ResponseEntity<List<TransactionData>> getFlaggedTransactions() {
		List<TransactionData> transactions = transactionService.getAllTransactions();
		List<TransactionData> flaggedTransactions = transactionService.getFlaggedTransactions(transactions);
		return ResponseEntity.ok(flaggedTransactions);
	}



}
