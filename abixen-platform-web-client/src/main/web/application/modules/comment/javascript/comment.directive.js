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

(function () {
    'use strict';

    angular
        .module('platformCommentModule')
        .directive('comment', commentDirective);

    commentDirective.$inject = ['$log', '$http', '$compile', 'responseHandler'];

    function commentDirective($log, $http, $compile) {
        var counter = 0,
            depth = null;

        return {
            restrict: 'E',
            templateUrl: '/application/modules/comment/html/comment.template.html',
            scope: {
                commentItem: '='
            },
            compile: compile,
            controller: CommentsDirectiveController,
            controllerAs: 'comment',
            bindToController: true
        };

        function compile(tElement, tAttrs) {
            depth = tAttrs.depth || depth || 1;
            if (counter == depth) {
                tElement.find('comment').remove();
                depth = 'end';
            }
            depth == 'end' ? counter = 0 : counter++;
        }
    }

    function CommentsDirectiveController($scope, $log, $compile, Comment, responseHandler, $templateRequest) {
        var comment = this;
        var addCommentForm = {};
        var replyClickCounter = 0;

        new AbstractDetailsController(comment, Comment, responseHandler, $scope,
            {
                entityId: null,
                initEntity: {
                    parentId: comment.commentItem.id,
                    moduleId: comment.commentItem.moduleId
                },
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
            }
        );

        comment.openReplyForm = openReplyForm;

        function openReplyForm(commentId) {
            var selector = '#reply-ref-' + commentId;
            if (replyClickCounter === 0) {
                var targetElement = angular.element(document.querySelector(selector));
                $templateRequest("/application/modules/comment/html/add.comment.template.html",false)
                    .then(function (html) {
                        addCommentForm = angular.element(html);
                        targetElement.append(addCommentForm);
                        $compile(addCommentForm)($scope);
                        replyClickCounter++;
                });
            }
        }

        function getValidators() {
            var validators = [];
            validators['message'] =
                [
                    new NotNull(),
                    new Length(1, 1000)
                ];
            return validators;
        }

        function onSuccessSaveForm() {
            addCommentForm.remove();
            replyClickCounter = 0;
            var curChildren = comment.commentItem.children;
            if(angular.isUndefined(curChildren) || curChildren === null){
                comment.commentItem.children = [comment.entity];
            }else{
                comment.commentItem.children.push(comment.entity);
            }
        }
    }
})();