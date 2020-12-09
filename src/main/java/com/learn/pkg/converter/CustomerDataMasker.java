package com.learn.pkg.converter;

import org.springframework.stereotype.Component;

import com.learn.pkg.constants.MaskEnum;
import com.learn.pkg.model.kafka.KafkaCustomerDataRequest;

@Component
public class CustomerDataMasker implements Converter<KafkaCustomerDataRequest> {

  @Override
  public KafkaCustomerDataRequest convert(KafkaCustomerDataRequest object) {

    KafkaCustomerDataRequest kafkaCustomerRequest = new KafkaCustomerDataRequest(object);

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
}
