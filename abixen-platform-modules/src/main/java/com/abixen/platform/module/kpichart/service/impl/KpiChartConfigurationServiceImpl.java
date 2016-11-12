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

package com.abixen.platform.module.kpichart.service.impl;

import com.abixen.platform.module.kpichart.form.KpiChartConfigurationForm;
import com.abixen.platform.module.kpichart.model.impl.KpiChartConfiguration;
import com.abixen.platform.module.kpichart.repository.KpiChartConfigurationRepository;
import com.abixen.platform.module.kpichart.service.KpiChartConfigurationDomainBuilderService;
import com.abixen.platform.module.kpichart.service.KpiChartConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class KpiChartConfigurationServiceImpl implements KpiChartConfigurationService {

    private final Logger log = LoggerFactory.getLogger(KpiChartConfigurationServiceImpl.class);

    @Resource
    private KpiChartConfigurationRepository kpiChartConfigurationRepository;

    @Autowired
    private KpiChartConfigurationDomainBuilderService kpiChartConfigurationDomainBuilderService;

    @Override
    public KpiChartConfiguration buildKpiChartConfiguration(KpiChartConfigurationForm form) {
        log.debug("buildKpiChartConfiguration() - simplePieChartConfigurationForm: " + form);
        return kpiChartConfigurationDomainBuilderService.newKpiChartConfigurationBuilderInstance()
                .basic(form.getModuleId())
                .value(form.getValue(), form.getMaxValue())
                .animation(form.getAnimationDuration(), form.getAnimationDelay(), form.getAnimationType())
                .appearance(form.getLineWidth(), form.getRadius(), form.isSemi(), form.isClockwise(), form.isResponsive(), form.isRounded(), form.getColorCode())
                .description(form.getDescription())
                .build();
    }

    @Override
    public KpiChartConfigurationForm createKpiChartConfiguration(KpiChartConfigurationForm kpiChartConfigurationForm) {
        KpiChartConfiguration kpiChartConfiguration = buildKpiChartConfiguration(kpiChartConfigurationForm);
        return new KpiChartConfigurationForm(updateKpiChartConfiguration(createKpiChartConfiguration(kpiChartConfiguration)));
    }

    @Override
    public KpiChartConfigurationForm updateKpiChartConfiguration(KpiChartConfigurationForm kpiChartConfigurationForm) {
        log.debug("updateKpiChartConfiguration() - kpiChartConfigurationForm: " + kpiChartConfigurationForm);

        KpiChartConfiguration kpiChartConfiguration = findKpiChartConfigurationByModuleId(kpiChartConfigurationForm.getModuleId());
        kpiChartConfiguration.setColorCode(kpiChartConfigurationForm.getColorCode());
        kpiChartConfiguration.setDescription(kpiChartConfigurationForm.getDescription());
        kpiChartConfiguration.setLineWidth(kpiChartConfigurationForm.getLineWidth());
        kpiChartConfiguration.setMaxValue(kpiChartConfigurationForm.getMaxValue());
        kpiChartConfiguration.setValue(kpiChartConfigurationForm.getValue());
        kpiChartConfiguration.setRounded(kpiChartConfigurationForm.isRounded());
        kpiChartConfiguration.setResponsive(kpiChartConfigurationForm.isResponsive());
        kpiChartConfiguration.setClockwise(kpiChartConfigurationForm.isClockwise());
        kpiChartConfiguration.setSemi(kpiChartConfigurationForm.isSemi());
        kpiChartConfiguration.setAnimationDelay(kpiChartConfigurationForm.getAnimationDelay());
        kpiChartConfiguration.setAnimationDuration(kpiChartConfigurationForm.getAnimationDuration());
        kpiChartConfiguration.setAnimationType(kpiChartConfigurationForm.getAnimationType());
        kpiChartConfiguration.setRadius(kpiChartConfigurationForm.getRadius());

        return new KpiChartConfigurationForm(updateKpiChartConfiguration(kpiChartConfiguration));
    }

    @Override
    public KpiChartConfiguration findKpiChartConfigurationByModuleId(Long id) {
        return kpiChartConfigurationRepository.findByModuleId(id);
    }

    @Override
    public KpiChartConfiguration createKpiChartConfiguration(KpiChartConfiguration kpiChartConfiguration) {
        log.debug("createKpiChartConfiguration() - kpiChartConfiguration: " + kpiChartConfiguration);
        KpiChartConfiguration createdKpiChartConfiguration = kpiChartConfigurationRepository.save(kpiChartConfiguration);
        return createdKpiChartConfiguration;
    }

    @Override
    public KpiChartConfiguration updateKpiChartConfiguration(KpiChartConfiguration kpiChartConfiguration) {
        log.debug("updateKpiChartConfiguration() - kpiChartConfiguration: " + kpiChartConfiguration);
        return kpiChartConfigurationRepository.save(kpiChartConfiguration);
    }
}
