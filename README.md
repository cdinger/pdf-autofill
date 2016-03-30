# pdf-autofill

pdf-autofill is a lightweight web service that automatically fills PDF form
fields with known data. For example, as a student, you may be required to
complete a financial aid form that asks for things like: student ID, first name,
last name, address, etc. The institution requiring this form already knows these
data about you (provided you're authenticated)—they shouldn't need to ask you.

## Usage

Run the pdf-autofill jar directly with `java -jar pdf-autofill` or drop it into
your favorite servlet container.

### Configuration

pdf-autofill uses environment variables to configure the database connection and
directory location for markdown files:

`DATABASE_URL=somejdbcconnectionurl`
`MARKDOWN_PATH=/some/file/path/`

### Provide field definitions

Fields can be provided in two ways: markdown files, or clojure code. Markdown files
great for simple, query-based fields. There's no programming necessary (other than
writing the query), so it's a great fit for maintenance by analysts.

If you need more flexibility or if you need to connect to non-SQL sources, you
can choose to write custom clojure/java/ruby/your-favorite-jvm-language code to
provide data to fields by implementing the `pdf-autofill.field` protocol.

### Link to PDF via the web service

Using the autofill service is as simple as changing your existing PDF links to go
through pdf-autofill. For example, if you're currently linking like:

```html
Complete <a href="some_gnarly_form.pdf">this painful form</a>.
```

to:

```html
Complete <a href="https://yourorg.com/pdf-autofill/autofill?some_gnarly_form.pdf">this painful form</a>.
```

To the user, this service is completely transparent—they get prompted to download the
form (or it opens in their browser). The only difference is, it arrives pre-populated
with known data. And the world is a better place.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2015 FIXME
