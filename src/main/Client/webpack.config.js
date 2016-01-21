var webpack = require('webpack');
var path = require('path');

module.exports = {

    entry: './app/boot',
    output: {
        path: __dirname,
        filename: './dist/bundle.js'
    },

    // Turn on source maps
    devtool: 'source-map',

    resolve: {
        extensions: ['', '.ts', '.js']
    },

    plugins: [

        // Minimize output
        // new webpack.optimize.UglifyJsPlugin()
    ],

    module: {
        loaders: [

            // Typescript support
            {test: /\.ts$/, loaders: ['ts-loader'], exclude: /node_modules/},

            // CSS support
            {test: /\.css$/, loader: 'style-loader!css-loader'}
        ]
    }
};