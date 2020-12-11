package com.learn.pkg.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learn.pkg.aop.ConsumerServiceAspectTest;
import com.learn.pkg.converter.KafkaCustomerDataRequestConverter;
import com.learn.pkg.model.kafka.PublisherRequest;
import com.learn.pkg.util.ObjectMapperUtilTest;

@ExtendWith(MockitoExtension.class)
public class KafkaSubscriberServiceImplTest {

  @InjectMocks private KafkaSubscriberServiceImpl subscriberService;

  @Mock private KafkaCustomerDataRequestConverter customerConsumerDataMasker;

  @Mock private ConsumerService consumerService;

  @Test
  public void testSubscribe() {
    PublisherRequest publisherRequest =
        ConsumerServiceAspectTest.createPublisherRequest(
            ObjectMapperUtilTest.getCustomerData(), "transaction-id", "activity-id");
    subscriberService.subscribe(publisherRequest);

    verify(consumerService, times(1)).publishCustomerData(Mockito.any());
  }

  @Test
  public void testSubscribeFailure() {
    PublisherRequest publisherRequest =
        ConsumerServiceAspectTest.createPublisherRequest(
            ObjectMapperUtilTest.getCustomerData(), "transaction-id", "activity-id");
    Mockito.when(customerConsumerDataMasker.convert(Mockito.any()))
        .thenThrow(new RuntimeException("Unable to convert"));

    assertThatExceptionOfType(RuntimeException.class)
        .isThrownBy(
            () -> {
              subscriberService.subscribe(publisherRequest);
            })
        .withMessage("Unable to convert");

    verify(consumerService, never()).publishCustomerData(Mockito.any());
  }
}
