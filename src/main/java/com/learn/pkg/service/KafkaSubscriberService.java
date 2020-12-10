package com.learn.pkg.service;

import com.learn.pkg.model.kafka.PublisherRequest;

public interface KafkaSubscriberService {
  public void subscribe(PublisherRequest request);
}
