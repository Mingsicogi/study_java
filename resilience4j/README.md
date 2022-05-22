# Resilience4j
#### Resilience4j is a lightweight, easy-to-use fault tolerance library inspired by Netflix Hystrix, but designed for Java 8 and functional programming

----
1. CircuitBreaker
   - The CircuitBreaker is implemented via a finite state machine with three normal states: CLOSED, OPEN and HALF_OPEN and two special states DISABLED and FORCED_OPEN.
   - sample code : minssogi.study.resilience4j.circuitBreaker
   - https://resilience4j.readme.io/docs/circuitbreaker
   - https://godekdls.github.io/Resilience4j/circuitbreaker
2. Bulkhead
   - Resilience4j provides two implementations of a bulkhead pattern that can be used to limit the number of concurrent execution
   - sample code : minssogi.study.resilience4j.bulkHead
   - https://resilience4j.readme.io/docs/bulkhead
   - https://godekdls.github.io/Resilience4j/bulkhead/
3. RateLimiter
4. Retry
5. TimeLimiter