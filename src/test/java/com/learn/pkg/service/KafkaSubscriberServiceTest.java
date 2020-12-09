package com.learn.pkg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learn.pkg.converter.CustomerDataMasker;
import com.learn.pkg.model.kafka.PublisherRequest;
import com.learn.pkg.util.ObjectMapperUtilTest;

@ExtendWith(MockitoExtension.class)
public class KafkaSubscriberServiceTest {

  @InjectMocks private KafkaSubscriberService subscriberService;

  @Mock private CustomerDataMasker customerConsumerDataMasker;

  @Mock private ConsumerService consumerService;

  @Test
  public void testSubscribe() {
    PublisherRequest publisherRequest =
        new PublisherRequest(
            ObjectMapperUtilTest.getCustomerData(), "transaction-id", "activity-id");
    subscriberService.subscribe(publisherRequest);

    verify(consumerService, times(1)).publishCustomerData(Mockito.any());
  }

  @Test
  public void testSubscribeFailure() {
    PublisherRequest publisherRequest =
        new PublisherRequest(
            ObjectMapperUtilTest.getCustomerData(), "transaction-id", "activity-id");
    Mockito.when(customerConsumerDataMasker.convert(Mockito.any()))
        .thenThrow(new RuntimeException("Unable to convert"));
    try {
      subscriberService.subscribe(publisherRequest);
    } catch (RuntimeException e) {
      assertEquals("Unable to convert", e.getMessage());
      verify(consumerService, never()).publishCustomerData(Mockito.any());
    }
  }
}
