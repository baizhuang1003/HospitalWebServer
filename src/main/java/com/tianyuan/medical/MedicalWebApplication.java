package com.tianyuan.medical;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tianyuan.core.EntityFreamwork;



@ComponentScan({ "com.tianyuan.controller"})
@SpringBootApplication
public class MedicalWebApplication extends BaseAppServer implements WebMvcConfigurer, CommandLineRunner  {

	public static void main(String[] args) {
		SpringApplication.run(MedicalWebApplication.class, args);
	}
	
	@Value("${file.upload.liunx}")
	String linuxFilePath;
	@Value("${file.upload.windows}")
	String windowsFilePath;
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		String filePath = "";
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win"))// windows 系统
			filePath = windowsFilePath;
		else if (os.toLowerCase().startsWith("mac"))// mac os系统
			filePath = System.getProperty("user.home") + "/wisdom/";
		else // linux 系统
			filePath = linuxFilePath;

		registry.addResourceHandler("/**").addResourceLocations("file:" + filePath)
				.addResourceLocations("classpath:/META-INF/resources/").addResourceLocations("classpath:/resources/")
				.addResourceLocations("classpath:/static/").addResourceLocations("classpath:/public/")
				.addResourceLocations("classpath:/META-INF/resources/webjars/")
				.addResourceLocations("classpath:/templates/");
	}
	
	//解决IE下载JSON问题
	public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
		MediaType media = new MediaType(MediaType.TEXT_HTML, Charset.forName("UTF-8"));
		supportedMediaTypes.add(media);
		jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
		return jsonConverter;
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		converters.add(customJackson2HttpMessageConverter());
		//WebMvcConfigurer.super.configureMessageConverters(converters);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		timer.schedule(timerTask, 3000, 15000);// 每15秒执行检测数据库
	}
	
	@Autowired
	EntityFreamwork ef;
	// 定时器
	private Timer timer = new Timer();
	private TimerTask timerTask = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ef.connectionTest();
		}
	};
}
