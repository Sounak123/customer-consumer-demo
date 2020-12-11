package com.learn.pkg.converter;

import org.springframework.stereotype.Component;

import com.learn.pkg.constants.MaskEnum;
import com.learn.pkg.model.kafka.KafkaCustomerAddress;
import com.learn.pkg.model.kafka.KafkaCustomerDataRequest;

@Component
public class KafkaCustomerDataRequestConverter implements Converter<KafkaCustomerDataRequest> {

  @Override
  public KafkaCustomerDataRequest convert(KafkaCustomerDataRequest object) {

    KafkaCustomerDataRequest kafkaCustomerRequest = createNewKafkaCustomerRequestObject(object);

    maskFields(kafkaCustomerRequest);

    return kafkaCustomerRequest;
  }

  private void maskFields(KafkaCustomerDataRequest kafkaCustomerRequest) {
    kafkaCustomerRequest.setBirthdate(
        kafkaCustomerRequest
            .getBirthdate()
            .replaceAll(MaskEnum.DOB_MASK_PATTERN.getValue(), MaskEnum.DOB_REPLACEMENT.getValue()));

    kafkaCustomerRequest.setCustomerNumber(
        kafkaCustomerRequest
            .getCustomerNumber()
            .replaceAll(
                MaskEnum.CUSTOMER_NUMBER_MASK_PATTERN.getValue(), MaskEnum.REPLACEMENT.getValue()));
    kafkaCustomerRequest.setEmail(
        kafkaCustomerRequest
            .getEmail()
            .replaceAll(MaskEnum.EMAIL_MASK_PATTERN.getValue(), MaskEnum.REPLACEMENT.getValue()));
  }

  private KafkaCustomerDataRequest createNewKafkaCustomerRequestObject(
      KafkaCustomerDataRequest object) {
    KafkaCustomerDataRequest customer = new KafkaCustomerDataRequest();
    customer.setCustomerNumber(object.getCustomerNumber());
    customer.setFirstName(object.getFirstName());
    customer.setLastName(object.getLastName());
    customer.setBirthdate(object.getBirthdate());
    customer.setCountry(object.getCountry());
    customer.setCountryCode(object.getCountryCode());
    customer.setMobileNumber(object.getMobileNumber());
    customer.setEmail(object.getEmail());
    customer.setCustomerStatus(object.getCustomerStatus());

    KafkaCustomerAddress address = new KafkaCustomerAddress();
    address.setAddressLine1(object.getAddress().getAddressLine1());
    address.setAddressLine2(object.getAddress().getAddressLine2());
    address.setStreet(object.getAddress().getStreet());
    address.setPostalCode(object.getAddress().getPostalCode());

    customer.setAddress(address);

    return customer;
  }
}
