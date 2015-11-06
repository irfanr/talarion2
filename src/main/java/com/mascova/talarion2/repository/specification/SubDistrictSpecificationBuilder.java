package com.mascova.talarion2.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.mascova.talarion2.domain.SubDistrict;

public class SubDistrictSpecificationBuilder {

  private final List<SearchCriteria> params;

  public SubDistrictSpecificationBuilder() {
    params = new ArrayList<SearchCriteria>();
  }

  public SubDistrictSpecificationBuilder with(String key, String operation, Object value) {
    params.add(new SearchCriteria(key, operation, value));
    return this;
  }

  public Specification<SubDistrict> build() {
    if (params.size() == 0) {
      return null;
    }

    List<Specification<SubDistrict>> specs = new ArrayList<Specification<SubDistrict>>();
    for (SearchCriteria param : params) {
      specs.add(new SubDistrictSpecification(param));
    }

    Specification<SubDistrict> result = specs.get(0);
    for (int i = 1; i < specs.size(); i++) {
      result = Specifications.where(result).and(specs.get(i));
    }
    return result;
  }

}
