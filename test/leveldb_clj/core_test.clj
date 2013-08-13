(ns leveldb-clj.core-test
  (:use clojure.test)
  (:require [leveldb-clj.core :as db]))

(deftest test-basic-operations
  (testing "with good data"
    (testing "put and get"
      (with-open [test (db/open "/tmp/tests")]
        (db/put test (.getBytes "testKey1") (.getBytes "testValue1"))
        (is (= "testValue1" (String. (db/get test (.getBytes "testKey1")))))))
    (testing "delete"
      (with-open [test (db/open "/tmp/tests")]
        (db/put test (.getBytes "testKey2") (.getBytes "testValue2"))
        (is (= "testValue2" (String. (db/get test (.getBytes "testKey2")))))
        (db/delete test (.getBytes "testKey2"))
        (is (nil? (db/get test (.getBytes "testKey2"))))))))


(deftest test-batch2
        (testing "better version" 
                 (with-open [test (db/open "/tmp/tests")
                              test-batch (db/create-write-batch test)]
                             (db/batch-put test-batch (.getBytes "batchKey1") (.getBytes "batchValue1"))
                             (db/batch-put test-batch (.getBytes "batchKey2") (.getBytes "batchValue2"))
                             (db/batch-put test-batch (.getBytes "batchKey3") (.getBytes "batchValue3"))
                             (db/batch-delete test-batch (.getBytes "batchKey3"))
                             (db/write-batch test test-batch))
                  (with-open [test (db/open "/tmp/tests")]
                              (is (= "batchValue1" (String. (db/get test (.getBytes "batchKey1")))))
                              (is (= "batchValue2" (String. (db/get test (.getBytes "batchKey2")))))
                              (is (nil? (db/get test (.getBytes "batchKey3")))))))


