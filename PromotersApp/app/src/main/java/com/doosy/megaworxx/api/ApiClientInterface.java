package com.doosy.megaworxx.api;

public interface ApiClientInterface<T, E> {

    T consumeApi(T t, E e);
}
