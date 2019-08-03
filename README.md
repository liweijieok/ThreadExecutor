# Java线程池

本库是对Java线程池的封装，方便日常使用。

## 引入

```
 implementation 'com.github.liweijie:ThreadExecutor:1.0.0'
```

## 使用

本库提供了5中类型拒绝策略的线程池，通过调用他们的`getInstance()`方法即可获取到不同的线程池操作类。五种类型分别是
1. InfiniteExecutor 无限制的线程池，他的特性是当等待的线程队列已满的时候，会自己启用线程直接执行
2. AbortExecutor 他的特性是当等待的线程池已满的时候，拒绝新的任务并且抛出异常，默认等待队列大小是128，核心线程大小是cpu+1
3. DiscardExecutor 他的特性是当等待的线程池已满的时候，新来的任务不处理，默认等待队列大小256，核心线程大小是cpu+1
3. DiscardOldest 他的特性是当等待的线程池已满的时候，新来的任务不处理，默认等待队列大小128，核心线程大小是cpu+1
3. CallerRunsExecutor 他与InfiniteExecutor类型，只不过当队列已满的时候，他是执行需要依靠外部来执行而不是像InfiniteExecutor直接起线程

我们也可以自己通过调用`ExecutorManager.config(ExecutorConfig)`来进行配置每一种线程池的具体大小，非核心线程池的超时时间，等待队列类型等

同时也提供了SystemThreadManager来获取java提供的常用线程值，只不过每次获取都是new的形式。

例如：

```
DiscardExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Log.e("MainActivity", "DiscardExecutor");
            }
        });
        InfiniteExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Log.e("MainActivity", "InfiniteExecutor");
            }
        });
        DiscardOldest.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Log.e("MainActivity", "DiscardOldest");
            }
        });
        CallerRunsExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Log.e("MainActivity", "CallerRunsExecutor");
            }
        });
        AbortExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Log.e("MainActivity", "AbortExecutor");
            }
        });

```

## Java线程池的梳理

在Android开发过程难以避免需要新开线程去执行异步任务,除了使用Android自带的AsyncTask或者是一些IntentService之类系统提供好的，我们一般其他都需要使用`new Thread的方式`，但是这样子效率不高，损耗较大，因为线程的创建是耗费资源的，合理的使用线程池可以避免这一点。本文并不是使用系统提供的类型`Executors.newCachedThreadPool()`的方式，而是使用自己手动创建的方式，毕竟那些方法底部也是使用了`ThreadPoolExecutor`的构造函数去实现的。本文是直接自己创建`ThreadPoolExecutor`的方式。

### Java的线程池

java线程池的优点是他可以做到线程在执行完了任务之后，可以选择该线程并不会被销毁而是被休眠的形式，等待有新任务直接执行，从而避免了线程创建的资源消耗。

### 相关方法介绍

* 构造方法
```
public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize, long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue)
public ThreadPoolExecutor(int corePoolSize,  int maximumPoolSize,  long keepAliveTime,  TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler)
public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize, long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory)
public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize,  long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler)
```

参数说明：
```
corePoolSize: 线程池的基本大小，即在没有任务的时候需要执行的时候线程池的大小，并且只有工作队列满了的情况下才会创建超出这个数量的线程。但是这些核心线程不是在创建线程池的时候就创建，除非调用了`prestartCoreThread()/prestartAllCoreThreads()`的时候
maximumPoolSize：线程中允许的最大线程数，线程池中的当前线程数目不会超过这个值。如果当前线程池中的线程都在执行，而且大小没有超出我们maximumPoolSize的时候，才会继续创建线程执行任务。我们可以通过调用`setMaximumPoolSize(int)`来重新设置线程池的大小。
keepAliveTime：表示线程在没有任务执行的时候最多保持多久时间就会终止。在默认情况下，只有线程池的线程数大于corePoolSize的时候，keepAliveTime才会起作用，直到线程池中的线程数不大于corePoolSize时。即当线程池中的线程数大于coolPllSize时，如果一个线程的空闲时间达到keepAliveTime，则会终止，知道线程池的线程数不超过cooPoolSize。但是如果调用了`allowCoreThreadTimeOut(boolean)`方法，在线程池数不大于corePoolSize时，keepAliveTime参数也会起作用，直到线程池中的线程数为0。
unit：keepAliveTime的时间单位
workQueue：任务队列的类型，就是当线程池使用完了之后，任务等待所处于的队列，常见的有三种。
threadFactory：线程构造器，常用于指定线程组的一些列的线程的名称，new出一个线程。
handler：线程池的拒绝策略，也就是任务队列满了之后，应该如何处理新来的任务。java提供了4中，也可以自己实现。
```

### 并发队列的选择

也就是workQueue的选择，常见使用如下的实现
1. `ArrayBlockingQueue`
    初始化的时候需要指定大小，而且不可变更。
2. `LinkedBlockingQueue`
    他是一个单项的链表，使用链表实现队列
3. `SynchronousQueue`
    他是一个阻塞队列，每一个put操作必须等待另外一个take操作，反之亦然，他是没有容量的。

他们都是`BlockingQueue`接口的实现类，`BlockingQueue`接口方法主要有：
```
boolean add(E e) //添加一个元素，假如已经满了会抛出IllegalStateException异常
boolean offer(E e); //添加一个元素，假如已经满了会返回false
boolean offer(E e, long timeout, TimeUnit unit) //添加一个元素，假如已经满了，那么将会等待，直到超时还是满的话将会返回false
void put(E e) throws InterruptedException; //添加一个元素假如已经满了，那么将会阻塞，
E take() throws InterruptedException; //获取一个元素，当集合为空的时候，会阻塞
 E poll(long timeout, TimeUnit unit) throws InterruptedException;//删除头部元素，假如为空则会返回null
boolean remove(Object o);//基于元素进行删除，如果删除成功则返回true，失败则返回false
boolean contains(Object o); //基于元素判断，如果已经找到返回true，没有则返回false
 int drainTo(Collection<? super E> c);//移除队列中所有可用的元素并添加到传入的集合中
int drainTo(Collection<? super E> c, int maxElements);//移除队列中所有可用的元素并添加到传入的集合中，并且制定最大移植的数目
```

### 线程池的拒绝策略

Java提供了四种拒绝策略，我们也可以实现`RejectedExecutionHandler`来实现自定义的拒绝策略。提供的有
1. `AbortPolicy`
    当任务等待队列满了，还继续添加任务，则会抛出异常
2. `DiscardPolicy`
    当任务满了，还继续添加则抛弃不做处理    
3. `DiscardOldestPolicy`
    当任务满了，还添加则丢弃老的任务队列
4. `CallerRunsPolicy`
    当任务满了，则丢给发起任务的任务方处理，即不使用线程池的线程执行而是外部自己使用线程池执行


参照我的github提供的库:[ThreadExecutor](https://github.com/liweijieok/ThreadExecutor)

### 系统提供的线程池

1. `Executors.newCachedThreadPool()`
    它提供的是无限制任务个数的线程池，使用`SynchronousQueue`作为待执行的任务队列，maxSize为`Integer.MAX_VALUE`，线程在执行完成没有下个任务执行的时候的超时时间为60s，可指定ThreadFactory来生成线程并设置线程的一些属性，比如名称等。
2. `Executors.newFixedThreadPool(int nThreads)`
    他提供固定线程池大小的线程池，可外部置顶，corePoolSize与maxSize一致，所有不需要keepLiveTime，使用`LinkedBlockingQueue`，无等待队列大小限制。其中`Executors.newSingleThreadExecutor()`就是调用了该方法，将core设置为了1。
3. `Executors.newSingleThreadExecutor()`
    它调用了上面的方法，他是只提供了只有一个核心线程。所以他的所有任务都是同一个线程执行，而且是串行执行的。
4. `Executors.newScheduledThreadPool(int corePoolSize)`
    它提供了一种定时的任务队列,支持定时的重复执行一些任务，或者是延时任务。他是没有maxSize的，最大为`Integer.MAX_VALUE`，使用的队列是`DelayedWorkQueue`。他是直接继承自`ExecutorService`而非`AbstractExecutorService`，其他几个都是继承`AbstractExecutorService`.
5. `Executors.newSingleThreadScheduledExecutor()`
    他也是执行定时任务而且只有一个定时线程，它继承的是`AbstractExecutorService`，而不是与`ScheduledExecutorService`一样。但是他内部有一个`ScheduledExecutorService`，使用类具体执行。 

### 推荐阅读

[深入理解 Java 线程池：ThreadPoolExecutor](https://juejin.im/entry/58fada5d570c350058d3aaad)

