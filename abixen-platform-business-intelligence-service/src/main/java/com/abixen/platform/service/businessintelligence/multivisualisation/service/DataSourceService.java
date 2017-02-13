package com.abixen.platform.service.businessintelligence.multivisualisation.service;

import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.datasource.DataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DataSourceService {

    Page<DataSource> getDataSources(Pageable pageable);

    DataSource findDataSource(Long id);

}
