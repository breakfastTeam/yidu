package com.smartbean.repository;

import com.smartbean.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2015/6/24.
 */
public interface CustomerRepository  extends JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer> {}
