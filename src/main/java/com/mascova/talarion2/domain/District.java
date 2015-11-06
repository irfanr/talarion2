package com.mascova.talarion2.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class District implements Serializable {
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

  @OneToMany(mappedBy = "district")
  @JsonIgnore
  private Set<SubDistrict> subDistricts = new HashSet<>();

  @ManyToOne
  @JoinColumn(nullable = false, name = "province_id")
  private Province province;

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

  public Set<SubDistrict> getSubDistricts() {
    return subDistricts;
  }

  public void setSubDistricts(Set<SubDistrict> subDistricts) {
    this.subDistricts = subDistricts;
  }

  public Province getProvince() {
    return province;
  }

  public void setProvince(Province province) {
    this.province = province;
  }
}
