(ns leveldb-clj.core-test
  (:use clojure.test
        [leveldb-clj.core :as db]))

(deftest test-db
  (testing "with good data"
    (testing "put and get"
      (db/with-db [test (db/open "/tmp/tests")]
        (db/put test (.getBytes "testKey1") (.getBytes "testValue1"))
        (is (= "testValue1" (String. (db/get test (.getBytes "testKey1")))))))
    (testing "delete"
      (db/with-db [test (db/open "/tmp/tests")]
        (db/put test (.getBytes "testKey2") (.getBytes "testValue2"))
        (is (= "testValue2" (String. (db/get test (.getBytes "testKey2")))))
        (db/delete test (.getBytes "testKey2"))
        (is (nil? (db/get test (.getBytes "testKey2"))))))))