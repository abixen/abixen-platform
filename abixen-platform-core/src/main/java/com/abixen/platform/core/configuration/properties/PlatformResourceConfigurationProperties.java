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

package com.abixen.platform.core.configuration.properties;

import org.bouncycastle.util.io.StreamOverflowException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;


@Configuration
@Component
@EnableConfigurationProperties(PlatformResourceConfigurationProperties.class)
@ConfigurationProperties(prefix = "platform.core.resource", locations = {"bootstrap.yml"})
public class PlatformResourceConfigurationProperties {

    @NotNull
    private String imageLibraryDirectory;

    @NotNull
    private String avatarLibraryDirectory;

    public String getImageLibraryDirectory() {
        return imageLibraryDirectory;
    }

    public void setImageLibraryDirectory(String imageLibraryDirectory) {
        this.imageLibraryDirectory = imageLibraryDirectory;
    }

    public String getAvatarLibraryDirectory() {
        return resolvePath(avatarLibraryDirectory);
    }

    public void setAvatarLibraryDirectory(String avatarLibraryDirectory) {
        this.avatarLibraryDirectory = avatarLibraryDirectory;
    }



    private String resolvePath(String path){
        String resolvedPath = path;
        if (path.contains("${baseDir}")){
            resolvedPath = resolveBaseDirPath(resolvedPath);
        }
        return resolvedPath;
    }

    private String resolveBaseDirPath(String path){
            return path.replace("${baseDir}",System.getProperty("user.dir"));
    }
}
