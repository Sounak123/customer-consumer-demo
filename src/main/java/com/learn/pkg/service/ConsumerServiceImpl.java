package com.learn.pkg.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.pkg.converter.KafkaCustomerDataRequestConverter;
import com.learn.pkg.dao.AuditLogRepository;
import com.learn.pkg.model.AuditLog;
import com.learn.pkg.model.kafka.KafkaCustomerDataRequest;
import com.learn.pkg.model.kafka.PublisherRequest;
import com.learn.pkg.util.ObjectMapperUtil;

@Service
public class ConsumerServiceImpl implements ConsumerService {
  private static Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);

  @Autowired private AuditLogRepository auditLogRepository;

  @Autowired private KafkaCustomerDataRequestConverter customerConsumerDataMasker;

  @Override
  public void publishCustomerData(PublisherRequest publisherRequest) {
    AuditLog auditLog = createAuditLog(publisherRequest.getCustomerData());
    auditLogRepository.save(auditLog);
    logger.info(
        "Request successfully persisted in database, transaction-id:{} and activity-id",
        publisherRequest.getTransactionId(),
        publisherRequest.getActivityId());
  }

  private AuditLog createAuditLog(KafkaCustomerDataRequest payload) {
    AuditLog auditLog = new AuditLog();
    auditLog.setCustomerNumber(payload.getCustomerNumber());
    auditLog.setPayload(
        ObjectMapperUtil.getJsonFromObj(customerConsumerDataMasker.convert(payload)));
    return auditLog;
  }
}
