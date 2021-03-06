package com.learn.pkg.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learn.pkg.aop.ConsumerServiceAspectTest;
import com.learn.pkg.converter.KafkaCustomerDataRequestConverter;
import com.learn.pkg.dao.AuditLogRepository;
import com.learn.pkg.model.kafka.PublisherRequest;
import com.learn.pkg.util.ObjectMapperUtilTest;

@ExtendWith(MockitoExtension.class)
public class ConsumerServiceImplTest {

  @InjectMocks ConsumerServiceImpl consumerService;

  @Mock private AuditLogRepository auditLogRepository;

  @Mock private KafkaCustomerDataRequestConverter customerConsumerDataMasker;

  @Test
  public void testPublishCustomerData() {
    PublisherRequest publisherRequest =
        ConsumerServiceAspectTest.createPublisherRequest(
            ObjectMapperUtilTest.getCustomerData(), "transaction-id", "activity-id");
    consumerService.publishCustomerData(publisherRequest);

    verify(auditLogRepository, times(1)).save(Mockito.any());
  }

  @Test
  public void testPublishCustomerDataFailure() {
    PublisherRequest publisherRequest =
        ConsumerServiceAspectTest.createPublisherRequest(
            ObjectMapperUtilTest.getCustomerData(), "transaction-id", "activity-id");
    Mockito.when(auditLogRepository.save(Mockito.any()))
        .thenThrow(new ServiceException("Unable to persist"));

    assertThatExceptionOfType(ServiceException.class)
        .isThrownBy(
            () -> {
              consumerService.publishCustomerData(publisherRequest);
            })
        .withMessage("Unable to persist");
  }
}
