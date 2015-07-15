package com.smartbean.service.impl;

import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.github.sd4324530.fastweixin.api.response.GetUsersResponse;
import com.google.common.collect.Lists;
import com.smartbean.common.CustomerStatus;
import com.smartbean.entity.Customer;
import com.smartbean.repository.CustomerRepository;
import com.smartbean.service.CustomerService;
import com.smartbean.util.weixin.WeixinConfig;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Value("#{configProperties['appid']}")
    private String appId;
    @Value("#{configProperties['appsecret']}")
    private String appSecret;

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findByOpenId(String openId) {
        Customer customer = new Customer();
        customer.setOpenId(openId);
        List<Customer> customerList = this.findAll(customer);
        if(customerList != null && customerList.size() == 1){
            return customerList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<Customer> findAll(final Customer customer) {
        return customerRepository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (StringUtils.isNotBlank(customer.getOpenId())) {
                    expressions.add(cb.equal(root.get("openId").as(String.class), customer.getOpenId()));
                }
                return predicate;
            }
        });
    }

    @Override
    public List<Customer> findAllFans() {
        WeixinConfig weixinConfig = new WeixinConfig();
        UserAPI userAPI = weixinConfig.getUserAPI(appId, appSecret);
        GetUsersResponse response = userAPI.getUsers(null);
        String[] openIds = response.getData().getOpenid();
        List<Customer> customerList = Lists.newArrayList();
        for(String openId : openIds){
            Customer customer = new Customer();
            GetUserInfoResponse rs = userAPI.getUserInfo(openId);
            customer.setOpenId(rs.getOpenid());
            customer.setAvatar(rs.getHeadimgurl());
            customer.setGender(rs.getSex());
            customer.setNickName(rs.getNickname());
            customer.setCity(rs.getCity());
            customer.setStatus(CustomerStatus.FOLLOW.toString());
            customer.setNickName(rs.getNickname());
            customer.setSubscribeTime(DateTime.now());
            customerList.add(customer);
        }
        customerList = customerRepository.save(customerList);

        return customerList;
    }
}
