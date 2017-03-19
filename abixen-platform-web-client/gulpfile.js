'use strict';

var gulp = require('gulp'),
    gulpIf = require('gulp-if'),
    mapConcat = require('gulp-concat-sourcemap'),
    order = require('gulp-order'),
    sass = require('gulp-sass'),
    htmlMin = require('gulp-htmlmin'),
    minifyCss = require('gulp-minify-css'),
    uglify = require('gulp-uglify'),
    jsHint = require('gulp-jshint'),
    jscs = require('gulp-jscs'),
    gutil = require('gulp-util'),
    merge = require('merge-stream'),
    runSequence = require('run-sequence'),
    ignore = require('gulp-ignore'),
    del = require('del'),
    config = require('./build-config'),
    templateCache = require('gulp-angular-templatecache'),
    devMode = true;

gulp.task('clean', cleanTask);
gulp.task('templates', templatesTask);
gulp.task('applicationTemplateCache', applicationTemplateCacheTask);
gulp.task('controlPanelTemplateCache', controlPanelTemplateCacheTask);
gulp.task('loginScripts', loginScriptsTask);
gulp.task('adminScripts', adminScriptsTask);
gulp.task('applicationScripts', applicationScriptsTask);
gulp.task('commonScripts', commonScriptsTask);
gulp.task('loginStyles', loginStylesTask);
gulp.task('adminStyles', adminStylesTask);
gulp.task('applicationStyles', applicationStylesTask);
gulp.task('libStyles', libStylesTask);
gulp.task('loginImages', loginImagesTask);
gulp.task('commonNavigationImages', commonNavigationImagesTask);
gulp.task('build', buildTask);
gulp.task('libs', libsTask);
gulp.task('dev', ['build'], devTask);
gulp.task('default', ['build']);
gulp.task('angularTemplateCache', ['applicationTemplateCache','controlPanelTemplateCache']);

function cleanTask() {

    return del(config.dest.files);
}

function templatesTask() {

    return gulp.src(config.templates.files)
        .pipe(htmlMin(config.templates.minifyOpts))
        .on('error', gutil.log)
        .pipe(gulp.dest(config.dest.dir));
}

function genericTemplateCacheTask(sourceTemplatePath, destinationScriptPath){

    return gulp.src(sourceTemplatePath)
        .pipe(templateCache('web-client.templatecache.js', {module : 'webClientTemplatecache' }))
        .pipe(gulp.dest(destinationScriptPath));
}

function controlPanelTemplateCacheTask(){

    return genericTemplateCacheTask(config.templates.files, config.dest.controlPanelTemplateCache);

}

function applicationTemplateCacheTask(){

    return genericTemplateCacheTask(config.templates.files, config.dest.applicationTemplateCache);

}

function loginScriptsTask() {

    return genericScriptsTask(config.scripts.loginFiles, config.dest.loginScripts);
}

function adminScriptsTask() {

    return genericScriptsTask(config.scripts.adminFiles, config.dest.adminScripts);
}

function commonScriptsTask() {

    return genericScriptsTask(config.scripts.commonFiles, config.dest.commonScripts);
}

function applicationScriptsTask() {

    return genericScriptsTask(config.scripts.applicationFiles, config.dest.applicationScripts);
}

function genericScriptsTask(sourceScriptsPath, destinationScriptsPath) {

    return gulp.src(sourceScriptsPath)
        .pipe(jsHint())
        .pipe(jsHint.reporter('jshint-stylish'))
        .pipe(gulpIf(!devMode, jsHint.reporter('fail')))
        .pipe(jscs())
        .pipe(jscs.reporter())
        .pipe(gulpIf(!devMode, jscs.reporter('fail')))
        .pipe(order(config.scripts.concatOrder, {base: '.'}))
        .pipe(mapConcat(destinationScriptsPath, {sourcesContent: true}))
        //.pipe(ignore.exclude([ "**/*.map" ]))
        //.pipe(uglify())
        .pipe(gulp.dest(config.dest.dir));
}

function loginStylesTask() {

    return genericStylesTask(config.styles.loginSass, config.dest.loginStyles);
}

function adminStylesTask() {

    return genericStylesTask(config.styles.adminSass, config.dest.adminStyles);
}

function applicationStylesTask() {

    return genericStylesTask(config.styles.applicationSass, config.dest.applicationStyles);
}

function libStylesTask() {

    return genericStylesTask(config.styles.libSass, config.dest.libStyles);
}

function genericStylesTask(sourceSassPath, destinationStylesPath) {

    return gulp.src(sourceSassPath)
        .pipe(sass().on('error', sass.logError))
        .pipe(mapConcat(destinationStylesPath))
        .pipe(minifyCss())
        .pipe(gulp.dest(config.dest.dir));
}

function buildTask(callback) {

    runSequence('clean',
        'angularTemplateCache',
        'libs',
        [
            'loginScripts',
            'adminScripts',
            'applicationScripts',
            'commonScripts',
            'templates',
            'loginStyles',
            'adminStyles',
            'applicationStyles',
            'libStyles',
            'loginImages',
            'commonNavigationImages'
        ],
        callback);
}

function libsTask() {
    var libs = gulp.src(config.libs.files)
        .pipe(gulp.dest(config.dest.libs));

    var fontawesome = gulp.src(config.libs.fontawesome)
        .pipe(gulp.dest(config.dest.fontawesome));

    var roboto = gulp.src(config.libs.roboto)
        .pipe(gulp.dest(config.dest.roboto));

    var glyphicons = gulp.src(config.libs.glyphicons)
        .pipe(gulp.dest(config.dest.glyphicons));

    var ckeditor = gulp.src(config.libs.ckeditor)
        .pipe(gulp.dest(config.dest.ckeditor));

    var moment = gulp.src(config.libs.moment)
        .pipe(gulp.dest(config.dest.moment));

    return merge(libs, fontawesome, roboto, glyphicons, ckeditor, moment);
}

function loginImagesTask() {
    return gulp.src(config.images.login)
        .pipe(gulp.dest(config.dest.loginImages));
}

function commonNavigationImagesTask() {
    return gulp.src(config.images.commonNavigation)
        .pipe(gulp.dest(config.dest.commonNavigationImages));
}

function devTask() {
    devMode = true;

    gulp.watch(config.scripts.loginFiles, ['loginScripts']);
    gulp.watch(config.scripts.adminFiles, ['adminScripts']);
    gulp.watch(config.scripts.applicationFiles, ['applicationScripts']);
    gulp.watch(config.scripts.commonFiles, ['commonScripts']);

    gulp.watch(config.templates.files, ['templates','angularTemplateCache']);

    gulp.watch(config.styles.loginWatch, ['loginStyles']);
    gulp.watch(config.styles.adminWatch, ['adminStyles']);
    gulp.watch(config.styles.applicationWatch, ['applicationStyles']);
}