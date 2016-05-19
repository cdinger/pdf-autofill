# pdf-autofill

pdf-autofill is a lightweight web service that automatically fills PDF form
fields with known data. For example, as a student, you may be required to
complete a financial aid form that asks for things like: student ID, first name,
last name, address, etc. The institution requiring this form already knows these
data about you (provided you're authenticated)—they shouldn't need to ask you.

This application should not be the first thing you grab for in making paper
forms go away. This is only meant to ease the pain of PDF forms where PDF forms
are absolutely necessary or where your business refuses to collect data via other
means.

## Usage

Run the pdf-autofill jar directly with `java -jar target/pdf-autofill-0.1.0-SNAPSHOT.jar`
or drop the `war` file into your favorite servlet container.

### Field definitions

A `field` is an atomic, scalar value that can be queried from a database (usually based
on the currently-logged-in user). `fields` are defined as markdown files. The markdown
format allows fields to be defined and maintained by subject matter experts, business
analysts, and programmers alike.

The markdown format for defining a field is simple:

    # field-name

    A brief description of the scalar value this field returns.

    ```SQL
    select blah
    from some_table
    where userid = :principal_id
    ```

An example for querying first name from a Peoplesoft database might look like this:

    # first_name

    Returns the first name of the current user.

    ```SQL
    select first_name
    from ps_names n
    where n.emplid = :principal_id
      and n.eff_status = 'A'
      and n.name_type = 'PRI'
      and n.effdt = (
        select max(effdt)
        from ps_names
        where emplid = n.emplid
          and name_type = n.name_type
          and effdt <= sysdate
      )
    ```

### Configuration

pdf-autofill is configured entirely using environment variables:

#### Required config

##### `DATABASE_SPEC`

`DATABASE_SPEC='{:classname "oracle.jdbc.OracleDriver"
                 :subprotocol "oracle"
                 :subname "thin:@172.27.1.7:1521:SID"
                 :user "user"
                 :password "pwd"}}'`

#### `MARKDOWN_PATH`

`MARKDOWN_PATH=/path/to/your/markdown/files/`

You can optionally configure these additional items:

Using S3 for markdown field definitions:
- `S3_BUCKET=my-pdf-autofill-bucket`
- `S3_ACCESS_KEY=youraccesskey`
- `S3_SECRET_KEY=yoursecretkey`

Customizing the HTTP header that contains the currently logged in user
(defaults to `remote_user`):
- `PRINCIPAL_HEADER=remote_user`

Customize the HTML template used for the index page (listing fields):
- `TEMPLATE_PATH=/path/to/custom/template.html`

### Link to PDF via the web service

Using the autofill service is as simple as changing your existing PDF links to go
through pdf-autofill. For example, if you're currently linking like:

```html
Complete <a href="http://example.com/some_gnarly_form.pdf">this painful form</a>.
```

to:

```html
Complete <a href="https://yourorg.com/pdf-autofill/fill?url=http://example.com/some_gnarly_form.pdf">this painful form</a>.
```

To the user, this service is completely transparent—they get prompted to download the
form (or it opens in their browser). The only difference is, it arrives pre-populated
with known data.

## Development

You will need [Leiningen][] 2.0.0 or above installed to build this project

[leiningen]: https://github.com/technomancy/leiningen

### Running

To start a web server for the application, run:

    lein ring server

### Run tests

    lein test

## License

Copyright © 2015
