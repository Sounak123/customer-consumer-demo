package com.learn.pkg.service;

import com.learn.pkg.model.kafka.PublisherRequest;

public interface ConsumerService {
  public void publishCustomerData(PublisherRequest publisherRequest);
}
