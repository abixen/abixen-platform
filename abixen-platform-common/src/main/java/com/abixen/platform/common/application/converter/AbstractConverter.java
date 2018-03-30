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

package com.abixen.platform.common.application.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

public abstract class AbstractConverter<Source, Target> {

    public final List<Target> convertToList(Collection<Source> sources, Map<String, Object> parameters) {
        if(sources == null) {
            return null;
        }
        List<Target> targets = new ArrayList<>(sources.size());
        for (Source source : sources) {
            targets.add(convert(source, parameters));
        }
        return targets;
    }

    public List<Target> convertToList(Collection<Source> sources) {
        return convertToList(sources, Collections.emptyMap());
    }

    public final Set<Target> convertToSet(Collection<Source> sources, Map<String, Object> parameters) {
        Set<Target> targets = new HashSet<>(sources.size());
        for (Source source : sources) {
            targets.add(convert(source, parameters));
        }
        return targets;
    }

    public Set<Target> convertToSet(Collection<Source> sources) {
        return convertToSet(sources, Collections.emptyMap());
    }

    public final Page<Target> convertToPage(Page<Source> sourcePage, Map<String, Object> parameters) {
        List<Target> targets = new ArrayList(sourcePage.getContent().size());
        for (Source source : sourcePage) {
            targets.add(convert(source, parameters));
        }

        Pageable pageable = new PageRequest(sourcePage.getNumber(), sourcePage.getSize(), sourcePage.getSort());
        Page<Target> targetPage = new PageImpl(targets, pageable, sourcePage.getTotalElements());
        return targetPage;
    }

    public Page<Target> convertToPage(Page<Source> sourcePage) {
        return convertToPage(sourcePage, Collections.emptyMap());
    }

    public Target convert(Source source) {
        return convert(source, Collections.emptyMap());
    }

    public abstract Target convert(Source source, Map<String, Object> parameters);

}