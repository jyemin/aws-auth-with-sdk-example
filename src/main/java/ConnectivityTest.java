import com.mongodb.client.MongoClients;
import org.bson.Document;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider;
import software.amazon.awssdk.utils.SdkAutoCloseable;

@SuppressWarnings({"CallToPrintStackTrace"})
public class ConnectivityTest {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage requires either a connection string or the simple name of a credential provider");
            System.out.println("Supported providers are: ");
            System.out.println("   SystemPropertyCredentialsProvider");
            System.out.println("   EnvironmentVariableCredentialsProvider");
            System.out.println("   WebIdentityTokenFileCredentialsProvider");
            System.out.println("   ProfileCredentialsProvider");
            System.out.println("   ContainerCredentialsProvider");
            System.out.println("   InstanceProfileCredentialsProvider");
            System.out.println("   DefaultCredentialProvider");
            return;
        }
        if (args[0].startsWith("mongodb")) {
            try (var client = MongoClients.create(args[0])) {
                var reply = client.getDatabase("admin").runCommand(new Document("hello", 1));
                System.out.println(reply.toJson());
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        } else {
            String simpleProviderClassName = args[0];
            System.out.println("Testing credential fetching from " + simpleProviderClassName);
            AwsCredentialsProvider provider = null;
            try {
                provider = getProvider(simpleProviderClassName);
                var credentials = provider.resolveCredentials();
                System.out.println(credentials);
            } catch (RuntimeException e) {
                e.printStackTrace();
            } finally {
                if (provider instanceof SdkAutoCloseable autoCloseable) {
                    autoCloseable.close();
                }
            }
        }
    }

    private static AwsCredentialsProvider getProvider(String simpleProviderClassName) {
        return switch (simpleProviderClassName) {
            case "SystemPropertyCredentialsProvider":
                yield SystemPropertyCredentialsProvider.create();
            case "EnvironmentVariableCredentialsProvider":
                yield EnvironmentVariableCredentialsProvider.create();
            case "WebIdentityTokenFileCredentialsProvider":
                yield WebIdentityTokenFileCredentialsProvider.builder().build();
            case "ProfileCredentialsProvider":
                yield ProfileCredentialsProvider.builder()
                        .profileFile(() -> null)
                        .profileName(null)
                        .build();
            case "ContainerCredentialsProvider":
                yield ContainerCredentialsProvider.builder().build();
            case "InstanceProfileCredentialsProvider":
                yield InstanceProfileCredentialsProvider.builder()
                        .profileFile(() -> null)
                        .profileName(null)
                        .build();
            case "DefaultCredentialProvider":
                yield DefaultCredentialsProvider.builder().build();
            default:
                throw new IllegalArgumentException("Unexpected value: " + simpleProviderClassName);
        };
    }
}
