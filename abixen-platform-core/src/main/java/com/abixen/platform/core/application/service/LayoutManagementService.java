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

import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.common.infrastructure.annotation.PlatformApplicationService;
import com.abixen.platform.common.infrastructure.security.PlatformUser;
import com.abixen.platform.core.application.converter.LayoutToLayoutDtoConverter;
import com.abixen.platform.core.application.dto.LayoutDto;
import com.abixen.platform.core.application.form.LayoutForm;
import com.abixen.platform.core.application.form.LayoutSearchForm;
import com.abixen.platform.core.domain.model.Layout;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.domain.service.LayoutService;
import com.abixen.platform.core.domain.service.UserService;
import com.abixen.platform.core.infrastructure.configuration.properties.PlatformResourceConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Slf4j
@PlatformApplicationService
public class LayoutManagementService {

    private final SecurityService securityService;
    private final UserService userService;
    private final LayoutService layoutService;
    private final PlatformResourceConfigurationProperties platformResourceConfigurationProperties;
    private final LayoutToLayoutDtoConverter layoutToLayoutDtoConverter;


    @Autowired
    public LayoutManagementService(SecurityService securityService,
                                   UserService userService,
                                   LayoutService layoutService,
                                   PlatformResourceConfigurationProperties platformResourceConfigurationProperties,
                                   LayoutToLayoutDtoConverter layoutToLayoutDtoConverter) {
        this.securityService = securityService;
        this.userService = userService;
        this.layoutService = layoutService;
        this.platformResourceConfigurationProperties = platformResourceConfigurationProperties;
        this.layoutToLayoutDtoConverter = layoutToLayoutDtoConverter;
    }

    @PreAuthorize("hasPermission('" + AclClassName.Values.LAYOUT + "', '" + PermissionName.Values.LAYOUT_ADD + "')")
    public LayoutForm createLayout(final LayoutForm layoutForm) {
        log.debug("createLayout() - layoutForm: {}", layoutForm);

        final Layout layout = Layout.builder()
                .title(layoutForm.getTitle())
                .content(layoutForm.getContent())
                .iconFileName(layoutForm.getIconFileName())
                .build();

        final Layout createdLayout = layoutService.create(layout);

        return new LayoutForm(createdLayout);
    }

    @PreAuthorize("hasPermission(#layoutForm.id, '" + AclClassName.Values.LAYOUT + "', '" + PermissionName.Values.LAYOUT_EDIT + "')")
    public LayoutForm updateLayout(final LayoutForm layoutForm) {
        log.debug("updateLayout() - layoutForm: {}", layoutForm);

        final Layout layout = layoutService.find(layoutForm.getId());
        layout.changeContent(layoutForm.getContent());
        layout.changeTitle(layoutForm.getTitle());
        //TODO - needed?
        layout.changeIconFileName(layoutForm.getIconFileName());

        final Layout updatedLayout = layoutService.update(layout);

        return new LayoutForm(updatedLayout);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.LAYOUT + "', '" + PermissionName.Values.LAYOUT_DELETE + "')")
    public void deleteLayout(final Long id) {
        log.debug("deleteLayout() - id={}", id);
        layoutService.delete(id);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.LAYOUT + "', '" + PermissionName.Values.LAYOUT_VIEW + "')")
    public LayoutDto findLayout(final Long id) {
        log.debug("findLayout() - id: {}", id);

        final Layout layout = layoutService.find(id);

        return layoutToLayoutDtoConverter.convert(layout);
    }

    public List<LayoutDto> findAllLayouts() {
        log.debug("findAllLayouts()");

        final PlatformUser platformAuthorizedUser = securityService.getAuthorizedUser();
        final User authorizedUser = userService.find(platformAuthorizedUser.getId());

        final List<Layout> layouts = layoutService.findAll(authorizedUser);

        return layoutToLayoutDtoConverter.convertToList(layouts);
    }

    public Page<LayoutDto> findAllLayouts(final Pageable pageable, final LayoutSearchForm layoutSearchForm) {
        log.debug("findAllLayouts() - pageable: {}, layoutSearchForm: {}", pageable, layoutSearchForm);

        final PlatformUser platformAuthorizedUser = securityService.getAuthorizedUser();
        final User authorizedUser = userService.find(platformAuthorizedUser.getId());
        final Page<Layout> layouts = layoutService.findAll(pageable, layoutSearchForm, authorizedUser);

        return layoutToLayoutDtoConverter.convertToPage(layouts);
    }

    @PreAuthorize("hasPermission(#id, '" + AclClassName.Values.LAYOUT + "', '" + PermissionName.Values.LAYOUT_EDIT + "')")
    public LayoutDto changeLayoutIcon(final Long id, final MultipartFile iconFile) throws IOException {
        log.debug("changeLayoutIcon() - id: {}, iconFile: {}", id, iconFile);

        final Layout layout = layoutService.find(id);
        //FIXME - rename to thumbnail
        final File currentThumbnailFile = new File(platformResourceConfigurationProperties.getImageLibraryDirectory() + "/layout-miniature/" + layout.getIconFileName());

        if (currentThumbnailFile.exists()) {
            if (!currentThumbnailFile.delete()) {
                throw new FileExistsException();
            }
        }

        final PasswordEncoder encoder = new BCryptPasswordEncoder();
        final String newIconFileName = encoder.encode(iconFile.getName() + new Date().getTime()).replaceAll("\"", "s").replaceAll("/", "a").replace(".", "sde");
        final File newIconFile = new File(platformResourceConfigurationProperties.getImageLibraryDirectory() + "/layout-miniature/" + newIconFileName);

        final FileOutputStream out = new FileOutputStream(newIconFile);
        out.write(iconFile.getBytes());
        out.close();

        layout.changeIconFileName(newIconFileName);
        final Layout updatedLayout = layoutService.update(layout);

        return layoutToLayoutDtoConverter.convert(updatedLayout);
    }

}