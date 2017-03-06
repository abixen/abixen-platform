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

    function commentDirective() {
        var counter = 0,
            depth = null;

        return {
            restrict: 'E',
            templateUrl: 'application/modules/comment/html/comment.template.html',
            scope: {
                commentItem: '=',
                moduleId: '=',
                roots: '='
            },
            compile: compile,
            controller: CommentDirectiveController,
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

    CommentDirectiveController.$inject = ['$scope', '$log', '$compile', 'Comment', 'responseHandler', '$templateRequest', 'platformSecurity', 'amMoment'];

    function CommentDirectiveController($scope, $log, $compile, Comment, responseHandler, $templateRequest, platformSecurity, amMoment) {
        var comment = this;
        var addCommentForm;
        var action;
        var commentBeforeEdit;

        new AbstractDetailsController(comment, Comment, responseHandler, $scope,
            {
                entityId: null,
                initEntity: {
                    parentId: comment.commentItem === 0 ? comment.commentItem.id : 0,
                    moduleId: comment.moduleId
                },
                getValidators: getValidators,
                onSuccessSaveForm: onSuccessSaveForm
            }
        );

        comment.openReplyForm = openReplyForm;
        comment.openEditForm = openEditForm;
        comment.openAddForm = openAddForm;
        comment.cancelForm = cancelForm;
        comment.deleteComment = deleteComment;
        comment.canEdit = canEdit();
        comment.avatarFullPath = getAvatarFullPath();
        changeMomentLocale();

        $scope.$watch(platformSecurity.getPlatformUser, onUserChange);

        function getAvatarFullPath() {
            var res = '';
            if (angular.isDefined(comment.commentItem) && comment.commentItem !== null &&
                angular.isDefined(comment.commentItem.user) && comment.commentItem.user !== null) {
                res = '/api/application/users/' + comment.commentItem.user.id + '/avatar/' + comment.commentItem.user.avatarFileName;
            }
            return res;
        }

        function canEdit() {
            var res = angular.isDefined(comment.commentItem)
                && angular.isDefined(comment.commentItem.user)
                && comment.commentItem.user != null
                && (platformSecurity.getPlatformUser().id === comment.commentItem.user.id);
            return res;
        }

        function openReplyForm(commentId) {
            var selector = '#comments-post-div-' + comment.commentItem.moduleId;
            action = 'ADD';
            comment.entity = {};
            comment.entity.parentId = comment.commentItem.id;
            comment.entity.moduleId = comment.commentItem.moduleId;
            appendAndShowCommentForm(selector);
        }

        function openEditForm(commentId) {
            var selector = '#comments-post-div-' + comment.commentItem.moduleId;
            commentBeforeEdit = angular.copy(comment.commentItem);
            comment.entity = comment.commentItem;
            action = 'EDIT';
            appendAndShowCommentForm(selector);
        }

        function openAddForm() {
            var selector = '#comments-post-div-' + comment.moduleId;
            action = 'ADD_ROOT';
            comment.entity = {};
            comment.entity.parentId = null;
            comment.entity.moduleId = comment.moduleId;
            appendAndShowCommentForm(selector);
        }

        function cancelForm() {
            if (addCommentForm) {
                if (action === 'EDIT'){
                    angular.copy(commentBeforeEdit, comment.commentItem);
                }
                addCommentForm.remove();
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

            if (action === 'ADD') {
                var curChildren = comment.commentItem.children;
                if (angular.isUndefined(curChildren) || curChildren === null) {
                    comment.commentItem.children = [comment.entity];
                } else {
                    comment.commentItem.children.push(comment.entity);
                }
            }
            if (action === 'ADD_ROOT') {
                comment.roots.push(comment.entity);
            }
        }

        function appendAndShowCommentForm(selector) {
            var existingForm = angular.element(document.querySelector('#comment-input-form'));
            if (angular.isDefined(existingForm) && existingForm !== null &&
                angular.isDefined(existingForm[0]) && existingForm[0].tagName === 'FORM') {
                $log.info(existingForm[0].tagName);
                existingForm.remove();
            }

            var targetElement = angular.element(document.querySelector(selector));
            $templateRequest("/application/modules/comment/html/add-comment.template.html", false)
                .then(function (html) {
                    addCommentForm = angular.element(html);
                    targetElement.append(addCommentForm);
                    $compile(addCommentForm)($scope);
                });
        }

        function deleteComment(commentItem) {
            $log.info('delete comment with id ' + commentItem.id);
            Comment.delete({id: commentItem.id})
                .$promise.then(function (data) {
                var index = comment.roots.indexOf(commentItem);
                comment.roots.splice(index, 1);
            });
        }

        function onUserChange() {
            changeMomentLocale();
        }

        function changeMomentLocale() {
            switch (platformSecurity.getPlatformUser().selectedLanguage) {
                case 'ENGLISH':
                    amMoment.changeLocale('en-gb');
                    break;
                case 'POLISH':
                    amMoment.changeLocale('pl');
                    break;
                case 'RUSSIAN':
                    amMoment.changeLocale('ru');
                    break;
                case 'UKRAINIAN':
                    amMoment.changeLocale('uk');
                    break;
                case 'SPANISH':
                    amMoment.changeLocale('sp');
                    break;
                default:
                    amMoment.changeLocale('en-gb');

            }
        }
    }
})();