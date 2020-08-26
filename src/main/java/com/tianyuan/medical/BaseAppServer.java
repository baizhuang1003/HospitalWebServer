package com.tianyuan.medical;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.repository.DeptRepository;
import com.tianyuan.repository.LimitRepository;
import com.tianyuan.repository.NurseryRepository;
import com.tianyuan.repository.OrganizationRepository;
import com.tianyuan.repository.ParameterRepository;
import com.tianyuan.repository.RegionRepository;
import com.tianyuan.repository.RoleRepository;
import com.tianyuan.repository.ServiceRepository;
import com.tianyuan.repository.SmsRepository;
import com.tianyuan.repository.UserRepository;
@Configuration
@EnableCaching

public class BaseAppServer extends CachingConfigurerSupport {

	@Bean
	public EntityFreamwork entityFreamwork() {
		return new EntityFreamwork();
	}
	
	@Bean
	UserRepository userRepository() {
		return new UserRepository();
	}
	@Bean
	LimitRepository limitRepository() {
		return new LimitRepository();
	}
	@Bean
	RoleRepository roleRepository() {
		return new RoleRepository();
	}
	@Bean
	SmsRepository smsRepository() {
		return new SmsRepository();
	}
	@Bean
	OrganizationRepository organizationRepository() {
		return new OrganizationRepository();
	}
	@Bean
	DeptRepository deptRepository() {
		return new DeptRepository();
	}
	@Bean
	NurseryRepository nurseryRepository() {
		return new NurseryRepository();
	}
	@Bean
	ServiceRepository serviceRepository() {
		return new ServiceRepository();
	}
	@Bean
	ParameterRepository parameterRepository() {
		return new ParameterRepository();
	}
	@Bean
	RegionRepository regionRepository() {
		return new RegionRepository();
	}
	
}
