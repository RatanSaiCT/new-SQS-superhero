package com.example.sqs_superherosra.controller;

import com.example.sqs_superherosra.config.SqsConfig;
import com.example.sqs_superherosra.dto.SuperheroRequestBody;
import com.example.sqs_superherosra.entities.Superhero;
import com.example.sqs_superherosra.services.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@RestController
@RequestMapping("/api")
public class SuperHeroController {

	@Autowired
	private SuperheroService superheroService;


	@Autowired
	private SqsConfig sqsConfig;

	@Autowired
	private SqsClient sqsClient;


//	@GetMapping("/hello")
//	public String hello(@RequestParam(value = "username", defaultValue = "World") String username) {
//		sqsClient.sendMessage(SendMessageRequest.builder()
//				.queueUrl(sqsConfig.getQueueUrl())
//				.messageBody("SpiderMan")
//				.build());
//
//
//		return String.format("Hello %s!, %s", username);
//	}

//	@GetMapping("/update_superhero_async")
//	public String updateSuperhero(@RequestParam(value = "superHeroName", defaultValue = "ironMan") String superHeroName) {
//
//
//		SendMessageResponse result = sqsClient.sendMessage(SendMessageRequest.builder()
//				.queueUrl(sqsConfig.getQueueUrl())
//				.messageBody(superHeroName)
//				.build());
//
//		return String.format("Message sent to queue with message id %s and superHero %s", result.messageId(), superHeroName);
//	}

	@GetMapping("/send_message_to_queue")
	public String sendMessage(@RequestParam(value = "message", defaultValue = "Ratan Kalpa Sai") String message) {
		SendMessageResponse result = sqsClient.sendMessage(SendMessageRequest.builder()
				.queueUrl(sqsConfig.getQueueUrl())
				.messageBody(message)
				.build());

		return String.format("Message sent to queue with message id %s and message %s", result.messageId(), message);
	}

//	@PutMapping("/update_message_to_queue")
//	public String updateMessage(@RequestParam(value = "message", defaultValue = "Ratan Kalpa Sai") String message) {
//
//		SendMessageResponse result = sqsClient.sendMessage(SendMessageRequest.builder()
//				.queueUrl(sqsConfig.getQueueUrl())
//				.messageBody(message)
//				.build());
//
//		return String.format("Message sent to queue with message id %s and message %s", result.messageId(), message);
//	}


	//    @GetMapping("/superhero")
	//    public Superhero getSuperhero(@RequestParam(value = "name", defaultValue = "Batman") String name,
	//                                  @RequestParam(value = "universe", defaultValue = "DC") String universe){
	//        return superheroService.getSuperhero(name, universe);
	//    }



	//	@GetMapping("/superhero")
	//	public String getSuperHeroByNameOrUniverse(@RequestParam(value = "name", defaultValue = "Batman") String name,
	//			@RequestParam(value = "universe", defaultValue = "DC") String universe) {
	//		return String.format("Superhero name: %s, Universe: %s", name, universe);
	//	}

	@GetMapping("/superhero")
	public Superhero getSuperHeroByNameOrUniverse(@RequestParam(value = "name", required = false) String name,
												  @RequestParam(value = "universe", required = false) String universe) {
		return superheroService.getSuperheroByNameOrUniverse(name, universe);
	}

	@PostMapping("/superhero")
	public Superhero persistSuperHero(@RequestBody SuperheroRequestBody superhero) {
		return superheroService.persistSuperhero(superhero);
	}

	@PutMapping("/superhero")
	public Superhero updateSuperHero(@RequestBody SuperheroRequestBody superhero) {
		return superheroService.updateSuperhero(superhero);
	}

	@DeleteMapping("/superhero")
	public void deleteSuperHero(@RequestParam(value = "name") String name) {
		superheroService.deleteSuperhero(name);
	}
}
