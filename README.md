# Mockito Tutorial for Beginners

## 测试替身（Test Double）的一些概念

*在单元测试时，使用Test Double减少对被测对象的依赖，使得测试更加单一。同时，让测试案例执行的时间更短，运行更加稳定，同时能对SUT内部的输入输出进行验证，让测试更加彻底深入。但是，Test Double也不是万能的，Test Double不能被过度使用，因为实际交付的产品是使用实际对象的，过度使用Test Double会让测试变得越来越脱离实际。要理解测试替身，需要了解一下Dummy Objects、Test Stub、Test Spy、Fake Object这几个概念，下面我们对这些概念分别进行说明。*

* **Dummy Objects**

Dummy Objects泛指在测试中必须传入的对象，而传入的这些对象实际上并不会产生任何作用，仅仅是为了能够调用被测对象而必须传入的一个东西。

* **Test Stub**

测试桩是用来接受SUT内部的间接输入（indirect inputs），并返回特定的值给SUT。可以理解Test Stub是在SUT内部打的一个桩，可以按照我们的要求返回特定的内容给SUT，Test Stub的交互完全在SUT内部，因此，它不会返回内容给测试案例，也不会对SUT内部的输入进行验证。

* **Test Spy**

Test Spy像一个间谍，安插在了SUT内部，专门负责将SUT内部的间接输出（indirect outputs）传到外部。它的特点是将内部的间接输出返回给测试案例，由测试案例进行验证，Test Spy只负责获取内部情报，并把情报发出去，不负责验证情报的正确性。

* **Mock Object**

Mock Object和Test Spy有类似的地方，它也是安插在SUT内部，获取到SUT内部的间接输出（indirect outputs），不同的是，Mock Object还负责对情报（intelligence）进行验证，总部（外部的测试案例）信任Mock Object的验证结果。

* **Fake Object**

经常，我们会把Fake Object和Test Stub搞混，因为它们都和外部没有交互，对内部的输入输出也不进行验证。不同的是，Fake Object并不关注SUT内部的间接输入（indirect inputs）或间接输出（indirect outputs），它仅仅是用来替代一个实际的对象，并且拥有几乎和实际对象一样的功能，保证SUT能够正常工作。实际对象过分依赖外部环境，Fake Object可以减少这样的依赖。

---
*看完Test Double这几个概念后，是不是一头雾水？以下通俗解释，Dummy Objects就不做解释了。*

* Test Stub

系统测试需要某一指定数据返回时，开发将获取数据逻辑代码替换成指定数据，发包测试完再替换回原来逻辑。替换代码返回指定数据，这就是测试桩。

* Test Spy

Test Stub只返回指定内容给SUT，并没有指定返回测试案例，所以我们引入单元测试，在单元测试用例调用引用该插桩的方法。这时我们能获测试桩间接输出内容，甚至是报错信息，再也不用到服务器查找错误日志了，这就是Test Spy。

* Mock Object

Mock Object就是在Test Spy的基础上，加入验证机制。调用引用该插桩的方法，我们要确保这个插桩正常被执行或指定执行n次，得到的结果是不是我们期望的结果，mock就以此为生。

* Fake Object

Fake Object相对Test Stub，是一个面向对象概念。我们只希望替换掉一个实际被引用对象里面的一个方法返回值，被替换某个方法返回值的对象就叫Fake Oject，它与实际对象一样的功能。Mock Object也囊括Fake Object概念，可以看出Test Stub < Fake Object < Mock Object。

Mock不是真实的对象，它只是用类型的class创建了一个虚拟对象，并可以设置对象行为；Spy是一个真实的对象，但它可以设置对象行为；InjectMocks创建这个类的对象并自动将标记@Mock、@Spy等注解的属性值注入到这个中。

## mock工具的对比

***JMock***

优点：通过mock对象来模拟一个对象的行为，从而隔离开我们不关心的其他对象，使得测试变得简单

缺点：在执行前记录期望行为，显得很繁琐

***Mockito***

优点：Mockito通过在执行后校验哪些函数已经被调用，消除了对期望行为的需要，API非常简洁。

缺点：对于静态函数、构造函数、私有函数等还是无能为力。

***PowerMock***

优点：在Mockito的基础上做出的扩展，实现了对静态方法、构造方法、私有方法以及 Final 方法的模拟支持，对静态初始化过程的移除等强大的功能。

缺点：PowerMock编写的测试程序不能被统计进覆盖率。

## mockito的使用方法

* 依赖：
```
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-all</artifactId>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </dependency>
```
* 一些简单的功能说明
```
    /*****************************1.验证行为是否发生**************************/
    @Test
    public void verify_behaviour(){
        //模拟创建一个List对象
        List mock = mock(List.class);
        //使用mock的对象
        mock.add(1);
        mock.clear();
        //验证add(1)和clear()行为是否发生
        verify(mock).add(1);
        verify(mock).clear();
    }


    /*****************************1.验证多次返回*****************************/
    @Test
    public void when_thenReturn(){
        //mock一个Iterator类
        Iterator iterator = mock(Iterator.class);
        //预设当iterator调用next()时第一次返回hello，第n次都返回world
        when(iterator.next()).thenReturn("hello").thenReturn("world");
        //        //使用mock的对象
        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
        //验证结果
        assertEquals("hello world world",result);
    }

    /*****************************1.验证异常行为抛出**************************/
    @Test(expected = IOException.class)
    public void when_thenThrow() throws IOException {
        OutputStream outputStream = mock(OutputStream.class);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        //预设当流关闭时抛出异常
        doThrow(new IOException()).when(outputStream).close();
        outputStream.close();
    }

    /*****************************1.验证不同参数返回**************************/
    @Test
    public void with_arguments(){
        Comparable comparable = mock(Comparable.class);
        //预设根据不同的参数返回不同的结果
        when(comparable.compareTo("Test")).thenReturn(1);
        when(comparable.compareTo("Omg")).thenReturn(2);
        assertEquals(1, comparable.compareTo("Test"));
        assertEquals(2, comparable.compareTo("Omg"));
        //对于没有预设的情况会返回默认值
        assertEquals(0, comparable.compareTo("Not stub"));
    }
```
都是given when then的三段式语法，简单易懂。

## 关于spring上下文解耦
在本例中，简单搭建了一个springboot+mybatis的框架，如果我们想测试service，按照常规的思路。我们在编写单元测试时需要启动spring容器，@Autowired被测试的service，当我们启动测试时会发现这个是真的走的dao的代码逻辑，如果是真实业务代码，那这就去读数据库了。这个流程虽然也能跑，但是牵扯的东西太多，还要保证UserDao能正确注入，运行；还要加载一堆spring/server的东西，耗时耗力。

当我们使用mockito之后，主要有以下几点变化：

1. @RunWith(SpringRunner.class)，@SpringBootTest这两个注解去掉，整个Test清除了Spring Test依赖，可以避免加载额外的东西；

2. Autowire 改成如下：
```
    @InjectMocks
    private UserService userService= new UserServiceImpl(); 
```
不再Autowire，而是InjetMocks，并且要自己new出Service对象。

3. 添加Mock Dao的代码
```
    @Mock                       
    private UserDao userDao; 
``` 
表示这个对象是需要Mock的。

4. 初始化Mockito，编写Mock逻辑
重点在when()这个方法里，when函数以需要mock的方法作为参数，any表示任何输入，thenReturn设置返回的值,避免执行UserDao的真实代码。

## 一个坑

假定类MyService有一个属性 MyRepository myRepository：
```   
        @Repository
        public class MyRepository {
         
            public void doSomething() {
                System.out.println("here's dosomething");
            }
         
            public Model findById(Long id) {
                return new Model(id, "Real Repository");
            }
        }

        @Service
        public class MyService {
         
            @Autowired
            private MyRepository myRepository;
         
            public void doSomething() {
                this.myRepository.doSomething();
            }
         
            public Model findById(Long id) {
                return this.myRepository.findById(id);
            }
        }
```
需要构造MyService实例时Mock内部状态：
```
        MyRepository myRepository = Mockito.mock(MyRepository.class); 
        MyService myService = new MyService(myRepository);
```
如果所有的Mock对象全部通过手工来创建，那就不容易体现出Mockito的优越性出来。因此对于被测试对象的创建，Mock 属性的注入应该让 @Mock 和 @InjectMocks这两个注解大显身手了。

* @Mock：创建一个Mock。
* @InjectMocks：创建一个实例，其余用@Mock（或@Spy)注解创建的mock将被注入到用该实例中。

@Autowird等方式完成自动注入。在单元测试中，没有启动spring框架，此时就需要通过 @InjectMocks完成依赖注入。@InjectMocks会将带有@Spy和@Mock注解的对象尝试注入到被 测试的目标类中。

所以我们可以得出如下代码：
```
        @RunWith(MockitoJUnitRunner.class)
        public class UserServiceTest {
         
            @Mock
            private MyRepository myRepository;
         
            @InjectMocks
            private MyService myService;
         
            @Test
            public void testInjectMocks() {
                System.out.println(myService.getMyRepository().getClass());
            }
        }
```
MyService 被标记了 @InjectMocks，在setUp方法中 执行MockitoAnnotations.initMocks(this); 的时候，会将标记了@Mock或@Spy的属性注入到service中。MyService里面的MyRepository完全被Mock实例替换，所有的调用都是针对Mock生成类的。如果我们还有一个MyController如下，需要注入MyService应该怎么解决呢？
```        
        @Controller
        public class MyController {
         
            @Autowired
            private MyService myService;
         
            public void doSomething() {
                this.myService.doSomething();
            }
         
            public Model findById(Long id) {
                return this.myService.findById(id);
            }
        }
```
如果我用如下的写法：
```
        @RunWith(MockitoJUnitRunner.class)
        public class MyControllerTest {
         
            @Mock
            private MyRepository myRepository;
         
            @InjectMocks
            private MyService myService;
         
            @InjectMocks
            private MyController myController;
         
            @Before
            public void setUp() throws Exception {
                Model model = new Model(11L, "AAA");
                doNothing().when(myRepository).doSomething();
                when(myRepository.findById(11L)).thenReturn(model);
            }
         
            @Test
            public void doSomething() throws Exception {
                this.myController.doSomething();
            }
         
            @Test
            public void findById() throws Exception {
                System.out.println(this.myController.findById(11L));
            }
        }
```
使用Mock打桩的为MyRepository，原本以为使用InjectMocks后，MyService会自动注入MyRepository，MyController会自动注入前的MyService，但是结果并不是这样的。MyController无法识别MyService。MyController实例后，没有给myService属性赋值。于是想在MyService上加个@Mock，虽然编译没问题，但是运行起来异常了：org.mockito.exceptions.base.MockitoException: This combination of annotations is not permitted on a single field:@Mock and @InjectMocks。所以InjectMocks字段是无法注入其他InjectMocks字段的。所以我们可以考虑使用Spring来做容器管理，修改Test类：
```
        @RunWith(SpringJUnit4ClassRunner.class)
        @ContextConfiguration(locations = {"classpath:beans.xml"})
        public class MyControllerTest {
         
            @Mock
            private MyRepository myRepository;
         
            @InjectMocks
            @Autowired
            private MyService myService;
         
            @Autowired
            private MyController myController;
         
            @Before
            public void setUp() throws Exception {
                MockitoAnnotations.initMocks(this);
                Model model = new Model(11L, "AAA");
                doNothing().when(myRepository).doSomething();
                when(myRepository.findById(11L)).thenReturn(model);
            }
         
            @Test
            public void doSomething() throws Exception {
                this.myController.doSomething();
            }
         
            @Test
            public void findById() throws Exception {
                System.out.println(this.myController.findById(11L));
            }
        }
```
其实不借助容器，也可以手动来赋值。在setup方法中做下修改：
```
        @RunWith(MockitoJUnitRunner.class)
        public class MyControllerTest {
         
            @Mock
            private MyRepository myRepository;
         
            @InjectMocks
            private MyService myService;
         
            @InjectMocks
            private MyController myController;
         
            @Before
            public void setUp() throws Exception {
                //通过ReflectionTestUtils注入需要的非public字段数据
                ReflectionTestUtils.setField(myController, "myService", myService);
                Model model = new Model(11L, "AAA");
                doNothing().when(myRepository).doSomething();
                when(myRepository.findById(11L)).thenReturn(model);
            }
         
            @Test
            public void doSomething() throws Exception {
                this.myController.doSomething();
            }
         
            @Test
            public void findById() throws Exception {
                System.out.println(this.myController.findById(11L));
            }
        }
```
