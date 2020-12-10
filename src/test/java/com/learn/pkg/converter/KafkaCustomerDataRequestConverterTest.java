package com.learn.pkg.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learn.pkg.model.kafka.KafkaCustomerDataRequest;
import com.learn.pkg.util.ObjectMapperUtilTest;

@ExtendWith(MockitoExtension.class)
public class KafkaCustomerDataRequestConverterTest {

  @InjectMocks private KafkaCustomerDataRequestConverter customerConsumerDataMasker;

  @Test
  public void testConvert() {
    KafkaCustomerDataRequest maskedData =
        customerConsumerDataMasker.convert(ObjectMapperUtilTest.getCustomerData());
    assertEquals("C00000****", maskedData.getCustomerNumber());
    assertEquals("XX-XX-2010", maskedData.getBirthdate());
    assertEquals("****@example.com", maskedData.getEmail());
  }
}
