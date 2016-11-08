module.exports = function (grunt) {

    grunt.loadNpmTasks('grunt-bowercopy');
    grunt.loadNpmTasks('grunt-contrib-sass');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-sass');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-htmlmin');

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        bowercopy: {
            bowercomponents: {
                options: {
                    destPrefix: 'src/main/resources/static/lib',
                    srcPrefix: 'bower_components'
                },
                files: {
                    'angular.min.js': 'angular/angular.min.js',
                    'angular-resource.min.js': 'angular-resource/angular-resource.min.js',
                    'angular-animate.min.js': 'angular-animate/angular-animate.min.js',
                    'angular-touch.min.js': 'angular-touch/angular-touch.min.js',
                    'angular-route.min.js': 'angular-route/angular-route.min.js',
                    'angular-ui-router.min.js': 'angular-ui-router/release/angular-ui-router.min.js',
                    'angular-ui-grid/angular-ui-grid.min.js': 'angular-ui-grid/ui-grid.min.js',
                    'angular-ui-grid/angular-ui-grid.min.css': 'angular-ui-grid/ui-grid.min.css',
                    'angular-ui-grid/ui-grid.eot': 'angular-ui-grid/ui-grid.eot',
                    'angular-ui-grid/ui-grid.svg': 'angular-ui-grid/ui-grid.svg',
                    'angular-ui-grid/ui-grid.ttf': 'angular-ui-grid/ui-grid.ttf',
                    'angular-ui-grid/ui-grid.woff': 'angular-ui-grid/ui-grid.woff',
                    'angular-aside.min.js': 'angular-aside/dist/js/angular-aside.min.js',
                    'angular-translate.min.js': 'angular-translate/angular-translate.min.js',
                    'angular-file-upload.min.js' :'angular-file-upload/dist/angular-file-upload.min.js',
                    'angular-cookies.js' : 'angular-cookies/angular-cookies.min.js',
                    'ui-bootstrap-tpls.min.js': 'angular-bootstrap/ui-bootstrap-tpls.min.js',
                    'sortable.min.js': 'Sortable/Sortable.min.js',
                    'pdfmake/pdfmake.min.js': 'pdfmake/build/pdfmake.min.js',
                    'pdfmake/vfs-fonts.js': 'pdfmake/build/vfs_fonts.js',
                    'toaster/toaster.min.js': 'AngularJS-Toaster/toaster.min.js',
                    'toaster/toaster.min.css': 'AngularJS-Toaster/toaster.min.css',
                    'angular-xeditable/xeditable.min.js': 'angular-xeditable/dist/js/xeditable.min.js',
                    'angular-xeditable/xeditable.css': 'angular-xeditable/dist/css/xeditable.css',
                    //'fonts/bootstrap/glyphicons-halflings-regular.woff': 'sass-bootstrap-glyphicons/fonts/glyphiconshalflings-regular.woff',
                    'fonts/fontawesome': 'fontawesome/fonts/',
                    'fonts/roboto': 'roboto-fontface/fonts'
                }
            }
        },
        sass: {
            dist: {
                options: {
                    sourcemap: false
                },
                files: {
                    'src/main/resources/static/stylesheets/font-awesome.css': 'src/main/web/lib/font-awesome/font-awesome.scss',
                    'src/main/resources/static/stylesheets/bootstrap.css': 'src/main/web/lib/bootstrap/bootstrap.scss',
                    'src/main/resources/static/stylesheets/login.css': 'src/main/web/login/scss/style.scss',
                    'src/main/resources/static/stylesheets/common/fonts/roboto.css': 'src/main/web/common/fonts/roboto.scss',
                    'src/main/resources/static/stylesheets/admin/application.css': 'src/main/web/admin/scss/application.scss',
                    'src/main/resources/static/stylesheets/application/application.css': 'src/main/web/application/scss/application.scss'
                }
            }
        },
        cssmin: {
            dist: {
                files: [
                    {
                        expand: true,
                        cwd: 'src/main/resources/static/stylesheets/',
                        src: ['*.css', '!*.min.css'],
                        dest: 'src/main/resources/static/stylesheets/',
                        ext: '.min.css'
                    },
                    {
                        expand: true,
                        cwd: 'src/main/resources/static/stylesheets/admin/',
                        src: ['*.css', '!*.min.css'],
                        dest: 'src/main/resources/static/stylesheets/admin/',
                        ext: '.min.css'
                    },
                    {
                        expand: true,
                        cwd: 'src/main/resources/static/stylesheets/application/',
                        src: ['*.css', '!*.min.css'],
                        dest: 'src/main/resources/static/stylesheets/application/',
                        ext: '.min.css'
                    },
                    {
                        expand: true,
                        cwd: 'src/main/resources/static/stylesheets/common/',
                        src: ['*.css', '!*.min.css'],
                        dest: 'src/main/resources/static/stylesheets/common/',
                        ext: '.min.css'
                    },
                    /*{
                     expand: true,
                     cwd: 'src/main/resources/static/stylesheets/user/',
                     src: ['*.css', '!*.min.css'],
                     dest: 'src/main/resources/static/stylesheets/user/',
                     ext: '.min.css'
                     },*/
                     {
                     expand: true,
                     cwd: 'src/main/resources/static/stylesheets/angular-aside/',
                     src: ['*.css', '!*.min.css'],
                     dest: 'src/main/resources/static/stylesheets/angular-aside/',
                     ext: '.min.css'
                     }
                ]
            }
        },
        clean: {
            css: [
                "src/main/resources/static"
            ]
        },
        concat: {
            options: {
                // define a string to put between each file in the concatenated output
                separator: ';'
            },
            login: {
                // the files to concatenate
                src: ['src/main/web/login/**/*.js'],
                // the location of the resulting JS file
                dest: 'src/main/resources/static/login/application.js'
            },
            adminApplication: {
                // the files to concatenate
                src: ['src/main/web/admin/javascript/**/*.js'],
                // the location of the resulting JS file
                dest: 'src/main/resources/static/admin/application.js'
            },
            adminModules: {
                // the files to concatenate
                src: ['src/main/web/admin/modules/**/*.js'],
                // the location of the resulting JS file
                dest: 'src/main/resources/static/admin/modules.js'
            },
            applicationApplication: {
                // the files to concatenate
                src: ['src/main/web/application/javascript/*.js'],
                // the location of the resulting JS file
                dest: 'src/main/resources/static/application/application.js'
            },
            applicationModules: {
                // the files to concatenate
                src: ['src/main/web/application/**/*.js'],
                // the location of the resulting JS file
                dest: 'src/main/resources/static/application/modules.js'
            },
            common: {
                // the files to concatenate
                src: ['src/main/web/common/**/*.js'],
                // the location of the resulting JS file
                dest: 'src/main/resources/static/common/modules.js'
            }
        },
        uglify: {
            options: {
                // the banner is inserted at the top of the output
            },
            dist: {
                files: {
                    'src/main/resources/static/login/application.min.js': ['src/main/resources/static/login/application.js'],
                    'src/main/resources/static/admin/application.min.js': ['src/main/resources/static/admin/application.js'],
                    'src/main/resources/static/admin/modules.min.js': ['src/main/resources/static/admin/modules.js'],
                    'src/main/resources/static/application/application.min.js': ['src/main/resources/static/application/application.js'],
                    'src/main/resources/static/application/modules.min.js': ['src/main/resources/static/application/modules.js'],
                    'src/main/resources/static/common/modules.min.js': ['src/main/resources/static/common/modules.js']
                }
            }
        },
        copy: {
            html: {
                files: [
                    {
                        expand: true,
                        cwd: 'src/main/web/',
                        src: '**/*.html',
                        dest: 'src/main/resources/static/',
                        filter: 'isFile'
                    }
                ]
            },
            javascript: {
                files: [
                    {
                        expand: true,
                        cwd: 'src/main/web/lib',
                        src: 'show-errors.min.js',
                        dest: 'src/main/resources/static/lib/',
                        filter: 'isFile'
                    }
                ]
            },
            images: {
                files: [
                    {
                        expand: true,
                        cwd: 'src/main/web/',
                        src: '**/*.jpg',
                        dest: 'src/main/resources/static/',
                        filter: 'isFile'
                    },
                    {
                        expand: true,
                        cwd: 'src/main/web/',
                        src: '**/*.png',
                        dest: 'src/main/resources/static/',
                        filter: 'isFile'
                    },
                    {
                        expand: true,
                        cwd: 'src/main/web/',
                        src: '**/*.svg',
                        dest: 'src/main/resources/static/',
                        filter: 'isFile'
                    }
                ]
            }
        },
        htmlmin: {
            dist: {
                options: {
                    removeComments: true,
                    collapseWhitespace: true
                },
                files: [
                    {
                        expand: true,                           // Enable dynamic expansion.
                        cwd: 'src/main/resources/static/',      // Src matches are relative to this path.
                        src: ['**/*.html'],                     // Actual pattern(s) to match.
                        dest: 'src/main/resources/static/'      // Destination path prefix.
                    }
                ]
            }
        }
    });


    grunt.registerTask('default', ['clean', 'bowercopy', 'sass', 'cssmin', 'concat', 'uglify', 'copy', 'htmlmin']);

};