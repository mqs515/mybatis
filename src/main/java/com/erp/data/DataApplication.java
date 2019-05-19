package com.erp.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @Author: Mqs
 * @Date: 2018/11/8 12:59
 * @Description: 
 */
@SpringBootApplication
@MapperScan("com.erp.data.dao")
public class DataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }
}
