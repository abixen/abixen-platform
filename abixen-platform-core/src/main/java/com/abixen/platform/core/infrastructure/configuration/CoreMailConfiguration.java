/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.core.infrastructure.configuration;

import com.abixen.platform.core.infrastructure.configuration.properties.PlatformMailConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Slf4j
@Configuration
public class CoreMailConfiguration {

    @Autowired
    private PlatformMailConfigurationProperties platformMailConfigurationProperties;

    @Bean
    public JavaMailSender javaMailService() {
        log.debug("javaMailService()");
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(platformMailConfigurationProperties.getHost());
        javaMailSender.setPort(platformMailConfigurationProperties.getPort());
        javaMailSender.setUsername(platformMailConfigurationProperties.getUser().getUsername());
        javaMailSender.setPassword(platformMailConfigurationProperties.getUser().getPassword());

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", platformMailConfigurationProperties.getTransport().getProtocol());
        properties.setProperty("mail.smtp.auth", platformMailConfigurationProperties.getSmtp().getAuth() ? "true" : "false");
        properties.setProperty("mail.smtp.starttls.enable", platformMailConfigurationProperties.getSmtp().getStarttls().getEnable() ? "true" : "false");
        properties.setProperty("mail.debug", platformMailConfigurationProperties.getDebug() ? "true" : "false");
        return properties;
    }

}
