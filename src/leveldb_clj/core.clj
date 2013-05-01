(ns leveldb-clj.core
  (:require [clojure.java.io :as io])
  (:import (org.iq80.leveldb Options DB))
  (:import org.fusesource.leveldbjni.JniDBFactory))


(defmacro with-db
  "opens the leveldb database"
  [bindings & body]
  {:pre [(vector? bindings)
         (even? (count bindings))]}
  (cond
   (= (count bindings) 0) `(do ~@body)
   (symbol? (bindings 0)) `(let ~(subvec bindings 0 2)
                             (try
                               (with-db ~(subvec bindings 2) ~@body)
                               (finally
                                 (.close ~(bindings 0)))))
   :else (throw (IllegalArgumentException.
                 "Bindings must be symbols."))))


(defn open
  [path]
  (let [opts (Options.)
        dbfile (io/file path)
        factory (JniDBFactory.)]
    (.open factory dbfile opts)))


(defn put
  [db key value]
  (.put db key value))

(defn get
  [db key]
  (.get db key))

(defn delete
  [db key]
  (.delete db key))

(defn create-write-batch
  "returns a WriteBatch object"
  [db]
  (.createWriteBatch db))

(defn batch-put
  [batch key value]
  (.put batch key value))

(defn batch-delete
  [batch key]
  (.delete batch key))

(defn write-batch
  [db batch]
  (.write db batch))

