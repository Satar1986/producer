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
                productDTO.getClientId(),
                productDTO.getNameAccount(),
                productDTO.getSum(),
                productDTO.getCurrency(),
                productDTO.getInterestRate(),
                productDTO.getInterestIsPaid(),
                productDTO.getMinRemainder(),
                productDTO.getStateId(),
                productDTO.getNameCompany(),
                productDTO.getInn(),
                productDTO.getKpp(),
                productDTO.getOgrn(),
                productDTO.getBusinessAddress(),
                productDTO.getAddress(),
                productDTO.getRcbic(),
                productDTO.getCorrAss(),
                productDTO.getAss(),
                productDTO.getBankName(),
                productDTO.getRequisitesId()
        );
        ProducerRecord<String, ProductEvent> record = new ProducerRecord<>(
                    "Account_bank",
                    externalId,
                    productEvent
            );
            record.headers().add("externalId", externalId.getBytes());
            CompletableFuture<SendResult<String, ProductEvent>> future =
                    kafkaTemplate.send(record);
            future.whenComplete((result, exception) -> {
                if (exception != null) {
                    logger.error("Failed to send message: {}", exception.getMessage());
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
                productUpdateDTO.getClientId(),
                productUpdateDTO.getNameAccount(),
                productUpdateDTO.getSum(),
                productUpdateDTO.getCurrency(),
                productUpdateDTO.getInterestRate(),
                productUpdateDTO.getInterestIsPaid(),
                productUpdateDTO.getMinRemainder(),
                productUpdateDTO.getStateId(),
                productUpdateDTO.getNameCompany(),
                productUpdateDTO.getInn(),
                productUpdateDTO.getKpp(),
                productUpdateDTO.getOgrn(),
                productUpdateDTO.getBusinessAddress(),
                productUpdateDTO.getAddress(),
                productUpdateDTO.getRcbic(),
                productUpdateDTO.getCorrAss(),
                productUpdateDTO.getAss(),
                productUpdateDTO.getBankName(),
                productUpdateDTO.getRequisitesId()
        );
        ProducerRecord<String, ProductEvent> record = new ProducerRecord<>(
                "Account_bank",
                productUpdateDTO.getExternalId(),
                productEvent
        );
        record.headers().add("externalId", productUpdateDTO.getExternalId().getBytes());
        CompletableFuture<SendResult<String, ProductEvent>> future =
                kafkaTemplate.send(record);
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                logger.error("Failed to send message: {}", exception.getMessage());
            } else {
                logger.info("Successfully sent message: {}", result.getRecordMetadata());
            }
        });
        logger.info("Return: {}", productUpdateDTO.getExternalId());
        return productUpdateDTO.getExternalId();
    }
}
