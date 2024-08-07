package com.plyushkin.budget;

public interface CategoryNumber<T extends CategoryNumber<T>> {
    T increment();
}
