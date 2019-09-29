(ns toy-robot.direction-test
  (:require [clojure.test :refer :all]
            [toy-robot.direction :refer :all]))

(deftest direction-left-test
  (testing "should return :west when :north" (is (= :west (direction-left :north))))
  (testing "should return :north when :east" (is (= :north (direction-left :east))))
  (testing "should return :east when :south" (is (= :east (direction-left :south))))
  (testing "should return :south when :west" (is (= :south (direction-left :west)))))

(deftest direction-right-test
  (testing "should return :east when :north" (is (= :east (direction-right :north))))
  (testing "should return :south when :east" (is (= :south (direction-right :east))))
  (testing "should return :west when :south" (is (= :west (direction-right :south))))
  (testing "should return :north when :west" (is (= :north (direction-right :west)))))

(deftest string->direction-test
  (testing "should return :north when NORTH" (is (= :north (string->direction "NORTH")))))

(deftest direction->string-test
  (testing "should return NORTH when :north" (is (= "NORTH" (direction->string :north)))))