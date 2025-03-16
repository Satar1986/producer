package org.example.service;


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
public class ProductServiceImpl implements ProductService {

    private KafkaTemplate<String, ProductEvent> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProductServiceImpl(KafkaTemplate<String, ProductEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }

    @Override
    public String createProduct(ProductDTO productDTO) {
        /// Too database
        String externalId = UUID.randomUUID().toString();
        ProductEvent productEvent = new ProductEvent(externalId,
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
record.headers().add("externalId",externalId.getBytes());

        CompletableFuture<SendResult<String, ProductEvent>> future=
               kafkaTemplate.send(record).completable();
       future.whenComplete((result,exeption)->{
           if (exeption!=null) {
logger.error("Failed to send message: {}",exeption.getMessage());
           }else {
logger.info("Successfully sent message: {}",result.getRecordMetadata());
           }
       });
logger.info("Return: {}",externalId);
        return externalId;
    }

}
