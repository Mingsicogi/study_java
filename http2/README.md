# Removing HTTP/2 Server Push from Chrome


## Why is this being removed?
- HTTP/2 Server Push allowed websites to proactively send resources needed by the page instead of waiting for them to be requested. However, it was problematic as Jake Archibald wrote about previously, and the performance benefits were often difficult to realize. As a result, it was not used much with only 1.25% of HTTP/2 sites making use of this feature. 
Analysis of the use of HTTP/2 Server Push has mixed results (Chrome, Akamai), without a clear net performance gain and in many cases performance regressions. Push was not implemented in many HTTP/3 servers and clients—even though it was included in the specification. For much of the web that is using the newer HTTP/3, Push has effectively been retired already. When rerunning that analysis more recently, we see that 1.25% HTTP/2 support by sites dropped to 0.7%.


## Alternatives to HTTP/2 Server Push
- 103 Early Hints is a much less error-prone alternative with many of the same upsides as Push, and a lot less of the downsides. Rather than the server pushing resources, 103 Early Hints sends only hints to the browser of resources that it may benefit from requesting immediately. This leaves the browser in control of deciding whether it needs these or not—for example if it already has those resources in the HTTP cache. Preloading critical resources is another alternative that allows the page and the browser to work together to preemptively load critical resources early in the page load. While this does require the page itself to be sent first—so is not quite as fast as either Server Push nor Early Hints—it has the added benefit of not delaying that critical page resource, which can happen with both of those solutions.


## Conclusion
- The web needs to be able to try things, and discard them when they are not used. Although the potential for Push sounded great, in reality using it was more problematic than envisaged. However, we learned a lot from Push that went into the design of 103 Early Hints. Now it's time to complete the progression and move away from Push.