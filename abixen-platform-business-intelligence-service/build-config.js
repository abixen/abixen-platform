'use strict';

module.exports = {
    dest: {
        dir: 'src/main/resources/static',
        files: 'src/main/resources/static/**',
        applicationLibs: 'src/main/resources/static/service/abixen/business-intelligence/application/lib',
        adminLibs: 'src/main/resources/static/service/abixen/business-intelligence/control-panel/lib',
        adminScripts: 'service/abixen/business-intelligence/control-panel/business-intelligence.min.js',
        applicationScripts: 'service/abixen/business-intelligence/application/business-intelligence.min.js',
        applicationStyles: 'service/abixen/business-intelligence/application/business-intelligence.min.css',
        adminStyles: 'service/abixen/business-intelligence/control-panel/business-intelligence.min.css',
        adminTemplateCache: 'src/main/web/service/abixen/business-intelligence/control-panel/multi-visualisation/javascript',
        applicationTemplateCache: 'src/main/web/service/abixen/business-intelligence/application/multi-visualisation/javascript'
    },
    applicationLibs: {
        files: [
            'bower_components/angular-svg-round-progressbar/build/roundProgress.min.js',
            'bower_components/d3/d3.min.js',
            'bower_components/nvd3/build/nv.d3.min.js',
            'bower_components/nvd3/build/nv.d3.min.css',
            'bower_components/angular-nvd3/dist/angular-nvd3.min.js'
        ]
    },
    adminLibs: {
        files: [
            'bower_components/js-xlsx/dist/xlsx.full.min.js'
        ]
    },
    templates: {
        files: 'src/main/web/**/*.html',
        minifyOpts: {
            removeComments: true,
            removeCommentsFromCDATA: true,
            collapseWhitespace: true,
            conservativeCollapse: false,
            collapseInlineTagWhitespace: true,
            preserveLineBreaks: false,
            useShortDoctype: true,
            keepClosingSlash: true,
            caseSensitive: true,
            quoteCharacter: '"'
        }
    },
    scripts: {
        adminFiles: [
            'src/main/web/service/abixen/business-intelligence/control-panel/**/*.js'
        ],
        applicationFiles: [
            'src/main/web/service/abixen/business-intelligence/application/**/*.js'
        ],
        concatOrder: [
            'src/main/web/**/*.templatecache.config.js',
            'src/main/web/**/*.module.js',
            'src/main/web/**/*.js'
        ]
    },
    styles: {
        adminWatch: [
            'src/main/web/service/abixen/business-intelligence/control-panel/**/*.scss'
        ],
        adminSass: [
            'src/main/web/service/abixen/business-intelligence/control-panel/scss/businessintelligence.scss'
        ],

        applicationWatch: [
            'src/main/web/service/abixen/business-intelligence/application/**/*.scss'
        ],
        applicationSass: [
            'src/main/web/service/abixen/business-intelligence/application/scss/businessintelligence.scss'
        ]
    }
};