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

package com.abixen.platform.module.chart.service.impl;

import com.abixen.platform.module.chart.form.FileDataSourceForm;
import com.abixen.platform.module.chart.model.impl.DataSourceColumn;
import com.abixen.platform.module.chart.model.impl.FileDataSource;
import com.abixen.platform.module.chart.repository.FileDataSourceRepository;
import com.abixen.platform.module.chart.service.DomainBuilderService;
import com.abixen.platform.module.chart.service.FileDataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service
public class FileDataSourceServiceImpl implements FileDataSourceService {

    private final Logger log = LoggerFactory.getLogger(FileDataSourceServiceImpl.class);

    @Resource
    private FileDataSourceRepository fileDataSourceRepository;

    @Autowired
    DomainBuilderService domainBuilderService;

    @Override
    public Set<DataSourceColumn> getDataSourceColumns(Long dataSourceId) {
        Set<DataSourceColumn> result;
        FileDataSource fileDataSource = fileDataSourceRepository.getOne(dataSourceId);
        result = fileDataSource.getColumns();
        return result;
    }

    @Deprecated
    @Override
    public Page<FileDataSource> getDataSources(String jsonCriteria, Pageable pageable) {
        Page<FileDataSource> result;
        //TODO - needs with criteria?
        //result = fileDataSourceRepository.findAllByJsonCriteria(jsonCriteria, pageable);
        result = fileDataSourceRepository.findAll(pageable);
        return result;
    }

    @Override
    public Page<FileDataSource> findAllDataSources(Pageable pageable) {
        return fileDataSourceRepository.findAll(pageable);
    }

    @Override
    public List<Map<String, Integer>> getAllColumns(Long dataSourceId) {

        List<Map<String, Integer>> result = new ArrayList<>();
        FileDataSource fileDataSource = fileDataSourceRepository.getOne(dataSourceId);

        for (DataSourceColumn dataSourceColumn : fileDataSource.getColumns()) {
            String name = dataSourceColumn.getName();
            Integer position = dataSourceColumn.getPosition();
            Map<String, Integer> column = new HashMap<>(1);
            column.put(name, position);
            result.add(column);
        }
        return result;
    }

    @Override
    public FileDataSource buildDataSource(FileDataSourceForm fileDataSourceForm) {
        log.debug("buildDataSource() - fileDataSourceForm: " + fileDataSourceForm);
        return domainBuilderService.newFileDataSourceBuilderInstance()
                .base(fileDataSourceForm.getName(), fileDataSourceForm.getDescription(), fileDataSourceForm.getDataSourceFileType())
                .build();
    }

    @Override
    public FileDataSourceForm createDataSource(FileDataSourceForm fileDataSourceForm) {
        FileDataSource fileDataSource = buildDataSource(fileDataSourceForm);
        return new FileDataSourceForm(updateDataSource(createDataSource(fileDataSource)));
    }

    @Override
    public FileDataSource createDataSource(FileDataSource fileDataSource) {
        log.debug("createDataSource() - fileDataSource: " + fileDataSource);
        FileDataSource createdFileDataSource = fileDataSourceRepository.save(fileDataSource);
        /*aclService.insertDefaultAcl(createdPage, new ArrayList<PermissionName>() {{
            add(PermissionName.PAGE_VIEW);
            add(PermissionName.PAGE_EDIT);
            add(PermissionName.PAGE_DELETE);
            add(PermissionName.PAGE_CONFIGURATION);
            add(PermissionName.PAGE_PERMISSION);
        }});*/
        return createdFileDataSource;
    }

    @Override
    public FileDataSourceForm updateDataSource(FileDataSourceForm fileDataSourceForm) {
        log.debug("updateDataSource() - fileDataSourceForm: " + fileDataSourceForm);

        FileDataSource fileDataSource = findDataSource(fileDataSourceForm.getId());
        fileDataSource.setName(fileDataSourceForm.getName());

        return new FileDataSourceForm(updateDataSource(fileDataSource));
    }

    @Override
    public FileDataSource updateDataSource(FileDataSource fileDataSource) {
        log.debug("updateDataSource() - fileDataSource: " + fileDataSource);
        return fileDataSourceRepository.save(fileDataSource);
    }

    @Override
    public FileDataSource findDataSource(Long id) {
        log.debug("findPage() - id: " + id);
        return fileDataSourceRepository.findOne(id);
    }
}
