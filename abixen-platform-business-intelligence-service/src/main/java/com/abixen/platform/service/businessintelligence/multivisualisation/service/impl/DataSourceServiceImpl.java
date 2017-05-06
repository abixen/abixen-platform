package com.abixen.platform.service.businessintelligence.multivisualisation.service.impl;

import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.repository.DataSourceRepository;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.DataSourceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Resource;

public class DataSourceServiceImpl implements DataSourceService {

    @Resource
    private DataSourceRepository dataSourceRepository;

    @Override
    public Page<DataSource> findAllDataSources(Pageable pageable) {
        Page<DataSource> result;
        result = dataSourceRepository.findAll(pageable);
        return result;
    }

    @Override
    public DataSource findDataSource(Long id) {
        return dataSourceRepository.findOne(id);
    }
}
