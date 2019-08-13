package com.changwoo.project.common;


import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class DataRangeResponse<T> {

    private long total;
    private List<T> data;

    public DataRangeResponse(long total, List<T> items) {
        this.total = total;
        this.data = items;
    }

    public long getTotal() {
        return total;
    }

    public List<T> getData() {
        return data;
    }
    
    public static <T> DataRangeResponse<T> empty() {
        return new DataRangeResponse<T>(0, Collections.emptyList());
    }
    
    public static <T> DataRangeResponse<T> with(List<T> items) {
        return new DataRangeResponse<T>(items.size(), items);
    }
    
    public static <T> DataRangeResponse<T> with(long total, List<T> items) {
        return new DataRangeResponse<T>(total, items);
    }
    
    public static <I, O> DataRangeResponse<O> with(List<I> items, Function<I, O> converter) {
        List<O> converted = new ArrayList<>(items.size());
        for (I each : items) {
            converted.add(converter.apply(each));
        }
        return new DataRangeResponse<O>(items.size(), converted);
    }
    
    public static <I, O> DataRangeResponse<O> with(Page<I> page, Function<I, O> converter) {
        List<I> input = page.getContent();
        List<O> converted = new ArrayList<>(input.size());
        for (I each : input) {
            converted.add(converter.apply(each));
        }
        return new DataRangeResponse<O>(page.getTotalElements(), converted);
    }
}
