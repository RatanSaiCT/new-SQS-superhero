package com.example.sqs_superherosra.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sqsMessages")
public class SqsMessage {
	@Id
	private String id;
	private String messageBody;
	private String receiptHandle;
}
