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

package com.abixen.platform.core.configuration;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlatformPackages {

    static final String MAIN = "com.abixen.platform.core";

    static final String CONFIG = MAIN + ".configuration";

    static final String SECURITY = MAIN + ".security";

    static final String INTEGRATION = MAIN + ".integration";

    static final String DOMAIN = MAIN + ".model.impl";

    static final String CONTROLLER = MAIN + ".controller";

    static final String SERVICE = MAIN + ".service.impl";

    static final String CONVERTER = MAIN + ".converter";

    static final String REPOSITORY = MAIN + ".repository";

}
