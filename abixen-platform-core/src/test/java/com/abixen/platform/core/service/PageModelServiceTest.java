package com.abixen.platform.core.service;

import com.abixen.platform.core.configuration.PlatformConfiguration;
import com.abixen.platform.core.dto.PageModelDto;
import com.abixen.platform.core.model.impl.Page;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertNotNull;

/**
 * Código generado por Gerado Pucheta Figueroa (CypraxPuch)
 * Twitter: @ledzedev
 * http://ledze.mx
 * https://github.com/CypraxPuch
 * 22/Dec/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformConfiguration.class)
public class PageModelServiceTest {

    static Logger log = Logger.getLogger(PageModelServiceTest.class);

    @Autowired
    private PageModelService pageModelService;

    @Autowired
    private PageService pageService;

    @Test()
    public void getPageModel(){
        log.debug("getPageModel()");

        Long pageId = 1L;

        //as long that getPageModel is a search util, we don't need to create a new page, we just select one of the existent to test the service.
        List<Page> lstPage = pageService.findAllPages();
        if( lstPage.size() > 0 ){
            Random r = new Random();
            pageId = lstPage.get( r.ints( 1, (lstPage.size()-1) ).findFirst().getAsInt() ).getId();
        }

        PageModelDto dto = pageModelService.getPageModel(pageId);
        log.debug(dto);
        assertNotNull(dto);

    }


}
