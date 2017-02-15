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
gulp.task('templateCache', templateCacheTask);
gulp.task('adminScripts', adminScriptsTask);
gulp.task('adminStyles', adminStylesTask);
gulp.task('build', buildTask);
gulp.task('adminLibs', adminLibsTask);
gulp.task('dev', ['build'], devTask);
gulp.task('default', ['build']);
gulp.task('angularTemplateCache', ['templateCache'], angularTemplateCacheTask)

function cleanTask() {

    return del(config.dest.files);
}

function templatesTask() {

    return gulp.src(config.templates.files)
        .pipe(htmlMin(config.templates.minifyOpts))
        .on('error', gutil.log)
        .pipe(gulp.dest(config.dest.dir));
}

function templateCacheTask(){

    return gulp.src(config.templates.files)
        .pipe(templateCache('template-cache.js', {module : 'templatecache' }))
        .pipe(gulp.dest(config.dest.templateCache));
}

function angularTemplateCacheTask(){

    return genericScriptsTask(config.scripts.adminFiles, config.dest.adminScripts);
}
function adminScriptsTask() {

    return genericScriptsTask(config.scripts.adminFiles, config.dest.adminScripts);
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

function adminStylesTask() {

    return genericStylesTask(config.styles.adminSass, config.dest.adminStyles);
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
        'adminLibs',
        [
            'angularTemplateCache',
            'adminScripts',
            'templates',
            'adminStyles'
        ],
        callback);
}

function adminLibsTask() {
    return gulp.src(config.adminLibs.files)
        .pipe(gulp.dest(config.dest.adminLibs));
}

function devTask() {
    devMode = true;

    gulp.watch(config.scripts.adminFiles, ['adminScripts']);

    gulp.watch(config.templates.files, ['templates']);

    gulp.watch(config.styles.adminWatch, ['adminStyles']);
}