package com.example.sqs_superherosra.repos;

import com.example.sqs_superherosra.entities.SqsMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SqsMessageRepository extends MongoRepository<SqsMessage, String> {
//	SqsMessage findByMessageBody(String messageBody);
}
