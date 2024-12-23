//package com.project.fraud.detection.v1.repository;
//
//import com.project.fraud.detection.v1.model.TransactionData;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface TransactionRepository extends JpaRepository<TransactionData, String> {
//
//    @Query("SELECT t FROM TransactionData t WHERE t.accountNumber = :accountNumber " +
//            "AND ABS(TIMESTAMPDIFF(MINUTE, t.transactionTime, :currentTime)) <= 5")
//    List<TransactionData> findTransactionsWithinFiveMinutes(@Param("accountNumber") String accountNumber,
//                                                            @Param("currentTime") String currentTime);
//}


package com.project.fraud.detection.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.fraud.detection.v1.model.TransactionData;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionData, String> {

    // Find all transactions by account number
    List<TransactionData> findByAccountNumber(String accountNumber);

    // Find transactions that have an amount greater than a specified threshold
    List<TransactionData> findByAmountGreaterThan(double amount);

    // Find all transactions from a specific IP address
    @Query("SELECT t FROM TransactionData t WHERE t.ipAddress = :ipAddress")
    List<TransactionData> findByIpAddress(@Param("ipAddress") String ipAddress);

    // Find transactions from locations not in India
    @Query("SELECT t FROM TransactionData t WHERE t.country != 'India'")
    List<TransactionData> findTransactionsNotFromIndia();
}

