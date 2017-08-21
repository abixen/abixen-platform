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

package com.abixen.platform.core.infrastructure.configuration.properties;


import javax.validation.constraints.NotNull;

public abstract class AbstractPlatformResourceConfigurationProperties {

    @NotNull
    private String imageLibraryDirectory;

    @NotNull
    private String themesDirectory;

    public String getImageLibraryDirectory() {
        return resolvePath(imageLibraryDirectory);
    }

    public String getThemesDirectory() {
        return resolvePath(themesDirectory);
    }

    public void setImageLibraryDirectory(String imageLibraryDirectory) {
        this.imageLibraryDirectory = imageLibraryDirectory;
    }

    public void setThemesDirectory(String themesDirectory) {
        this.themesDirectory = themesDirectory;
    }

    private String resolvePath(String path) {
        String resolvedPath = path;
        if (path.contains("${baseDir}")) {
            resolvedPath = resolveBaseDirPath(resolvedPath);
        }
        return resolvedPath;
    }

    private String resolveBaseDirPath(String path) {
        return path.replace("${baseDir}", System.getProperty("user.dir"));
    }
}