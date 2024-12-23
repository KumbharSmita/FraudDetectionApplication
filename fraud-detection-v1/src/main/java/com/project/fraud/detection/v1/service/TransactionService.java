//package com.project.fraud.detection.v1.service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.project.fraud.detection.v1.model.TransactionData;
//import com.project.fraud.detection.v1.repository.TransactionRepository;
//
//
//@Service
//public class TransactionService {
//
//	private final String url = "https://prepstripe.com/transaction_task_payloads.json";
//
//	@Autowired
//	private TransactionRepository transactionRepository;
//
////	public void fetchAndSaveTransactions() {
////
////		RestTemplate restTemplate = new RestTemplate();
////		JsonNode transactions = restTemplate.getForObject(url,  JsonNode.class);
////
////		// Loop through the JSON and save each transaction
////		transactions.forEach(transaction -> {
////			TransactionData data = new TransactionData();
////			data.setTransactionId(transaction.get("transactionId").asText());
////			data.setAmount(transaction.get("amount").asDouble());
////			data.setAccountNumber(transaction.get("accountNumber").asText());
////			data.setTransactionTime(transaction.get("transactionTime").asText());
////            data.setIpAddress(transaction.get("ipAddress").asText());
////            data.setCountry(transaction.get("location").get("country").asText());
////            data.setCity(transaction.get("location").get("city").asText());
////            data.setTransactionType(transaction.get("transactionType").asText());
////            data.setRemarks(transaction.get("remarks").asText());
//	////            data.setIndiaIpRange(indiaIpRange);
////
////			transactionRepository.save(data);
////		});
////	}
//
//	public void fetchAndSaveTransactions() {
//		try {
//			RestTemplate restTemplate = new RestTemplate();
//			JsonNode transactions = restTemplate.getForObject(url, JsonNode.class);
//
//			if (transactions == null || transactions.isEmpty()) {
//				System.err.println("No data returned from the API");
//				return;
//			}
//
//			System.out.println("Fetched Transactions: " + transactions.toString());
//
//			transactions.forEach(transaction -> {
//				try {
//					TransactionData data = new TransactionData();
//					data.setTransactionId(transaction.get("transactionId").asText());
//					data.setAmount(transaction.get("amount").asDouble());
//					data.setAccountNumber(transaction.get("accountNumber").asText());
//					data.setTransactionTime(transaction.get("transactionTime").asText());
//					data.setIpAddress(transaction.get("ipAddress").asText());
//					data.setCountry(transaction.get("location").get("country").asText());
//					data.setCity(transaction.get("location").get("city").asText());
//					data.setTransactionType(transaction.get("transactionType").asText());
//					data.setRemarks(transaction.get("remarks").asText());
//
//					transactionRepository.save(data);
//				} catch (Exception e) {
//					System.err.println("Error processing transaction: " + transaction + ", Error: " + e.getMessage());
//				}
//			});
//		} catch (Exception e) {
//			System.err.println("Error fetching or parsing API data: " + e.getMessage());
//		}
//	}
//
//
//
//
//	public List<TransactionData> getAllTransactions() {
//		return transactionRepository.findAll();
//	}
//
//
//
//	public List<TransactionData> getFlaggedTransactions(List<TransactionData> transactions) {
//		return transactions.stream()
//				.filter(transaction -> transaction.getAmount() > 100000)
//				.collect(Collectors.toList());
//	}
//
//
//
//	public List<TransactionData> getFlaggedTransactionsByIp(List<TransactionData> transactions) {
//		return transactions.stream()
//				.filter(transaction -> !"India".equals(transaction.getCountry()))
//				.collect(Collectors.toList());
//	}
//
//
//
//	public List<TransactionData> getFlaggedTransactionsByAccount(List<TransactionData> transactions) {
//		List<String> blacklistedAccounts = List.of("123456", "9229520619"); // Hardcoded Blacklisted Accounts
//		return transactions.stream()
//				.filter(transaction -> blacklistedAccounts.contains(transaction.getAccountNumber()))
//				.collect(Collectors.toList());
//	}
//
//
////	public List<TransactionData> getFlaggedTransactionsByTime(List<TransactionData> transactions) {
////	    return transactions.stream()
////	            .filter(transaction -> {
////	                // Fetch transactions for the same account and check the time
////	                List<TransactionData> similarTransactions = transactionRepository.findTransactionsWithinFiveMinutes(transaction.getAccountNumber(), transaction.getTransactionTime());
////	                return similarTransactions.size() > 3;
////	            })
////	            .collect(Collectors.toList());
////	}
//
//}
//
//
//
//



//
//package com.project.fraud.detection.v1.service;
//
//import com.project.fraud.detection.v1.model.TransactionData;
//import com.project.fraud.detection.v1.repository.TransactionRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class TransactionService {
//
//	// URL to fetch transaction data (can be configured in application.properties)
//	@Value("${transactions.api.url}")
//	private String apiUrl;
//
//	@Autowired
//	private TransactionRepository transactionRepository;
//
//	// Method to fetch transactions from the external API and save them to the database
//	public void fetchAndSaveTransactions() {
//		try {
//			RestTemplate restTemplate = new RestTemplate();
//			List<TransactionData> transactions = restTemplate.getForObject(apiUrl, List.class);
//
//			if (transactions == null || transactions.isEmpty()) {
//				System.err.println("No data returned from the API");
//				return;
//			}
//
//			System.out.println("Fetched Transactions: " + transactions.toString());
//
//			// Loop through the list of transactions and save each one to the database
//			transactions.forEach(transaction -> {
//				try {
//					// Save each transaction to the repository
//					transactionRepository.save(transaction);
//				} catch (Exception e) {
//					System.err.println("Error processing transaction: " + transaction + ", Error: " + e.getMessage());
//				}
//			});
//		} catch (Exception e) {
//			System.err.println("Error fetching or parsing API data: " + e.getMessage());
//		}
//	}
//
//	// Get all transactions from the database
//	public List<TransactionData> getAllTransactions() {
//		return transactionRepository.findAll();
//	}
//
//	// Flag transactions based on the amount exceeding ₹1,00,000
//	public List<TransactionData> getFlaggedTransactions(List<TransactionData> transactions) {
//		return transactions.stream()
//				.filter(transaction -> transaction.getAmount() > 100000) // Flag transactions with amounts over ₹1,00,000
//				.collect(Collectors.toList());
//	}
//
//	// Flag transactions based on the country being outside of India
//	public List<TransactionData> getFlaggedTransactionsByIp(List<TransactionData> transactions) {
//		return transactions.stream()
//				.filter(transaction -> !"India".equals(transaction.getCountry())) // Flag transactions from countries other than India
//				.collect(Collectors.toList());
//	}
//
//	// Flag transactions from blacklisted accounts (hardcoded accounts or can be fetched from a DB)
//	@Value("${blacklisted.accounts}")
//	private List<String> blacklistedAccounts;
//
//	public List<TransactionData> getFlaggedTransactionsByAccount(List<TransactionData> transactions) {
//		return transactions.stream()
//				.filter(transaction -> blacklistedAccounts.contains(transaction.getAccountNumber())) // Flag transactions from blacklisted accounts
//				.collect(Collectors.toList());
//	}
//
//	// Flag transactions based on more than 3 transactions from the same account within 5 minutes
//	public List<TransactionData> getFlaggedTransactionsByTime(List<TransactionData> transactions) {
//		return transactions.stream()
//				.filter(transaction -> {
//					// You would need a method to check for similar transactions within the same account and time window
//					List<TransactionData> similarTransactions = transactionRepository.findTransactionsWithinFiveMinutes(transaction.getAccountNumber(), transaction.getTransactionTime());
//					return similarTransactions.size() > 3; // Flag if more than 3 transactions within 5 minutes
//				})
//				.collect(Collectors.toList());
//	}
//
//	// Method to process incoming transaction and flag if needed
//	public String processTransaction(TransactionData transaction) {
//		// Check if the transaction exceeds ₹1,00,000
//		if (transaction.getAmount() > 100000) {
//			return "{ \"status\": \"Fraud\", \"reason\": \"Transaction amount exceeds the limit of ₹1,00,000.\" }";
//		}
//
//		// Check if the transaction comes from a blacklisted account
//		if (blacklistedAccounts.contains(transaction.getAccountNumber())) {
//			return "{ \"status\": \"Fraud\", \"reason\": \"Transaction from a blacklisted account.\" }";
//		}
//
//		// Check if the transaction is from outside India
//		if (!"India".equals(transaction.getCountry())) {
//			return "{ \"status\": \"Fraud\", \"reason\": \"Transaction initiated from outside India.\" }";
//		}
//
//		// Check if there are more than 3 transactions within 5 minutes from the same account
//		List<TransactionData> similarTransactions = transactionRepository.findTransactionsWithinFiveMinutes(transaction.getAccountNumber(), transaction.getTransactionTime());
//		if (similarTransactions.size() > 3) {
//			return "{ \"status\": \"Fraud\", \"reason\": \"More than 3 transactions within 5 minutes from the same account.\" }";
//		}
//
//		// If none of the fraud conditions are met
//		return "{ \"status\": \"Success\", \"message\": \"Transaction is valid.\" }";
//	}
//}

package com.project.fraud.detection.v1.service;

import com.fasterxml.jackson.databind.JsonNode; // <-- Add this import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.fraud.detection.v1.model.TransactionData;
import com.project.fraud.detection.v1.repository.TransactionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	public void fetchAndSaveTransactions() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = "https://prepstripe.com/transaction_task_payloads.json";
			JsonNode transactions = restTemplate.getForObject(url, JsonNode.class);

			if (transactions == null || transactions.isEmpty()) {
				System.err.println("No data returned from the API");
				return;
			}

			transactions.forEach(transaction -> {
				try {
					TransactionData data = new TransactionData();
					data.setTransactionId(transaction.get("transactionId").asText());
					data.setAmount(transaction.get("amount").asDouble());
					data.setAccountNumber(transaction.get("accountNumber").asText());
					data.setTransactionTime(transaction.get("transactionTime").asText());
					data.setIpAddress(transaction.get("ipAddress").asText());
					data.setCountry(transaction.get("location").get("country").asText());
					data.setCity(transaction.get("location").get("city").asText());
					data.setTransactionType(transaction.get("transactionType").asText());
					data.setRemarks(transaction.get("remarks").asText());

					transactionRepository.save(data);
				} catch (Exception e) {
					System.err.println("Error processing transaction: " + transaction + ", Error: " + e.getMessage());
				}
			});
		} catch (Exception e) {
			System.err.println("Error fetching or parsing API data: " + e.getMessage());
		}
	}

	public List<TransactionData> getAllTransactions() {
		return transactionRepository.findAll();
	}

	public List<TransactionData> getFlaggedTransactions(List<TransactionData> transactions) {
		return transactions.stream()
				.filter(transaction -> transaction.getAmount() > 100000) // Flagging high-value transactions
				.collect(Collectors.toList());
	}

	public List<TransactionData> getFlaggedTransactionsByIp(List<TransactionData> transactions) {
		return transactions.stream()
				.filter(transaction -> !"India".equals(transaction.getCountry())) // Flagging transactions from non-India locations
				.collect(Collectors.toList());
	}

	public List<TransactionData> getFlaggedTransactionsByAccount(List<TransactionData> transactions) {
		List<String> blacklistedAccounts = List.of("123456", "9229520619"); // Hardcoded Blacklisted Accounts
		return transactions.stream()
				.filter(transaction -> blacklistedAccounts.contains(transaction.getAccountNumber())) // Flagging blacklisted accounts
				.collect(Collectors.toList());
	}
}
