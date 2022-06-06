# Caffeine is a high performance Java caching library providing a near optimal hit rate

A Cache is similar to ConcurrentMap, but not quite the same. 
The most fundamental difference is that a ConcurrentMap persists all elements that are added to it until they are explicitly removed. 
A Cache on the other hand is generally configured to evict entries automatically, in order to constrain its memory footprint. 
In some cases a LoadingCache or AsyncLoadingCache can be useful even if it doesn't evict entries, due to its automatic cache loading.


### Caffeine provide flexible construction to create a cache with a combination of the following features:

***1. automatic loading of entries into the cache, optionally asynchronously***
2. size-based eviction when a maximum is exceeded based on frequency and recency
3. time-based expiration of entries, measured since last access or last write
4. asynchronously refresh when the first stale request for an entry occurs
5. keys automatically wrapped in weak references
6. values automatically wrapped in weak or soft references
7. notification of evicted (or otherwise removed) entries
8. writes propagated to an external resource
9. accumulation of cache access statistics
10. To improve integration, JSR-107 JCache and Guava adapters are provided in extension modules. JSR-107 standardizes a 
    Java 6 based API to minimize vendor specific code at the cost of features and performance. Guava's Cache is the predecessor library and the adapters provide a simple migration strategy.

Contributions are welcome. Please read the design document, developer setup guide, and roadmap.

