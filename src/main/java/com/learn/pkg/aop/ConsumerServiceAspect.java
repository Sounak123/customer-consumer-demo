package com.learn.pkg.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.learn.pkg.converter.KafkaCustomerDataRequestConverter;
import com.learn.pkg.dao.ErrorLogRepository;
import com.learn.pkg.model.ErrorLog;
import com.learn.pkg.model.kafka.KafkaCustomerDataRequest;
import com.learn.pkg.model.kafka.PublisherRequest;
import com.learn.pkg.util.ObjectMapperUtil;

@Aspect
@Component
public class ConsumerServiceAspect {
  private static Logger logger = LoggerFactory.getLogger(ConsumerServiceAspect.class);

  @Autowired private ErrorLogRepository errorLogRepository;

  @Autowired private KafkaCustomerDataRequestConverter customerConsumerDataMasker;

  @AfterThrowing(
      pointcut = "execution(* com.learn.pkg.service.ConsumerServiceImpl.publishCustomerData(..))",
      throwing = "ex")
  public void handleThrownException(JoinPoint joinPoint, Throwable ex) {
    PublisherRequest request = (PublisherRequest) joinPoint.getArgs()[0];
    logger.error(
        "Error caught for, transaction-id:{}, and activity-id:{} ",
        request.getTransactionId(),
        request.getActivityId());

    ErrorLog errorLog = getErrorLogFromRequest(request.getCustomerData(), ex);
    errorLogRepository.save(errorLog);
    logger.info("Error logged successfully:{}", errorLog);
  }

  private ErrorLog getErrorLogFromRequest(
      KafkaCustomerDataRequest kafkaCustomerDataRequest, Throwable ex) {
    ErrorLog errorLog = new ErrorLog();
    errorLog.setErrorDescription(ex.getMessage());
    errorLog.setErrorType(ex.getClass().getName());
    errorLog.setPayload(
        ObjectMapperUtil.getJsonFromObj(
            customerConsumerDataMasker.convert(kafkaCustomerDataRequest)));

    return errorLog;
  }
}
