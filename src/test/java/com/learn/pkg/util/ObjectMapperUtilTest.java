package com.learn.pkg.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learn.pkg.model.kafka.KafkaCustomerAddress;
import com.learn.pkg.model.kafka.KafkaCustomerDataRequest;

@ExtendWith(MockitoExtension.class)
public class ObjectMapperUtilTest {

  @Test
  public void testGetJsonFromObj() {
    String strObj = ObjectMapperUtil.getJsonFromObj(getCustomerData());
    assertEquals(
        "{\"customerNumber\":\"C000000004\",\"firstName\":\"Ronald\",\"lastName\":\"Wesley\",\"birthdate\":\"26-12-2010\",\"country\":\"US\",\"countryCode\":null,\"mobileNumber\":\"9083618912\",\"email\":\"user@example.com\",\"customerStatus\":\"Restored\",\"address\":{\"addressLine1\":\"3/1 XYZ avenue,\",\"addressLine2\":\"Boston, USA\",\"street\":\"Storrow Dr road\",\"postalCode\":\"702215\"}}",
        strObj);
  }

  public static KafkaCustomerDataRequest getCustomerData() {
    KafkaCustomerDataRequest customer = new KafkaCustomerDataRequest();
    customer.setCustomerNumber("C000000004");
    customer.setFirstName("Ronald");
    customer.setLastName("Wesley");
    customer.setBirthdate("26-12-2010");
    customer.setCountry("USA");
    customer.setCountry("US");
    customer.setMobileNumber("9083618912");
    customer.setEmail("user@example.com");
    customer.setCustomerStatus("Restored");

    KafkaCustomerAddress address = new KafkaCustomerAddress();
    address.setAddressLine1("3/1 XYZ avenue,");
    address.setAddressLine2("Boston, USA");
    address.setStreet("Storrow Dr road");
    address.setPostalCode("702215");

    customer.setAddress(address);

    return customer;
  }
}
