package com.example.sqs_superherosra.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
@Data
public class SqsConfig {

	@Value("${aws.sqs.queue.url}")
	private String queueUrl;

	@Value("${aws.region}")
	private String region;

	@Value("${aws.accessKeyId}")
	private String accessKey;

	@Value("${aws.secretAccessKey}")
	private String secretKey;

	@Value("${aws.sessionToken}")
	private String sessionToken;
}
