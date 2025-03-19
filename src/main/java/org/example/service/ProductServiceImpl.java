package org.example.service;


import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.example.event.ProductEvent;

import org.example.service.dto.ProductDTO;


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
        if (productDTO.getExternalId() == null) {
                   productDTO.setExternalId(UUID.randomUUID().toString());
               }
        ProductEvent  productEvent = new ProductEvent(productDTO.getExternalId(),
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
                    productDTO.getExternalId(),
                    productEvent
            );
            record.headers().add("externalId", productDTO.getExternalId().getBytes());

            CompletableFuture<SendResult<String, ProductEvent>> future =
                    kafkaTemplate.send(record);
            future.whenComplete((result, exeption) -> {
                if (exeption != null) {
                    logger.error("Failed to send message: {}", exeption.getMessage());
                } else {
                    logger.info("Successfully sent message: {}", result.getRecordMetadata());
                }
            });
            logger.info("Return: {}", productDTO.getExternalId());
            return productDTO.getExternalId();
    }
}
