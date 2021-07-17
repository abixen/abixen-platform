package com.abixen.platform.common.interfaces.web.page;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PlatformSort extends PageRequest {

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    protected PlatformSort(@JsonProperty("pageNumber") int pageNumber,
                           @JsonProperty("pageSize") int pageSize,
                           @JsonProperty("sort")Sort sort) {
        super(pageNumber, pageSize, sort);
    }
}
