/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.caddarik.pair;

import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author Cédric Chavériat
 * @param <K>
 * @param <V>
 */
public class Pair<K, V> extends SimpleEntry<K, V>{
    
    /**
     * 
     * @param key
     * @param value 
     */
    public Pair(K key, V value) {
        super(key, value);
    }
    
    /**
     * 
     * @param entry 
     */
    public Pair(Entry<K, V> entry) {
        super(entry);
    }
    
    /**
     * 
     * @param <K2>
     * @param mapper
     * @return 
     */
    public <K2> Pair<K2, V> mapKey(Function<K, K2> mapper) {
        return mapKey(this, mapper);
    }
    
    /**
     * 
     * @param <K2>
     * @param mapper
     * @return 
     */
    public <K2> Stream<Pair<K2, V>> flatMapKey(Function<K, Stream<K2>> mapper) {
        return flatMapKey(this, mapper);
    }
    
    /**
     * 
     * @param <V2>
     * @param mapper
     * @return 
     */
    public <V2> Pair<K, V2> mapValue(Function<V, V2> mapper) {
        return mapValue(this, mapper);
    }
    
    /**
     * 
     * @param <V2>
     * @param mapper
     * @return 
     */
    public <V2> Stream<Pair<K, V2>> flatMapValue(Function<V, Stream<V2>> mapper) {
        return flatMapValue(this, mapper);
    }
    
    /**
     * 
     * @return 
     */
    public Pair<V, K> invert() {
        return invert(this);
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    public static <K extends Comparable<? super K>, V> Comparator<Entry<K,V>> comparingByKey() {
        return Entry.comparingByKey();
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    public static <K, V extends Comparable<? super V>> Comparator<Entry<K,V>> comparingByValue() {
        return Entry.comparingByValue();
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param cmp
     * @return 
     */
    public static <K extends Comparable<? super K>, V> Comparator<Entry<K,V>> comparingByKey(Comparator<? super K> cmp) {
        return Entry.comparingByKey(cmp);
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param cmp
     * @return 
     */
    public static <K, V extends Comparable<? super V>> Comparator<Entry<K,V>> comparingByValue(Comparator<? super V> cmp) {
        return Entry.comparingByValue(cmp);
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param <E>
     * @return 
     */
    public static <K, V, E extends Entry<K, V>> Collector<E, ?, Map<K, V>> toMap() {
        return Collectors.toMap(Entry::getKey, Entry::getValue);
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param <E>
     * @param mergeFunction
     * @return 
     */
    public static <K, V, E extends Entry<K, V>> Collector<E, ?, Map<K, V>> toMap(BinaryOperator<V> mergeFunction) {
        return Collectors.toMap(Entry::getKey, Entry::getValue, mergeFunction);
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param <E>
     * @param mergeFunction
     * @param mapSupplier
     * @return 
     */
    public static <K, V, E extends Entry<K, V>> Collector<E, ?, Map<K, V>> toMap(BinaryOperator<V> mergeFunction, Supplier<Map<K,V>> mapSupplier) {
        return Collectors.toMap(Entry::getKey, Entry::getValue, mergeFunction, mapSupplier);
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param <E>
     * @return 
     */
    public static <K, V, E extends Entry<K, V>> Collector<E, ?, Map<K, List<V>>> groupingBy() {
        return Collectors.groupingBy(Entry::getKey, Collectors.mapping(Entry::getValue, Collectors.toList()));
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param <E>
     * @param mapSupplier
     * @return 
     */
    public static <K, V, E extends Entry<K, V>> Collector<E, ?, Map<K, List<V>>> groupingBy(Supplier<Map<K,List<V>>> mapSupplier) {
        return Collectors.groupingBy(Entry::getKey, mapSupplier, Collectors.mapping(Entry::getValue, Collectors.toList()));
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param <E>
     * @return 
     */
    public static <K, V, E extends Entry<K, V>> Collector<E, ?, Map<K, Long>> counting() {
        return Collectors.groupingBy(Entry::getKey, Collectors.counting());
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param <E>
     * @param mapSupplier
     * @return 
     */
    public static <K, V, E extends Entry<K, V>> Collector<E, ?, Map<K, Long>> counting(Supplier<Map<K,Long>> mapSupplier) {
        return Collectors.groupingBy(Entry::getKey, mapSupplier, Collectors.counting());
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param <K2>
     * @param e
     * @param mapper
     * @return 
     */
    public static <K, V, K2> Pair<K2, V> mapKey(Entry<K, V> e, Function<K, K2> mapper) {
        return new Pair<>(mapper.apply(e.getKey()), e.getValue());
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param <K2>
     * @param e
     * @param mapper
     * @return 
     */
    public static <K, V, K2> Stream<Pair<K2, V>> flatMapKey(Entry<K, V> e, Function<K, Stream<K2>> mapper) {
        return mapper.apply(e.getKey()).map(k2 -> new Pair<>(k2, e.getValue()));
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param <V2>
     * @param e
     * @param mapper
     * @return 
     */
    public static <K, V, V2> Pair<K, V2> mapValue(Entry<K, V> e, Function<V, V2> mapper) {
        return new Pair<>(e.getKey(), mapper.apply(e.getValue()));
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param <V2>
     * @param e
     * @param mapper
     * @return 
     */
    public static <K, V, V2> Stream<Pair<K, V2>> flatMapValue(Entry<K, V> e, Function<V, Stream<V2>> mapper) {
        return mapper.apply(e.getValue()).map(v2 -> new Pair<>(e.getKey(), v2));
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param e
     * @return 
     */
    public static <K, V> Pair<V, K> invert(Entry<K, V> e) {
        return new Pair<>(e.getValue(), e.getKey());
    }
    
    /**
     * 
     * @param <T>
     * @param l
     * @return 
     */
    public static <T> Stream<Pair<T, T>> combine(List<T> l) {
        int size = l.size();
        return IntStream.range(0, size - 1)
                .mapToObj(i -> (Integer) i)
                .flatMap(i -> IntStream.range(i + 1, size)
                .mapToObj(j -> new Pair<>(l.get(i), l.get(j))));
    }
}
