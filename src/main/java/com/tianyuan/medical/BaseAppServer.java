package com.tianyuan.medical;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tianyuan.core.EntityFreamwork;
import com.tianyuan.repository.DeptRepository;
import com.tianyuan.repository.DictionariesRepository;
import com.tianyuan.repository.HospitalSericeRepository;
import com.tianyuan.repository.NurseryRepository;
import com.tianyuan.repository.OrganizationRepository;
import com.tianyuan.repository.ProtocolRepository;
import com.tianyuan.repository.ServiceItemsRepository;
@Configuration
@EnableCaching

public class BaseAppServer extends CachingConfigurerSupport {

	@Bean
	public EntityFreamwork entityFreamwork() {
		return new EntityFreamwork();
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
	DictionariesRepository dictionariesRepository() {
		return new DictionariesRepository();
	}
	@Bean
	HospitalSericeRepository hospitalSericeRepository() {
		return new HospitalSericeRepository();
	}
	@Bean 
	NurseryRepository nurseryRepository() {
		return new NurseryRepository();
	}
	@Bean
	ServiceItemsRepository serviceItemsRepository() {
		return new ServiceItemsRepository();
	}
	@Bean
	ProtocolRepository protocolRepository() {
		return new ProtocolRepository();
	}
}
