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

package com.abixen.platform.service.webcontent.configuration;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlatformWebContentServicePackages {

    public static final String MAIN = "com.abixen.platform.service.webcontent";

    public static final String CONFIG = MAIN + ".configuration";

    public static final String CONTROLLER = MAIN + ".controller";

    public static final String SERVICE = MAIN + ".service";

    public static final String REPOSITORY = MAIN + ".repository";

    public static final String DOMAIN = MAIN + ".model";

    public static final String CLIENT = MAIN + ".client";

    public static final String CONVERTER = MAIN + ".converter";
}
