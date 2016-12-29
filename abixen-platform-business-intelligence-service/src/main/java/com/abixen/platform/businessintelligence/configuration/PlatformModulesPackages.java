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

package com.abixen.platform.businessintelligence.configuration;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlatformModulesPackages {

    public static final String MAIN = "com.abixen.platform.businessintelligence";

    public static final String CONFIG = MAIN + ".configuration";

    public static final String CONTROLLER = MAIN + ".controller";

    public static final String CLIENT = MAIN + ".client";

    public static final String CHART = MAIN + ".chart";

    public static final String KPI_CHART = MAIN + ".kpichart";

    public static final String MAGIC_NUMBER = MAIN + ".magicnumber";

    public static final String CHART_DOMAIN = CHART + ".model";

    public static final String KPI_CHART_DOMAIN = KPI_CHART + ".model";

    public static final String MAGIC_NUMBER_DOMAIN = MAGIC_NUMBER + ".model";

    public static final String CHART_REPOSITORY = CHART + ".repository";

    public static final String KPI_CHART_REPOSITORY = KPI_CHART + ".repository";

    public static final String MAGIC_NUMBER_REPOSITORY = MAGIC_NUMBER + ".repository";

}
