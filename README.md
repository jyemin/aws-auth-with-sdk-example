This project contains a basic connectivity test to a MongoDB cluster. It includes a dependency on the AWS SDK v2 
for Java, which the driver detects on the classpath at runtime and delegates the task of fetching the AWS credentials.

Run from source:

```
./gradlew run --args "<connection string>"
```

Create distribution:

```
./gradlew distZip
```

Run from distribution:

```
./bin/aws-auth-with-sdk-example "<connection string>"
```