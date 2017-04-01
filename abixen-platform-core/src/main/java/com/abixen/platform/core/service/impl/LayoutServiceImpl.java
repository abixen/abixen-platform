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

package com.abixen.platform.core.service.impl;

import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.common.security.PlatformUser;
import com.abixen.platform.core.configuration.properties.AbstractPlatformResourceConfigurationProperties;
import com.abixen.platform.core.form.LayoutForm;
import com.abixen.platform.core.form.LayoutSearchForm;
import com.abixen.platform.core.model.impl.Layout;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.repository.LayoutRepository;
import com.abixen.platform.core.service.LayoutService;
import com.abixen.platform.core.service.SecurityService;
import com.abixen.platform.core.service.UserService;
import com.abixen.platform.core.util.LayoutColumnUtil;
import com.abixen.platform.core.util.LayoutRowUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileExistsException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class LayoutServiceImpl implements LayoutService {

    private final SecurityService securityService;
    private final UserService userService;
    private final AbstractPlatformResourceConfigurationProperties platformResourceConfigurationProperties;
    private final LayoutRepository layoutRepository;


    @Autowired
    public LayoutServiceImpl(SecurityService securityService,
                             UserService userService,
                             AbstractPlatformResourceConfigurationProperties platformResourceConfigurationProperties,
                             LayoutRepository layoutRepository) {
        this.securityService = securityService;
        this.userService = userService;
        this.platformResourceConfigurationProperties = platformResourceConfigurationProperties;
        this.layoutRepository = layoutRepository;
    }

    @PreAuthorize("hasPermission('" + AclClassName.Values.LAYOUT + "', '" + PermissionName.Values.LAYOUT_ADD + "')")
    @Override
    public Layout createLayout(Layout layout) {
        log.debug("createLayout() - layout={}", layout);
        return layoutRepository.save(layout);
    }

    @PreAuthorize("hasPermission(#layout.id, '" + AclClassName.Values.LAYOUT + "', '" + PermissionName.Values.LAYOUT_EDIT + "')")
    @Override
    public Layout updateLayout(Layout layout) {
        log.debug("updateLayout() - layout={}", layout);
        return layoutRepository.save(layout);
    }

    @PreAuthorize("hasPermission(#layoutForm.id, '" + AclClassName.Values.LAYOUT + "', '" + PermissionName.Values.LAYOUT_EDIT + "')")
    @Override
    public LayoutForm updateLayout(LayoutForm layoutForm) {
        log.debug("updateLayout() - layoutForm: " + layoutForm);
        Layout layout = findLayout(layoutForm.getId());
        layout.setContent(layoutForm.getContent());
        layout.setTitle(layoutForm.getTitle());
        layout.setIconFileName(layoutForm.getIconFileName());
        return new LayoutForm(updateLayout(layout));
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.LAYOUT + "', '" + PermissionName.Values.LAYOUT_DELETE + "')")
    @Override
    public void deleteLayout(Long id) {
        log.debug("deleteLayout() - id={}", id);
        layoutRepository.delete(id);
    }

    @Override
    public String htmlLayoutToJson(String htmlString) {

        log.debug("htmlLayoutToJson() - htmlString={}", htmlString);

        Document doc = Jsoup.parse(htmlString);
        Elements htmlRows = doc.getElementsByClass("row");
        List<LayoutRowUtil> rowUtilList = new ArrayList<>();

        for (Element row : htmlRows) {

            Document rowDoc = Jsoup.parse(row.toString());
            Elements htmlColumns = rowDoc.getElementsByClass("column");
            List<LayoutColumnUtil> columnUtilList = new ArrayList<>();

            for (Element column : htmlColumns) {
                String styleClass = column.attr("class");
                columnUtilList.add(new LayoutColumnUtil(styleClass.substring(styleClass.indexOf(" ") + 1)));
            }

            rowUtilList.add(new LayoutRowUtil(columnUtilList));
        }

        return "{\"rows\":" + new Gson().toJson(rowUtilList) + "}";
    }

    @Override
    public Page<Layout> findAllLayouts(Pageable pageable, LayoutSearchForm layoutSearchForm) {
        PlatformUser platformAuthorizedUser = securityService.getAuthorizedUser();
        User authorizedUser = userService.findUser(platformAuthorizedUser.getId());

        return layoutRepository.findAllSecured(pageable, layoutSearchForm, authorizedUser, PermissionName.LAYOUT_VIEW);
    }

    @Override
    public List<Layout> findAllLayouts() {
        PlatformUser platformAuthorizedUser = securityService.getAuthorizedUser();
        User authorizedUser = userService.findUser(platformAuthorizedUser.getId());

        return layoutRepository.findAllSecured(authorizedUser, PermissionName.LAYOUT_VIEW);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.LAYOUT + "', '" + PermissionName.Values.LAYOUT_VIEW + "')")
    @Override
    public Layout findLayout(Long id) {
        log.debug("findLayout() - id={}", id);
        return layoutRepository.findOne(id);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.LAYOUT + "', '" + PermissionName.Values.LAYOUT_EDIT + "')")
    @Override
    public Layout changeIcon(Long id, MultipartFile iconFile) throws IOException {
        Layout layout = findLayout(id);
        File currentAvatarFile = new File(platformResourceConfigurationProperties.getImageLibraryDirectory() + "/layout-miniature/" + layout.getIconFileName());
        if (currentAvatarFile.exists()) {
            if (!currentAvatarFile.delete()) {
                throw new FileExistsException();
            }
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String newIconFileName = encoder.encode(iconFile.getName() + new Date().getTime()).replaceAll("\"", "s").replaceAll("/", "a").replace(".", "sde");
        File newIconFile = new File(platformResourceConfigurationProperties.getImageLibraryDirectory() + "/layout-miniature/" + newIconFileName);
        FileOutputStream out = new FileOutputStream(newIconFile);
        out.write(iconFile.getBytes());
        out.close();
        layout.setIconFileName(newIconFileName);
        updateLayout(layout);
        return findLayout(id);
    }

    @Override
    public void convertPageLayoutToJson(com.abixen.platform.core.model.impl.Page page) {
        String html = page.getLayout().getContent();
        page.getLayout().setContentAsJson(htmlLayoutToJson(html));
    }
}