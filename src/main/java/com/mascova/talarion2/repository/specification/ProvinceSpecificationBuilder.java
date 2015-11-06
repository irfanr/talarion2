package com.mascova.talarion2.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.mascova.talarion2.domain.Province;

public class ProvinceSpecificationBuilder {

  private final List<SearchCriteria> params;

  public ProvinceSpecificationBuilder() {
    params = new ArrayList<SearchCriteria>();
  }

  public ProvinceSpecificationBuilder with(String key, String operation, Object value) {
    params.add(new SearchCriteria(key, operation, value));
    return this;
  }

  public Specification<Province> build() {
    if (params.size() == 0) {
      return null;
    }

    List<Specification<Province>> specs = new ArrayList<Specification<Province>>();
    for (SearchCriteria param : params) {
      specs.add(new ProvinceSpecification(param));
    }

    Specification<Province> result = specs.get(0);
    for (int i = 1; i < specs.size(); i++) {
      result = Specifications.where(result).and(specs.get(i));
    }
    return result;
  }

}
