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

[comparison benchmark](https://github.com/nort3x/Quanta/blob/main/Benchmark.txt)

![comparison-chart](https://github.com/nort3x/Quanta/blob/main/chart.png "comparison chart")



## Usage
```java
// Auto Object Map:

//initializer is a reflective ObjectGraph so its costy but the result is threadsafe and fast
BasicSerializer<TestOBJ> serializer = new BasicSerializer<>(TestOBJ.class); // initialize it 

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


## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
Please make sure to update Tests and Docs as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)