package com.kiki.target.module.exchange;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kiki.target.common.model.Exchange;

/**
 * Title:
 * Description:
 * @author jjtEatJava
 * @date 2018年2月5日
 */
public interface ExchangeDao extends JpaRepository<Exchange, String>,JpaSpecificationExecutor<Exchange>{

}
