## Silverpeas Web

This project is a POC dedicated to build a Web 2.0 modern UI based upon the new emergent technologies.
This actual implementation of the POC is based upon Polymer 1.0.
The layout of the project is inspired by the Polymer Starter Kit 1.0.

## Set up the project

### Project build

The project build is managed by [Gulpjs](http://gulpjs.com/) that is based upon ([Node.js](http://nodejs.org)). Build tasks in Gulpjs are provided by plugins that depends upon some Node.js modules to perform their work.

To set up the project, just run:
```sh
$ npm run deps
```

It will install globally some Node.js module required by the project and then locally both the dependencies of the project as well the Gulpjs plugins.

### Project dependencies

The project is based upon Polymer as the Web framework and upon some libraries (like [Page.js](https://visionmedia.github.io/page.js/ "Page.js") for routing). These dependencies are managed by [Bower](http://bower.io/ "Bower") that is a dependency management tool dedicated to the development of Web front-end. It is based upon Node.js and it uses the bower.json descriptor to define the dependencies of a project.

The dependencies of the project as well the Bower tool are installed with the `npm run deps` command.


