package com.learn.pkg.aop;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.aspectj.lang.JoinPoint;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learn.pkg.converter.CustomerDataMasker;
import com.learn.pkg.dao.ErrorLogRepository;
import com.learn.pkg.model.kafka.PublisherRequest;
import com.learn.pkg.util.ObjectMapperUtilTest;

@ExtendWith(MockitoExtension.class)
public class ConsumerServiceAspectTest {

  @InjectMocks ConsumerServiceAspect consumerServiceAspect;

  @Mock private ErrorLogRepository errorLogRepository;

  @Mock private CustomerDataMasker customerConsumerDataMasker;

  @Mock(answer = Answers.RETURNS_MOCKS)
  private JoinPoint joinPoint;

  @Test
  public void testHandleThrownException() {
    PublisherRequest publisherRequest =
        new PublisherRequest(
            ObjectMapperUtilTest.getCustomerData(), "transaction-id", "activity-id");
    Mockito.when(joinPoint.getArgs()).thenReturn(new Object[] {publisherRequest});
    consumerServiceAspect.handleThrownException(
        joinPoint, new ServiceException("Unable to persist"));

    verify(errorLogRepository, times(1)).save(Mockito.any());
  }
}
