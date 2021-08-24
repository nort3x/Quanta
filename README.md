[![Total alerts](https://img.shields.io/lgtm/alerts/g/nort3x/Quanta.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/nort3x/Quanta/alerts/)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/nort3x/Quanta.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/nort3x/Quanta/context:java)
[![](https://jitpack.io/v/nort3x/Quanta.svg)](https://jitpack.io/#nort3x/Quanta)
# Quanta

Quanta is a fast and efficient serializer


```
Gson average: 51797.09
MessagePack average: 7578.465
Quanta average: 3239.465
``` 
measured in nano seconds for a primitive POJO

if you are librarian you can make MessagePack (which is a wrapper over Jackson/Marshal/...) and Gson faster by providing more complex configuration magics but not Quanta!

main purpose of Quanta is being the head first solution for efficiency in practice and still reducing complexity as much as possible,

its also mentionable that Quanta is a standalone library, which makes it pretty small compared to other libraries

[comparison benchmark](https://github.com/nort3x/Quanta/blob/main/Benchmark.txt)

![comparison-chart](https://github.com/nort3x/Quanta/blob/main/chart.png "comparison chart")

## features
+ supports all primitives and their object wrappers automatically
+ supports all primitive arrays and their wrappers automatically
+ supports multidimensional arrays of anytype of any dimension
+ supports java collection framework (ArrayList,List,LinkedList,Queue,...)
+ branchless structure and smooth jit optimization
+ lower overhead compared to other serializers for mapping structure
+ caching schemas for faster further mappings (cleanable)
+ threadsafe 

## Integrate


### maven

Step 1. Add the JitPack repository to your build file
```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```
Step 2. Add the dependency
```xml
<dependency>
    <groupId>com.github.nort3x</groupId>
    <artifactId>Quanta</artifactId>
    <version>1.0.0</version>
</dependency>
```


### gradle

Step 1. Add the JitPack repository to your build file


Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
Step 2. Add the dependency
```gradle
dependencies {
        implementation 'com.github.nort3x:Quanta:1.0.0'
}
```

## Usage
```java
// Auto Object Map:

//initializer is a reflective ObjectGraph so its costy but the result is threadsafe and fast
NestedConverter<TestOBJ> serializer = new NestedConverter<>(TestOBJ.class); // initialize it 

//use it
byte[] arr = serializer.serialize(f); 
TestOBJ q = serializer.deserialize(arr);

```
```java
// Manual Pack:
Serializer s = new Serializer();

s.writeByte((byte) 1);
s.writeByteArray("Quanta".getBytes());
s.writeFloat64(1.2);
s.writeFloat32(1.2f);
s.writeBool(true);
s.writeInt32(20);
s.writeInt64(200);
s.writeStringArray("1,2,3,4".split(","));
s.writeIntArray(new int[]{1, 2, 3, 4});

s.writeObject(YourObject,YourConverter);  // just to introduce API
s.writeObjectArray(YourObject,YourConverter);

byte[] buff = s.toArray();
///////////////////

Deserializer d = new Deserializer(buff);

byte[] byteArr = d.readByteArray();
double aDouble = d.readFloat64();
....
```
## Roadmap
+ finding active co-ops
+ adding nested object map support
+ fine tuning algorithms
+ giving it structure
+ adding cross language support

## Donation
if you liked **Quanta** buy me a pack of ciggaretes [here](http://google.com), if you dont smoke yourself i dont expect anything

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
Please make sure to update Tests and Docs as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
