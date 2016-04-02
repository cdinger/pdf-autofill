(ns pdf-autofill.pdf
  (:import [org.apache.pdfbox.pdmodel PDDocument]
           [java.net URL]))

(defn document [s]
  (let [url (URL. s)
        stream (.openStream url)]
    (PDDocument/load stream)))

(defn fields [doc]
  (-> doc
      .getDocumentCatalog
      .getAcroForm
      .getFields))

(defn field-value [doc fieldname]
  (let [field (some #(when (= fieldname (.getPartialName %)) %) (fields doc))]
    (.getValue field)))

(defn set-values [pdf values]
  (doseq [field (filter #(contains? values (.getPartialName %)) (fields pdf))]
    (.setValue field (get values (.getPartialName field))))
  pdf)

(defn fieldnames [doc]
  (map #(.getPartialName %) (fields doc)))

(defn save-pdf-to-stream [doc, stream]
  (.save doc stream))
