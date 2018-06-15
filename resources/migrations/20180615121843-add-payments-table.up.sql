CREATE TABLE payments
(id VARCHAR(80) PRIMARY KEY,
 full_name VARCHAR(50),
 msisdn VARCHAR(30),
 amount BIGINT,
 status INT,
 date_created TIMESTAMP,
 last_modified TIMESTAMP,
 external_txn_id VARCHAR(100),
 mpesa_response_code VARCHAR(10),
 mpesa_conversation_id VARCHAR(100),
 mpesa_originator_conversation_id VARCHAR(100),
 mpesa_response_description VARCHAR(300),
 user_id VARCHAR(80));
