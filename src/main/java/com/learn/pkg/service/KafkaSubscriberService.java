package com.learn.pkg.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.learn.pkg.converter.CustomerDataMasker;
import com.learn.pkg.model.kafka.KafkaCustomerDataRequest;
import com.learn.pkg.model.kafka.PublisherRequest;

@Service
public class KafkaSubscriberService {

  private static Logger logger = LoggerFactory.getLogger(KafkaSubscriberService.class);

  @Autowired private CustomerDataMasker customerConsumerDataMasker;

  @Autowired private ConsumerService consumerService;

  @KafkaListener(topics = "${cloudkarafka.topic}", groupId = "${cloudkarafka.group-id}")
  public void subscribe(PublisherRequest request) {
    KafkaCustomerDataRequest kafkaDataRequest =
        customerConsumerDataMasker.convert(request.getCustomerData());
    logger.info("Consumed data {}", kafkaDataRequest);
    consumerService.publishCustomerData(request);
  }
}
