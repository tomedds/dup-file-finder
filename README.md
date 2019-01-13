# Duplicate file finder

This is an early version of an application to identify duplicate files for those who want to do some disk cleanup. It runs as a command line program, taking one 
or more directories to be scanned, and then generates a CSV listing duplicate files. The CSV has columns for the number of duplicates found, the file size, and then 
each of the duplicate files is shown in a separate column.

## Getting Started

### Prerequisites

Linux or MacOS. The project was developed and tested on MacOS. It should work on Linux. It will probably work on Windows but you'll 
need to convert the shell scripts to  Windows command files.

Java 8 or later.

### Building

 `./build.sh`
 
The application.yml file contains property settings that you may want to tweak, such as logging or the minimum file size used
in selecting files for review.

### Installing

No install in this version.

### Running the tests

Unit tests are run as part of the standard build.

## Deployment

No additional deployment required in this version.

## Execution

Once you have build the application and loaded the data, you can use the `run.sh` script to start the service, specifying the directories to be scanned.

Example:
  `./run.sh /tmp`

## Documentation

TBD

### Roadmap

The next version will have additional criteria for selecting the files to be reviewed. The current version uses only file size, but we need to
be able to filter on file extension and perhaps date modified.

Currently the values for the selection criteria are specified as properties in the application.yml file. These should be available as commmand line options.

## Contributing

TBD

## Versioning

TBD

## Authors

* **Tom Edds** - *Initial work* 

## License

Copyright &copy; 2019, Tom Edds. All rights reserved.


[//]: # (This file based on a temlpate from https://gist.github.com/PurpleBooth/109311bb0361f32d87a2)
