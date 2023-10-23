# r2rml-kit – Export relational databases to RDF with R2RML

## For Mohan:

### Pre-requisites:

- Java 8 installed locally
- A running (and JDBC-available) SQL database

### Getting Started

1. Clone this repo and navigate to the proejct directory.
2. Ensure your JAVA_HOME is set to JDK/JRE 8 (if using 9+, the tool will fail) — you can check this by running

```
java -version
```

(You should see something like: `openjdk version "1.8.0_292”`)

3. Run the following:

```
mvn clean install -DskipTests
mvn compile
mvn dependency:copy-dependencies
```

4. (generate the vocabulary): To generate simply the RDFS + OWL vocabulary from your SQL data, we will run with the flag `-v`, like so:

```
./generate-mapping -o output.ttl -v jdbc:mysql://localhost:5432/dbname
```

Change out the jdbc driver accordingly (e.g. `jdbc:postgresql` or `jdbc:mysql` etc)

Above I’ve written `localhost:5432/dbname` but obviously change to the host, port, and database name that’s relevant for you

The option `-o output.ttl` describes a relative path to where the output file will be written

If you have to provide a username or password for the SQL db, use the `-u` and `-p` options accordingly, e.g.

```
./generate-mapping -u db_user -p db_password -v jdbc:mysql://localhost:5432/dbname
```

The output file will be a Turtle (TTL) serialization of the RDF vocabulary. Protege can work with TTL formats, but you can use any tool that serializes across RDF formats to turn this into JSON-LD, RDF/XML, etc. An online tool is here: https://www.easyrdf.org/converter

You can also download/install a tool like `trld` or `ttl2jsonld` to do this from the command line

4. (generate dump of as RDF / TTL): This step might be meaningless to you right now, but if you not only wanted to create a baseline RDF vocabulary out of your db’s DDL but also wanted to dump your SQL-shaped data into TTL/JSON-LD, you can use the `r2rml-kit` repo for this as well.

For this, you will want to first create the `d2rq` mapping by running the `./generate-mapping` command WITHOUT the `-v` flag:

```
./generate-mapping -u db_user -p db_password -o d2rq.ttl jdbc:mysql://localhost:5432/dbname
```

This will generate a `d2rq` mapping that not only maps the DDL -> RDF but also includes the metadata to connect to the SQL DB

The dumping command is available within the repo via `./dump-rdf`. The only option you need to likely supply is the output file of the operation, e.g. `-o test-dump.ttl`:

```
./dump-rdf -o test-dump.ttl d2rq.ttl
```

The `d2rq.ttl` argument points to the output of the `./generate-mapping` command above

The output of this will be a total, RDF-compatible dump of all the data in the SQL db into RDF-serialization (Turtle, specifically)

## Original README

**r2rml-kit** is an implementation of W3C's [R2RML](https://www.w3.org/TR/r2rml/) and [Direct Mapping](https://www.w3.org/TR/rdb-direct-mapping/) standards. It can:

- Generate an R2RML mapping by inspecting a relational database schema
- Validate R2RML mapping files
- Dump the contents of a database to an RDF file according to an R2RML mapping
- Dump the contents of a database to an RDF file according to the W3C Direct Mapping
- Access the contents of a database through the Jena API

Besides R2RML, **r2rml-kit** also supports the [D2RQ mapping language](http://d2rq.org/d2rq-language).

**r2rml-kit** is an offshoot of [D2RQ](http://d2rq.org/), based on its abandoned `develop` branch. Unlike D2RQ, it does not support SPARQL, and does not include a server application equivalent to D2RQ's D2R Server.

**r2rml-kit** is currently in pre-alpha stage. It is not yet fully separated from the D2RQ codebase, and many things will not yet work. It does not support R2RML's named graph features. See [`TODO.md`](https://github.com/d2rq/r2rml-kit/blob/master/TODO.md) for a short-term roadmap.

## Running r2rml-kit

After building with `mvn compile`, you can test-run the various components. Let's assume you have a MySQL database called `mydb` on your machine.

### Generating a default mapping file

`./generate-mapping -u root -o mydb.ttl jdbc:mysql:///mydb`

This generates a mapping file `mydb.ttl` for your database.

### Validating an R2RML mapping

`./validate mydb.ttl`

This validates the mapping file `mydb.ttl`.

### Dumping the database

`./dump-rdf -m mydb.ttl -o dump.nt`

This creates `dump.nt`, a dump containing the mapped RDF in N-Triples format.

### Running the unit tests

The unit tests can be executed with `mvn test`.

Some unit tests rely on MySQL being present, and require that two databases are created:

1. A database called `iswc` that contains the data from `src/test/resources/example/iswc-mysql.sql`:

   echo "CREATE DATABASE iswc" | mysql -u root
   mysql -u root iswc < src/test/resources/example/iswc-mysql.sql

2. An empty database called `D2RQ_TEST`.
