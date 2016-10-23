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
                    destPrefix: 'src/main/resources/static/application/modules/abixen/lib',
                    srcPrefix: 'bower_components'
                },
                files: {
                    'round-progress.min.js': 'angular-svg-round-progressbar/build/roundProgress.min.js',
                    'd3.min.js': 'd3/d3.min.js',
                    'nvd3/nv.d3.min.js': 'nvd3/build/nv.d3.min.js',
                    'nvd3/nv.d3.min.css': 'nvd3/build/nv.d3.min.css',
                    'angular-nvd3.min.js': 'angular-nvd3/dist/angular-nvd3.min.js'
                }
            }
        },
        sass: {
            dist: {
                options: {
                    sourcemap: false
                },
                files: {
                    'src/main/resources/static/admin/modules/abixen/modules.css': 'src/main/web/admin/modules/abixen/scss/modules.scss',
                    'src/main/resources/static/application/modules/abixen/modules.css': 'src/main/web/application/modules/abixen/scss/modules.scss'
                }
            }
        },
        cssmin: {
            dist: {
                files: [
                    {
                        expand: true,
                        cwd: 'src/main/resources/static/admin/modules/abixen/',
                        src: ['*.css', '!*.min.css'],
                        dest: 'src/main/resources/static/admin/modules/abixen/',
                        ext: '.min.css'
                    },
                    {
                        expand: true,
                        cwd: 'src/main/resources/static/application/modules/abixen/',
                        src: ['*.css', '!*.min.css'],
                        dest: 'src/main/resources/static/application/modules/abixen/',
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
            adminModules: {
                // the files to concatenate
                src: ['src/main/web/admin/modules/abixen/**/*.js'],
                // the location of the resulting JS file
                dest: 'src/main/resources/static/admin/modules/abixen/modules.js'
            },
            applicationModules: {
                // the files to concatenate
                src: ['src/main/web/application/modules/abixen/**/*.js'],
                // the location of the resulting JS file
                dest: 'src/main/resources/static/application/modules/abixen/modules.js'
            }
        },
        uglify: {
            options: {
                // the banner is inserted at the top of the output
            },
            dist: {
                files: {
                    'src/main/resources/static/admin/modules/abixen/modules.min.js': ['src/main/resources/static/admin/modules/abixen/modules.js'],
                    'src/main/resources/static/application/modules/abixen/modules.min.js': ['src/main/resources/static/application/modules/abixen/modules.js']
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
                /*files: [
                    {
                        expand: true,
                        cwd: 'src/main/web/lib',
                        src: 'show-errors.min.js',
                        dest: 'src/main/resources/static/lib/',
                        filter: 'isFile'
                    }
                ]*/
            },
            images: {
                files: [
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