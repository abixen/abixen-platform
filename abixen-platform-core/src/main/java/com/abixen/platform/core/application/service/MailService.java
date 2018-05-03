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

package com.abixen.platform.core.application.service;

import com.abixen.platform.common.infrastructure.annotation.PlatformApplicationService;
import com.abixen.platform.core.infrastructure.configuration.properties.PlatformMailConfigurationProperties;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.context.ServletContextAware;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.Map;

@Slf4j
@PlatformApplicationService
public class MailService implements ServletContextAware {

    public static final String USER_ACCOUNT_ACTIVATION_MAIL = "userAccountActivationMail";

    public static final String USER_PASSWORD_CHANGE_MAIL = "userPasswordChangeMail";


    //FIXME - unused?
    private ServletContext servletContext;
    private final JavaMailSender mailSender;
    private final PlatformMailConfigurationProperties platformMailConfigurationProperties;

    @Autowired
    public MailService(JavaMailSender mailSender,
                       PlatformMailConfigurationProperties platformMailConfigurationProperties) {
        this.mailSender = mailSender;
        this.platformMailConfigurationProperties = platformMailConfigurationProperties;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Async
    public void sendMail(String to, Map<String, String> parameters, String template, String subject) {
        log.debug("sendMail() - to: " + to);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            String stringDir = MailService.class.getResource("/templates/freemarker").getPath();
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
            cfg.setDefaultEncoding("UTF-8");

            File dir = new File(stringDir);
            cfg.setDirectoryForTemplateLoading(dir);
            cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_22));

            StringBuffer content = new StringBuffer();
            Template temp = cfg.getTemplate(template + ".ftl");
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(temp, parameters));

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content.toString(), "text/html;charset=\"UTF-8\"");

            MimeMultipart multipart = new MimeMultipart("related");
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            message.setFrom(new InternetAddress(platformMailConfigurationProperties.getUser().getUsername(), platformMailConfigurationProperties.getUser().getName()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            mailSender.send(message);

            log.info("Message has been sent to: " + to);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}