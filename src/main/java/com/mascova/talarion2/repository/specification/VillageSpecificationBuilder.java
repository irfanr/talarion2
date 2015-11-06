package com.mascova.talarion2.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import com.mascova.talarion2.domain.Village;

public class VillageSpecificationBuilder {

  private final List<SearchCriteria> params;

  public VillageSpecificationBuilder() {
    params = new ArrayList<SearchCriteria>();
  }

  public VillageSpecificationBuilder with(String key, String operation, Object value) {
    params.add(new SearchCriteria(key, operation, value));
    return this;
  }

  public Specification<Village> build() {
    if (params.size() == 0) {
      return null;
    }

    List<Specification<Village>> specs = new ArrayList<Specification<Village>>();
    for (SearchCriteria param : params) {
      specs.add(new VillageSpecification(param));
    }

    Specification<Village> result = specs.get(0);
    for (int i = 1; i < specs.size(); i++) {
      result = Specifications.where(result).and(specs.get(i));
    }
    return result;
  }

}
