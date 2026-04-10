package com.btg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.ses.SesClient;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class DynamoDbConfig {

   // private final Region region = Region.US_EAST_1;
     
    private final DefaultCredentialsProvider credentialsProvider = DefaultCredentialsProvider.create();
 
    @Value("${aws.dynamodb.accessKey}")
    private String accessKeyBD;

    @Value("${aws.dynamodb.secretKey}")
    private String secretKeyBD;
    
    @Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.secretAccessKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;
    
    
    @Bean
    @Primary
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyBD, secretKeyBD)))
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    public SnsClient snsClient() {
//        return SnsClient.builder()
//                .region(region)
//                .credentialsProvider(credentialsProvider)
//                .build();
        return SnsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    @Bean
    public SesClient sesClient() {
//        return SesClient.builder()
//                .region(region)
//                .credentialsProvider(credentialsProvider)
//                .build();
        return SesClient.builder()
                .region(Region.of(region)).credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }
    
 
}
