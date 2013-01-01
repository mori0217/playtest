import org.junit.Test;

import play.api.libs.concurrent.Promise;
import play.api.libs.ws.Response;
import play.api.libs.ws.WS;

public class ApplicationTest {

	@Test
	public static void Test() {
		Promise<Response> homePage = WS.url("http://localhost:9000/").get();
		System.out.println(homePage.value());
	}
}
