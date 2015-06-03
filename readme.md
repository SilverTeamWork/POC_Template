## Silverpeas Web

This project is a POC dedicated to build a Web 2.0 modern UI based upon the new emergent technologies.
This actual implementation of the POC is based upon Polymer 1.0.
The layout of the project is inspired by the Polymer Starter Kit 1.0 and by the following posts 
* [Why we should stop using Grunt & Gulp](http://blog.keithcirkel.co.uk/why-we-should-stop-using-grunt/ "Why we should stop using Grunt & Gulp")  
* [How to Use npm as a Build Tool](http://blog.keithcirkel.co.uk/how-to-use-npm-as-a-build-tool/ "Why we should stop using Grunt & Gulp")

## Set up the project

### Project build

The project build is managed by npm itself ([Node.js](http://nodejs.org)) and it requires for doing some tools that are provided as Node.js modules.
These modules can be either installed locally (id est in the project root directory) or globally.

To install locally the modules, just run:
```sh
$ npm install
```

To install globally the modules, just run:
```sh
$ npm run deps
```

The different build tasks are defines in the npm descriptor package.json under the _scripts_ section.
For instance, not all is done with the build in order to have a well-completed web distribution of the project but the package.json build descriptor
will be enriched progressively.

### Project dependencies

If you have installed globally the Node.js modules, you can skip this section.

The project is based upon Polymer as the Web framework and upon some libraries (like [Page.js](https://visionmedia.github.io/page.js/ "Page.js") for routing). These dependencies are managed by [Bower](http://bower.io/ "Bower") that is a dependency management tool dedicated to the development of Web front-end. It is based upon Node.js and it uses the bower.json descriptor to define the dependencies of a project.

In order to install these dependencies, just run:
```sh
$ bower install
```

## To code

When working on the project, you have to run two commands in background:

```sh
$ npm run watch:dev
```

This will launch a watcher of changes in the code and for each change it will rebuild the source. At first launch it will build the project in order to be served by the web server used in development mode.

```sh
$ npm run serve
```

This will launch a web server listening at port 9090 and serving the build of the project located in the `dist` directory.

