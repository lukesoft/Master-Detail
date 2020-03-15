package com.lukemadzedze.zapperdisplay.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.lukemadzedze.zapperdisplay.utils.Status.ERROR;
import static com.lukemadzedze.zapperdisplay.utils.Status.LOADING;
import static com.lukemadzedze.zapperdisplay.utils.Status.SUCCESS;

public class Resource<T> {
    @NonNull
    public final Status status;
    public final T data;
    @Nullable
    public final String message;

    private Resource(@NonNull Status status, T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    public boolean isSuccess() {
        return status == SUCCESS && data != null;
    }

    public boolean isLoading() {
        return status == LOADING;
    }

    public boolean isLoaded() {
        return status != LOADING;
    }
}