package org.example.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.example.event.ProductEvent;

import org.example.service.dto.ProductDTO;
import org.example.service.dto.ProductUpdateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;
    private static final  Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public String createProduct(ProductDTO productDTO) {
        String externalId = UUID.randomUUID().toString();
        ProductEvent  productEvent = new ProductEvent(externalId,
                productDTO.getClient_id(),
                productDTO.getName_account(),
                productDTO.getSum(),
                productDTO.getCurrency(),
                productDTO.getInterest_rate(),
                productDTO.getInterest_is_paid(),
                productDTO.getMin_remainder(),
                productDTO.getState_id(),
                productDTO.getName_company(),
                productDTO.getInn(),
                productDTO.getKpp(),
                productDTO.getOgrn(),
                productDTO.getBusiness_address(),
                productDTO.getAddress(),
                productDTO.getRcbic(),
                productDTO.getCorr_ass(),
                productDTO.getAss(),
                productDTO.getBank_name()
        );
        ProducerRecord<String, ProductEvent> record = new ProducerRecord<>(
                    "Account_bank",
                    externalId,
                    productEvent
            );
            record.headers().add("externalId", externalId.getBytes());
            CompletableFuture<SendResult<String, ProductEvent>> future =
                    kafkaTemplate.send(record);
            future.whenComplete((result, exeption) -> {
                if (exeption != null) {
                    logger.error("Failed to send message: {}", exeption.getMessage());
                } else {
                    logger.info("Successfully sent message: {}", result.getRecordMetadata());
                }
            });
            logger.info("Return: {}", externalId);
            return externalId;
    }
    @Override
    public String updateProduct(ProductUpdateDTO productUpdateDTO) {
        ProductEvent productEvent = new ProductEvent(
                productUpdateDTO.getExternalId(),
                productUpdateDTO.getClient_id(),
                productUpdateDTO.getName_account(),
                productUpdateDTO.getSum(),
                productUpdateDTO.getCurrency(),
                productUpdateDTO.getInterest_rate(),
                productUpdateDTO.getInterest_is_paid(),
                productUpdateDTO.getMin_remainder(),
                productUpdateDTO.getState_id(),
                productUpdateDTO.getName_company(),
                productUpdateDTO.getInn(),
                productUpdateDTO.getKpp(),
                productUpdateDTO.getOgrn(),
                productUpdateDTO.getBusiness_address(),
                productUpdateDTO.getAddress(),
                productUpdateDTO.getRcbic(),
                productUpdateDTO.getCorr_ass(),
                productUpdateDTO.getAss(),
                productUpdateDTO.getBank_name()
        );
        ProducerRecord<String, ProductEvent> record = new ProducerRecord<>(
                "Account_bank",
                productUpdateDTO.getExternalId(),
                productEvent
        );
        record.headers().add("externalId", productUpdateDTO.getExternalId().getBytes());
        CompletableFuture<SendResult<String, ProductEvent>> future =
                kafkaTemplate.send(record);
        future.whenComplete((result, exeption) -> {
            if (exeption != null) {
                logger.error("Failed to send message: {}", exeption.getMessage());
            } else {
                logger.info("Successfully sent message: {}", result.getRecordMetadata());
            }
        });
        logger.info("Return: {}", productUpdateDTO.getExternalId());
        return productUpdateDTO.getExternalId();
    }
}
