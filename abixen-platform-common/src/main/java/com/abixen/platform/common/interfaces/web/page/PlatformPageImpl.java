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

package com.abixen.platform.common.interfaces.web.page;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class PlatformPageImpl<T> extends PageImpl<T> {

    private int number;
    private int size;
    private int totalPages;
    private int numberOfElements;
    private long totalElements;
    private boolean first;
    private boolean last;
    private List<T> content;
    private Sort sort;

    public PlatformPageImpl() {
        super(Collections.EMPTY_LIST);
    }

    public PlatformPageImpl(Page page) {
        super(page.getContent(),
                new PageRequest(page.getNumber(), page.getSize(), page.getSort()),
                page.getTotalElements());
        this.number = page.getNumber();
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
        this.numberOfElements = page.getNumberOfElements();
        this.totalElements = page.getTotalElements();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.content = page.getContent();
        this.sort = page.getSort();
    }

    @JsonDeserialize(using = PlatformSortDeserializer.class)
    public void setSort(Sort sort) {
        this.sort = sort;
    }

}