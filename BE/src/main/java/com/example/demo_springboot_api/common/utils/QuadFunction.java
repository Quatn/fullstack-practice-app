package com.example.demo_springboot_api.common.utils;

@FunctionalInterface
public interface QuadFunction<A, B, C, D, R> {
  R apply(A a, B b, C c, D d);
}
