-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass)
VALUES (:id, :first_name, :last_name, :email, :pass)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id

-- :name create-payment! :! :n
-- :doc Creates new payment
INSERT INTO payments
(id, full_name, msisdn, amount, status, date_created, last_modified, external_txn_id,
 mpesa_response_code, mpesa_conversation_id, mpesa_originator_conversation_id,
 mpesa_response_description, user_id)
VALUES
(:id, :full_name, :msisdn, :amount, :status, :date_created, :last_modified, :external_txn_id,
 :mpesa_response_code, :mpesa_conversation_id, :mpesa_originator_conversation_id,
 :mpesa_response_description, :user_id)

-- :name update-payment-satus! :! :n
-- :doc updates an existing payment record status
UPDATE payments
SET status = :status, last_modified = :last_modified, mpesa_response_code = :mpesa_response_code,
mpesa_conversation_id = :mpesa_conversation_id, mpesa_conversation_id = :mpesa_conversation_id,
mpesa_originator_conversation_id = :mpesa_originator_conversation_id,
mpesa_response_description = :mpesa_response_description, user_id = :user_id
WHERE id = :id

-- :name get-payments :? :*
-- :doc selects all available payments
SELECT * from payments