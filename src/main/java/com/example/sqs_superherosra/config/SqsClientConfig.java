package com.example.sqs_superherosra.config;

import org.apache.catalina.authenticator.BasicAuthenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
public class SqsClientConfig {

	private final SqsConfig sqsConfig;

	public SqsClientConfig(SqsConfig sqsConfig) {
		this.sqsConfig = sqsConfig;
	}

	@Bean
	public SqsClient sqsClient() {
		StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(
				AwsBasicCredentials.create(
						sqsConfig.getAccessKey(),
						sqsConfig.getSecretKey()
				)
		);

		return SqsClient.builder()
				.region(Region.of(sqsConfig.getRegion()))
				.credentialsProvider(credentialsProvider)
				.endpointOverride(URI.create("http://localhost:4566")) // For LocalStack
				.build();
	}
}