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

package com.abixen.platform.core.infrastructure.configuration;

import com.abixen.platform.core.infrastructure.repository.impl.PlatformJpaRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;


public class CoreJpaRepositoryFactoryBean<R extends JpaRepository<T, I>, T,
        I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I> {

    protected CoreJpaRepositoryFactoryBean(final Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new PlatformJpaRepositoryFactory(em);
    }

    private static class PlatformJpaRepositoryFactory<T, I extends Serializable>
            extends JpaRepositoryFactory {

        private final EntityManager em;

        PlatformJpaRepositoryFactory(EntityManager em) {

            super(em);
            this.em = em;
        }

        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return PlatformJpaRepositoryImpl.class;
        }

        @Override
        protected PlatformJpaRepositoryImpl getTargetRepository(RepositoryInformation information) {
            JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType());
            return new PlatformJpaRepositoryImpl(entityInformation, em);
        }


    }


}
