package com.learn.pkg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AUDIT_LOG")
public class AuditLog {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "customer_number")
  private String customerNumber;

  @Column(columnDefinition = "JSON")
  private String payload;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCustomerNumber() {
    return customerNumber;
  }

  public void setCustomerNumber(String customerNumber) {
    this.customerNumber = customerNumber;
  }

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }
}
