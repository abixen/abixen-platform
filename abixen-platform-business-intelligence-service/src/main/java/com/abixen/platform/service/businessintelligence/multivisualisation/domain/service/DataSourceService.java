package com.abixen.platform.service.businessintelligence.multivisualisation.domain.service;

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DataSourceType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DataSourceService {

    DataSource find(final Long id);

    Page<DataSource> findAll(final Pageable pageable, final DataSourceType dataSourceType);

    DataSource create(final DataSource dataSourceForm);

    DataSource update(final DataSource dataSourceForm);

    void delete(final Long dataSourceId);

}
