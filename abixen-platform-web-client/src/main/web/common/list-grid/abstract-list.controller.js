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

'use strict';

function AbstractListGridController(Resource, config) {
    var abstractListGridController = this;
    var DEFAULT_PAGE_SIZE = 20;
    var DEFAULT_SORT = 'id,asc';
    var DEFAULT_SELECT_TYPE = 'single';
    var DEFAULT_DATA_PROVIDER_TYPE = 'page';

    abstractListGridController.listGridConfig = {
        totalPages: 0,
        pageNumber: 0,
        lastPage: false,
        getListGridData: function () {
            return [];
        },
        tableReady: false,
        getListGridColumns: function () {
            return [];
        },
        getListGridSelectedRows: function () {
            return [];
        },
        getData: getData,
        setData: setData,
        selectedEntity: null,
        selectedEntities: [],
        getTableColumns: angular.isDefined(config) ? config.getTableColumns : undefined,
        onTableReady: angular.isDefined(config) ? config.onTableReady : undefined,
        onRowSelected: angular.isDefined(config) ? config.onRowSelected : undefined,
        onRowUnselected: angular.isDefined(config) ? config.onRowUnselected : undefined,
        onGetDataResult: angular.isDefined(config) ? config.onGetDataResult : undefined,
        onGetDataError: angular.isDefined(config) ? config.onGetDataError : undefined,
        sort: angular.isDefined(config) ? (angular.isDefined(config.sort) ? config.sort : DEFAULT_SORT) : DEFAULT_SORT,
        pageSize: angular.isDefined(config) ? (angular.isDefined(config.pageSize) ? config.pageSize : DEFAULT_PAGE_SIZE) : DEFAULT_PAGE_SIZE,
        filter: angular.isDefined(config) ? (angular.isDefined(config.filter) ? config.filter : {}) : {},
        payloadFilter: angular.isDefined(config) ? (angular.isDefined(config.payloadFilter) ? config.payloadFilter : {}) : {},
        selectType: angular.isDefined(config) ? (angular.isDefined(config.selectType) ? config.selectType : DEFAULT_SELECT_TYPE) : DEFAULT_SELECT_TYPE,
        loadOnStart: angular.isDefined(config) ? (angular.isDefined(config.loadOnStart) ? config.loadOnStart : true) : true,
        dataProviderType: angular.isDefined(config) ? (angular.isDefined(config.dataProviderType) ? config.dataProviderType : DEFAULT_DATA_PROVIDER_TYPE) : DEFAULT_DATA_PROVIDER_TYPE
    };

    abstractListGridController.filter = {};

    abstractListGridController.search = search;
    abstractListGridController.deleteEntity = deleteEntity;


    function deleteEntity(entity) {
        Resource.delete({id: entity.id}, null, function () {
            search();
        });
    }

    function search() {
        angular.forEach(abstractListGridController.listGridConfig.filter, function (value, key) {

            if (abstractListGridController.listGridConfig.filter[key] != null && abstractListGridController.listGridConfig.filter[key] === '') {
                abstractListGridController.listGridConfig.filter[key] = null;
            }
        });

        abstractListGridController.listGridConfig.selectedEntity = null;
        abstractListGridController.listGridConfig.selectedEntities = [];
        abstractListGridController.listGridConfig.pageNumber = 0;

        var params = {
            page: abstractListGridController.listGridConfig.pageNumber++,
            size: abstractListGridController.listGridConfig.pageSize,
            sort: abstractListGridController.listGridConfig.sort
        };

        angular.extend(params, abstractListGridController.listGridConfig.filter);

        query(params, true);
    }

    function getData(reset) {
        var params = {
            page: abstractListGridController.listGridConfig.pageNumber++,
            size: abstractListGridController.listGridConfig.pageSize,
            sort: abstractListGridController.listGridConfig.sort
        };

        angular.extend(params, abstractListGridController.listGridConfig.filter);

        query(params, reset);
    }

    function setData(data) {
        abstractListGridController.listGridConfig.totalPages = 0;
        abstractListGridController.listGridConfig.lastPage = 0;
        abstractListGridController.listGridConfig.totalElements = data.length;

        abstractListGridController.listGridConfig.onDataChanged(data, true);
    }

    function query(params, reset) {
        angular.extend(params, abstractListGridController.listGridConfig.additionalQueryParams);

        Resource.query(params, abstractListGridController.listGridConfig.payloadFilter)
            .$promise
            .then(onQueryResult, onQueryError);

        function onQueryResult(data) {
            if (abstractListGridController.listGridConfig.dataProviderType === 'page') {
                abstractListGridController.listGridConfig.totalPages = data.totalPages;
                abstractListGridController.listGridConfig.lastPage = data.last;
                abstractListGridController.listGridConfig.totalElements = data.totalElements;

                abstractListGridController.listGridConfig.onDataChanged(data.content, reset);

            } else if (abstractListGridController.listGridConfig.dataProviderType === 'list') {
                abstractListGridController.listGridConfig.totalPages = 0;
                abstractListGridController.listGridConfig.lastPage = 0;
                abstractListGridController.listGridConfig.totalElements = data.length;

                abstractListGridController.listGridConfig.onDataChanged(data, reset);
            } else {
                throw new Error('Wrong data provider type: ' + abstractListGridController.listGridConfig.dataProviderType);
            }
            if (abstractListGridController.listGridConfig.onGetDataResult) {
                abstractListGridController.listGridConfig.onGetDataResult(data);
            }
        }

        function onQueryError(error) {
            if (abstractListGridController.listGridConfig.onGetDataError) {
                abstractListGridController.listGridConfig.onGetDataError(error);
            }
        }
    }

}