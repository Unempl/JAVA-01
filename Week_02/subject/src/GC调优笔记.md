#第二课——GC调优学习笔记
- Serial GC
- Parallel GC
- ConcMarkSweep GC
- G1 GC
- sb压测

###Serial GC
``-Xms128m -Xmx128m -XX:+UseSerialGC -XX:+PrintGCDetails``  
在此条件下，会发生java.lang.OutOfMemoryError: Java heap space  
``-Xms512m -Xmx512m -XX:+UseSerialGC -XX:+PrintGCDetails``  
在此条件下，没有发生Full GC，执行结束!共生成对象次数:7811  

###Parallel GC
``-Xms128m -Xmx128m -XX:+UseParallelGC -XX:+PrintGCDetails``  
在此条件下，会发生java.lang.OutOfMemoryError: Java heap space  
``-Xms512m -Xmx512m -XX:+UseParallelGC -XX:+PrintGCDetails``  
在此条件下，没有发生Full GC，执行结束!共生成对象次数:7522  

###ConcMarkSweep GC
``-Xms128m -Xmx128m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails``  
在此条件下，会发生java.lang.OutOfMemoryError: Java heap space  
``-Xms512m -Xmx512m -XX:+UseConcMarkSweepGC -XX:+PrintGCDetails``  
在此条件下，没有发生Full GC，执行结束!共生成对象次数:8441  

###G1 GC
``-Xms128m -Xmx128m -XX:+UseG1GC -XX:+PrintGCDetails``  
在此条件下，会发生java.lang.OutOfMemoryError: Java heap space  
``-Xms512m -Xmx512m -XX:+UseG1GC -XX:+PrintGCDetails``  
在此条件下，没有发生Full GC，执行结束!共生成对象次数:8258  

###sb压测
Step1``java -jar -Xms128m -Xmx128m -XX:+UseParallelGC -XX:+PrintGC gateway-server-0.0.1-SNAPSHOT.jar``  
Step2``sb -u http://localhost:8088/api/hello -n 100000``  
用128m内存运行该jar包，压测条件10w RPS，只发生了一次Full GC
```
#如果将Xmx和Xms都设为4g
执行结束!共生成对象次数:9935 Serial
执行结束!共生成对象次数:12171 Parallel
执行结束!共生成对象次数:12099 ConcMarkSweep
执行结束!共生成对象次数:10651 G1
```
##总结
经过对Xms，Xmx的反复测试，可以认为内存越大，使用Parallel GC的调优越明显，相对来说，Parallel GC的缺点是发生Full GC，STW时间会比较长，而ConcMarkSweep/G1 GC由于分段进行SWT时间会比较短，而在高并发的大环境下，Serial GC还是单线程工作显然有些吃力。

