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

import com.abixen.platform.core.configuration.properties.AbstractPlatformResourceConfigurationProperties;
import com.abixen.platform.core.model.impl.Theme;
import com.abixen.platform.core.repository.ThemeRepository;
import com.abixen.platform.core.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.annotation.Resource;


@Transactional
@Service
public class ThemeServiceImpl implements ThemeService {

    private static Logger log = Logger.getLogger(ThemeServiceImpl.class.getName());

    private static final int BUFFER_SIZE = 4096;

    @Autowired
    private AbstractPlatformResourceConfigurationProperties platformResourceConfigurationProperties;

    @Resource
    private ThemeRepository themeRepository;

    @Override
    public org.springframework.data.domain.Page<Theme> findAllThemes(Pageable pageable) {
        log.debug("findAllThemes() - pageable: " + pageable);
        return themeRepository.findAll(pageable);
    }

    public Theme uploadTheme(MultipartFile file) throws IOException {

        log.debug("uploadTheme(MultipartFile file)");
        String fileName = null;
        String dateFormatString = new SimpleDateFormat("MMDDyyyyHHSS").format(new Date());

        try {
            //    Copy the zip over first.
            fileName = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(
                    new File(platformResourceConfigurationProperties.getImageLibraryDirectory() + "/" + fileName)));
            buffStream.write(bytes);
            buffStream.close();

            // Then extract using the same name.
            String zipFileLocation = platformResourceConfigurationProperties.getImageLibraryDirectory() + "/" + fileName;
            String extractDestination = platformResourceConfigurationProperties.getImageLibraryDirectory() + "/" + fileName + "_" + dateFormatString;

            //    Create the extract folder destination.
            File destDir = new File(extractDestination);
            if (!destDir.exists()) {
                destDir.mkdir();
            }
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFileLocation));
            ZipEntry entry = zipIn.getNextEntry();
            // iterates over entries in the zip file
            while (entry != null) {
                String filePath = extractDestination + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    // if the entry is a file, extracts it
                    extractFile(zipIn, filePath);
                } else {
                    // if the entry is a directory, make the directory
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
            zipIn.close();

        } catch (Exception e) {
            throw new IOException(e);
        }
        //    Temporarily return new theme.
        return new Theme();
    }

    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}