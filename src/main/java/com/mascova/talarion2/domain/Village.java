package com.mascova.talarion2.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
public class Village implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Version
  private Long version;

  @NotNull
  private String name;

  @ManyToOne
  @JoinColumn(nullable = false, name = "sub_district_id")
  private SubDistrict subDistrict;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SubDistrict getSubDistrict() {
    return subDistrict;
  }

  public void setSubDistrict(SubDistrict subDistrict) {
    this.subDistrict = subDistrict;
  }
}
