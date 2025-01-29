package com.example.sqs_superherosra.services;

import com.example.sqs_superherosra.config.SqsConfig;
import com.example.sqs_superherosra.dto.SuperheroRequestBody;
import com.example.sqs_superherosra.entities.SqsMessage;
import com.example.sqs_superherosra.entities.Superhero;
import com.example.sqs_superherosra.repos.SqsMessageRepository;
import com.example.sqs_superherosra.repos.SuperheroRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

@Service
public class SuperheroService {

	@Autowired
	private SqsConfig sqsConfig;

	@Autowired
	private final SqsClient sqsClient;

	@Autowired
	private SuperheroRepository superheroRepository;

	@Autowired
	private SqsMessageRepository sqsMessageRepository;

	public SuperheroService(SqsClient sqsClient) {
		this.sqsClient = sqsClient;
	}

//	@PostConstruct
	@Scheduled(fixedDelay = 1000)
	public void consumeMessages() {
//		new Thread(() -> {
//			while (true) {
				try {
					// Poll messages from the SQS queue
					ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
							.queueUrl(sqsConfig.getQueueUrl())
							.maxNumberOfMessages(10) // Adjust as needed
							.waitTimeSeconds(5) // Long polling
							.build();

					ReceiveMessageResponse response = sqsClient.receiveMessage(receiveMessageRequest);
					for (Message message : response.messages()) {

						SqsMessage sqsMessage = new SqsMessage();
						sqsMessage.setMessageBody(message.body());
						sqsMessage.setReceiptHandle(message.receiptHandle());

						sqsMessageRepository.save(sqsMessage);

						// Delete the message from SQS
						deleteMessageFromQueue(message.receiptHandle());
					}
				} catch (Exception e) {
					System.err.println("Error consuming messages: " + e.getMessage());
				}
			}
//		}
//		).start();
//	}

	private void deleteMessageFromQueue(String receiptHandle) {
		DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
				.queueUrl(sqsConfig.getQueueUrl())
				.receiptHandle(receiptHandle)
				.build();
		sqsClient.deleteMessage(deleteMessageRequest);
	}



	//	public SuperheroService(SuperheroRepository superheroRepository) {
	//		this.superheroRepository = superheroRepository;
	//	}

	public Superhero getSuperheroByNameOrUniverse(String name, String universe) {
		//		if(null != name){
		//			return getbyName(name);
		//		}
		//		else if(null != universe){
		//			return getbyUniverse(universe);
		//		}
		//		else {
		//			throw new RuntimeException("Please provide name or universe");
		//		}
		Superhero superhero = superheroRepository.findByName(name) ;
		if (null == superhero) {
			superhero = superheroRepository.findByUniverse(universe);
		}
		return superhero;
	}

	private Superhero getbyUniverse(String universe) {
		Superhero ok = new Superhero();
		ok.setUniverse(universe);
		return ok;
	}

	private Superhero getbyName(String name) {
		Superhero ok = new Superhero();
		ok.setName(name);
		return ok;
	}

	public Superhero persistSuperhero(SuperheroRequestBody superhero) {
		Superhero superhero1 = new Superhero();
		superhero1.setName(superhero.getName());
		superhero1.setUniverse(superhero.getUniverse());
		superhero1.setPower(superhero.getPower());
		//		superhero1.se
		return superheroRepository.save(superhero1);
	}

	public Superhero updateSuperhero(SuperheroRequestBody superhero) {
		Superhero superhero1 = superheroRepository.findByName(superhero.getName());
		superhero1.setName(superhero.getName());
		superhero1.setUniverse(superhero.getUniverse());
		superhero1.setPower(superhero.getPower());
		return superheroRepository.save(superhero1);
	}

	public void deleteSuperhero(String name) {
		superheroRepository.deleteByName(name);
	}
}