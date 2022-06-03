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
   - You can simply decline this over limit request, or build a queue to execute them later or combine these two approaches in some way.
   - sample code : minssogi.study.resilience4j.rateLimiter
   - https://resilience4j.readme.io/docs/ratelimiter
4. Retry
   - Just like the CircuitBreaker module, this module provides an in-memory RetryRegistry which you can use to manage (create and retrieve) Retry instances.
   - sample code : minssogi.study.resilience4j.retry
   - https://resilience4j.readme.io/docs/retry
5. TimeLimiter
   - return type should be CompletionStage

----
### Resilience4j + Spring boot 2
 - https://resilience4j.readme.io/docs/getting-started-3
 - 