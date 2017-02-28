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
gulp.task('adminTemplateCache', adminTemplateCacheTask);
gulp.task('applicationTemplateCache', applicationTemplateCacheTask);
gulp.task('adminScripts', adminScriptsTask);
gulp.task('applicationScripts', applicationScriptsTask);
gulp.task('adminStyles', adminStylesTask);
gulp.task('applicationStyles', applicationStylesTask);
gulp.task('build', buildTask);
gulp.task('applicationLibs', applicationLibsTask);
gulp.task('adminLibs', adminLibsTask);
gulp.task('dev', ['build'], devTask);
gulp.task('default', ['build']);
gulp.task('angularTemplateCache', ['adminTemplateCache', 'applicationTemplateCache']);


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
        .pipe(templateCache('business-intelligence-service.templatecache.js', {module : 'businessIntelligenceServiceTemplatecache' }))
        .pipe(gulp.dest(destinationScriptPath));
}

function adminTemplateCacheTask(){

    return genericTemplateCacheTask(config.templates.files, config.dest.adminTemplateCache);

}

function applicationTemplateCacheTask(){

    return genericTemplateCacheTask(config.templates.files, config.dest.applicationTemplateCache);

}

function adminScriptsTask() {

    return genericScriptsTask(config.scripts.adminFiles, config.dest.adminScripts);
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

function adminStylesTask() {

    return genericStylesTask(config.styles.adminSass, config.dest.adminStyles);
}

function applicationStylesTask() {

    return genericStylesTask(config.styles.applicationSass, config.dest.applicationStyles);
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
        'applicationLibs',
        'adminLibs',
        [
            'adminScripts',
            'applicationScripts',
            'templates',
            'adminStyles',
            'applicationStyles'
        ],
        callback);
}

function applicationLibsTask() {
    return gulp.src(config.applicationLibs.files)
        .pipe(gulp.dest(config.dest.applicationLibs));
}

function adminLibsTask() {
    return gulp.src(config.adminLibs.files)
        .pipe(gulp.dest(config.dest.adminLibs));
}

function devTask() {
    devMode = true;

    gulp.watch(config.scripts.adminFiles, ['adminScripts']);
    gulp.watch(config.scripts.applicationFiles, ['applicationScripts']);

    gulp.watch(config.templates.files, ['templates', 'angularTemplateCache']);

    gulp.watch(config.styles.adminWatch, ['adminStyles']);
    gulp.watch(config.styles.applicationWatch, ['applicationStyles']);
}