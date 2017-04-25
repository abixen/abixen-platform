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

package com.abixen.platform.service.businessintelligence.multivisualisation.service.impl;

import com.abixen.platform.service.businessintelligence.multivisualisation.form.FileDataSourceForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.file.FileDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataSourceColumnWeb;
import com.abixen.platform.service.businessintelligence.multivisualisation.repository.FileDataSourceRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DataFileService;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DomainBuilderService;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.FileDataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class FileDataSourceServiceImpl implements FileDataSourceService {

    @Resource
    private FileDataSourceRepository fileDataSourceRepository;

    @Autowired
    private DomainBuilderService domainBuilderService;

    @Autowired
    private DataFileService dataFileService;

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
                .base(fileDataSourceForm.getName(), fileDataSourceForm.getDescription())
                .data(fileDataSourceForm.getColumns(), dataFileService.findDataFile(fileDataSourceForm.getDataFile().getId()))
                .build();
    }

    @Override
    public FileDataSourceForm createDataSource(FileDataSourceForm fileDataSourceForm) {
        FileDataSource fileDataSource = buildDataSource(fileDataSourceForm);
        return new FileDataSourceForm(createDataSource(fileDataSource));
    }

    @Override
    public FileDataSource createDataSource(FileDataSource fileDataSource) {
        log.debug("createDataSource() - fileDataSource: " + fileDataSource);
        FileDataSource createdFileDataSource = fileDataSourceRepository.save(fileDataSource);
        return createdFileDataSource;
    }

    @Override
    public FileDataSourceForm updateDataSource(FileDataSourceForm fileDataSourceForm) {
        log.debug("updateDataSource() - fileDataSourceForm: " + fileDataSourceForm);

        FileDataSource fileDataSource = findDataSource(fileDataSourceForm.getId());
        fileDataSource.setName(fileDataSourceForm.getName());
        fileDataSource.setDescription(fileDataSourceForm.getDescription());
        Set<DataSourceColumn> dataSourceColumns = new HashSet<>();
        List<String> oldColumnNames = fileDataSource.getColumns().stream()
                .map(DataSourceColumn::getName)
                .peek(s -> s = s.toUpperCase())
                .collect(Collectors.toList());
        List<String> newColumnNames = fileDataSourceForm.getColumns().stream()
                .map(DataSourceColumnWeb::getName)
                .peek(s -> s = s.toUpperCase())
                .collect(Collectors.toList());
        newColumnNames.replaceAll(String::toUpperCase);
        oldColumnNames.replaceAll(String::toUpperCase);
        List<DataSourceColumn> toRemove = fileDataSource.getColumns().stream().filter(dataSourceColumn -> !newColumnNames.contains(dataSourceColumn.getName().toUpperCase())).collect(Collectors.toList());
        List<DataSourceColumnWeb> toAdd = fileDataSourceForm.getColumns().stream().filter(dataSourceColumn -> !oldColumnNames.contains(dataSourceColumn.getName().toUpperCase())).collect(Collectors.toList());
        if (!toRemove.isEmpty()) {
            fileDataSource.removeColumns(new HashSet<>(toRemove));
        }
        if (!toAdd.isEmpty()) {
            convertDataSourceColumnWebToDataSourceColumn(fileDataSource, dataSourceColumns, toAdd);
            fileDataSource.addColumns(dataSourceColumns);
        }
        fileDataSource.setDataFile(dataFileService.findDataFile(fileDataSourceForm.getDataFile().getId()));
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

    @Override
    public void delateFileDataSource(Long id) {
        fileDataSourceRepository.delete(id);
    }

    private void convertDataSourceColumnWebToDataSourceColumn(FileDataSource databaseDataSource, Set<DataSourceColumn> dataSourceColumns, List<DataSourceColumnWeb> toAdd) {
        for (DataSourceColumnWeb dataSourceColumnWeb : toAdd) {
            DataSourceColumn dataSourceColumn = new DataSourceColumn();
            dataSourceColumn.setName(dataSourceColumnWeb.getName());
            dataSourceColumn.setPosition(dataSourceColumnWeb.getPosition());
            dataSourceColumn.setDataSource(databaseDataSource);
            dataSourceColumn.setDataValueType(dataSourceColumnWeb.getDataValueType());
            dataSourceColumns.add(dataSourceColumn);
        }
    }
}
