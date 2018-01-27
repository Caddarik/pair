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
public class Pair<K, V> extends SimpleEntry<K, V> {

    /**
     * Creates an pair representing a mapping from the specified key to the
     * specified value.
     *
     * @param key the key represented by this entry
     * @param value the value represented by this entry
     */
    public Pair(K key, V value) {
        super(key, value);
    }

    /**
     *
     * Creates an pair representing the same mapping as the specified entry.
     *
     * @param entry the entry to copy
     */
    public Pair(Entry<K, V> entry) {
        super(entry);
    }

    /**
     *
     * Returns a new pair with the key change by the function mapper
     *
     * @param <K2> the new type of the key
     * @param mapper the function that maps the old key in the new one
     * @return a new pair with a new key
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
     * Returns a new pair with the value change by the function mapper
     *
     * @param <V2> the new type of the value
     * @param mapper the function that maps the old key in the new one
     * @return a new pair with a new value
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
     * Returns a new pair with the key and the value inverted
     *
     * @return a new pair with invert key value
     */
    public Pair<V, K> invert() {
        return invert(this);
    }

    /**
     *
     * Returns the comparator of Map.Entry.comparingByKey()
     *
     * Returns a comparator that compares {@link Map.Entry} in natural order on
     * key.
     *
     * <p>
     * The returned comparator is serializable and throws {@link
     * NullPointerException} when comparing an entry with a null key.
     *
     * @param <K> the {@link Comparable} type of then map keys
     * @param <V> the type of the map values
     * @return a comparator that compares {@link Map.Entry} in natural order on
     * key.
     * @see Comparable
     * @see
     */
    public static <K extends Comparable<? super K>, V> Comparator<Entry<K, V>> comparingByKey() {
        return Entry.comparingByKey();
    }

    /**
     *
     * Returns the comparator of Map.Entry.comparingByValue()
     *
     * Returns a comparator that compares {@link Map.Entry} in natural order on
     * value.
     *
     * <p>
     * The returned comparator is serializable and throws {@link
     * NullPointerException} when comparing an entry with null values.
     *
     * @param <K> the type of the map keys
     * @param <V> the {@link Comparable} type of the map values
     * @return a comparator that compares {@link Map.Entry} in natural order on
     * value.
     * @see Comparable
     */
    public static <K, V extends Comparable<? super V>> Comparator<Entry<K, V>> comparingByValue() {
        return Entry.comparingByValue();
    }

    /**
     *
     * Returns the comparator of Map.Entry.comparingByKey(Comparator)
     *
     * Returns a comparator that compares {@link Map.Entry} by key using the
     * given {@link Comparator}.
     *
     * <p>
     * The returned comparator is serializable if the specified comparator is
     * also serializable.
     *
     * @param <K> the type of the map keys
     * @param <V> the type of the map values
     * @param cmp the key {@link Comparator}
     * @return a comparator that compares {@link Map.Entry} by the key.
     */
    public static <K extends Comparable<? super K>, V> Comparator<Entry<K, V>> comparingByKey(Comparator<? super K> cmp) {
        return Entry.comparingByKey(cmp);
    }

    /**
     *
     * Returns the comparator of Map.Entry.comparingByValue(Comparator)
     *
     * Returns a comparator that compares {@link Map.Entry} by value using the
     * given {@link Comparator}.
     *
     * <p>
     * The returned comparator is serializable if the specified comparator is
     * also serializable.
     *
     * @param <K> the type of the map keys
     * @param <V> the type of the map values
     * @param cmp the value {@link Comparator}
     * @return a comparator that compares {@link Map.Entry} by the value.
     */
    public static <K, V extends Comparable<? super V>> Comparator<Entry<K, V>> comparingByValue(Comparator<? super V> cmp) {
        return Entry.comparingByValue(cmp);
    }

    /**
     *
     * Returns a {@code Collector} that accumulates elements into a {@code Map}
     * whose keys are the keys of the entries and values are the values of the
     * entries
     *
     * <p>
     * If the mapped keys contains duplicates (according to
     * {@link Object#equals(Object)}), an {@code IllegalStateException} is
     * thrown when the collection operation is performed. If the mapped keys may
     * have duplicates, use {@link #toMap(Function, Function, BinaryOperator)}
     * instead.
     *
     * @param <K> the type of the key of both the map and the pairs
     * @param <V> the type of the value of both the map and the pairs
     * @param <E> the type of the entries
     * @return a {@code Collector} which collects elements into a {@code Map}
     * whose keys are the keys of the entries and values are the values of the
     * entries
     */
    public static <K, V, E extends Entry<K, V>> Collector<E, ?, Map<K, V>> toMap() {
        return Collectors.toMap(Entry::getKey, Entry::getValue);
    }

    /**
     *
     * Returns a {@code Collector} that accumulates elements into a {@code Map}
     * whose keys are the keys of the entries and values are the values of the
     * entries
     *
     * <p>
     * If the mapped keys contains duplicates (according to
     * {@link Object#equals(Object)}), the value mapping function is applied to
     * each equal element, and the results are merged using the provided merging
     * function.
     *
     * @param <K> the type of the key of both the map and the pairs
     * @param <V> the type of the value of both the map and the pairs
     * @param <E> the type of the entries
     * @param mergeFunction a merge function, used to resolve collisions between
     * values associated with the same key, as supplied to
     * {@link Map#merge(Object, Object, BiFunction)}
     * @return a {@code Collector} which collects elements into a {@code Map}
     * whose keys are the keys of the entries and values are the values of the
     * entries and combining them using the merge function
     */
    public static <K, V, E extends Entry<K, V>> Collector<E, ?, Map<K, V>> toMap(BinaryOperator<V> mergeFunction) {
        return Collectors.toMap(Entry::getKey, Entry::getValue, mergeFunction);
    }

    /**
     *
     * Returns a {@code Collector} that accumulates elements into a {@code Map}
     * whose keys and values are the result of applying the provided mapping
     * functions to the input elements.
     *
     * <p>
     * If the mapped keys contains duplicates (according to
     * {@link Object#equals(Object)}), the value mapping function is applied to
     * each equal element, and the results are merged using the provided merging
     * function. The {@code Map} is created by a provided supplier function.
     *
     * @param <K> the type of the key of both the map and the pairs
     * @param <V> the type of the value of both the map and the pairs
     * @param <E> the type of the entries
     * @param mergeFunction a merge function, used to resolve collisions between
     * values associated with the same key, as supplied to
     * {@link Map#merge(Object, Object, BiFunction)}
     * @param mapSupplier a function which returns a new, empty {@code Map} into
     * which the results will be inserted
     * @return a {@code Collector} which collects elements into a {@code Map}
     * whose  keys are the keys of the entries and values are the values of the
     * entries, and whose values are the result of applying a value mapping
     * function to all input elements equal to the key and combining them
     * using the merge function
     */
    public static <K, V, E extends Entry<K, V>> Collector<E, ?, Map<K, V>> toMap(BinaryOperator<V> mergeFunction, Supplier<Map<K, V>> mapSupplier) {
        return Collectors.toMap(Entry::getKey, Entry::getValue, mergeFunction, mapSupplier);
    }

    /**
     *
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
    public static <K, V, E extends Entry<K, V>> Collector<E, ?, Map<K, List<V>>> groupingBy(Supplier<Map<K, List<V>>> mapSupplier) {
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
    public static <K, V, E extends Entry<K, V>> Collector<E, ?, Map<K, Long>> counting(Supplier<Map<K, Long>> mapSupplier) {
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
