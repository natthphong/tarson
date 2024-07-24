# tar_pain

# setup
```xml
<!-- https://mvnrepository.com/artifact/io.github.natthphong/tarson -->
<dependency>
    <groupId>io.github.natthphong</groupId>
    <artifactId>tarson</artifactId>
    <version>0.0.1</version>
</dependency>
```
```groovy
// https://mvnrepository.com/artifact/io.github.natthphong/tarson
implementation group: 'io.github.natthphong', name: 'tarson', version: '0.0.1'
```
# exmaple

```java
import org.springframework.beans.factory.annotation.Autowired;

@Autowired
private TarSonConverter tarsonConverter;

public void test(){
   String jsonStr  = tarsonConverter.objectToJsonString(null);
}
```