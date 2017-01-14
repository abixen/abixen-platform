package com.abixen.platform.core.controller.admin;

import com.abixen.platform.core.model.impl.Resource;
import com.abixen.platform.core.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Yogen Rai on 1/8/2017.
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/admin/module-types")
public class AdminResourceController {

    private final ResourceService resourceService;

    @Autowired
    public AdminResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @RequestMapping(value = "/{moduleId}/resources", method = RequestMethod.GET)
    public Page<Resource> getResources(@PathVariable("moduleId") Long moduleId, @PageableDefault(size = 1, page = 0) Pageable pageable) {
        log.debug("AdminResourceController >> getResources({})", moduleId);
        return this.resourceService.findAllResourcesForModule(moduleId, pageable);
    }
}
