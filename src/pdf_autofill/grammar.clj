(ns pdf-autofill.grammar
  (:require [instaparse.core :as insta]))

(def as-and-bs
  (insta/parser
    "S = AB*
    AB = A B
    A = 'a'+
    B = 'b'+"))

(def pdf
  (insta/parser
    "pdf        = header <filler> object*
     header     = <'%PDF-'> version
     space      = <#'[\\s*\n]+'>
     version    = #'\\d+\\.\\d+'
     object     = id <' '> generation <' '> <beginobj> content <endobj>
     beginobj   = 'obj'
     endobj     = 'endobj'
     filler     = #'[^(\\d+ \\d+ obj)]*'
     content    = !endobj* | #'.*'
     num        = #'\\d+'
     id         = num
     generation = num
     <rest>  = <#'[^(\\%PDF\\-)].*'>"))

(def file
  (clojure.java.io/resource "hello.pdf"))

(def file-contents
  (slurp file))

; (insta/parser (clojure.java.io/resource "pdf-grammar.bnf"))
