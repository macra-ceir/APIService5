package com.gl.mdr.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesReader {

	@Value("${spring.jpa.properties.hibernate.dialect}")
	public String dialect;
	
//	@Value("${date.view.format}")
//	public String dateViewFormat;
	
	@Value("${local-ip}")
	public String localIp;
	
	@Value("#{new Integer('${serverId}')}")
	public Integer serverId;
}
