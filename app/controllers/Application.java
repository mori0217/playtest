package controllers;

import org.apache.commons.lang.StringUtils;

import play.libs.Comet;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.Controller;
import play.mvc.Http.RequestBody;
import play.mvc.Result;
import play.mvc.WebSocket;

public class Application extends Controller {

	public static Result index() {
		String message = flash("succsss");
		if (message != null) {
			return ok(message);
		}
		return ok(views.html.index.render("Your new application is ready."));
		// return ok("test");
	}

	public static Result redirectTest() {
		return redirect(routes.Application.index());
	}

	public static Result hello(String name) {
		return ok("こんにちは " + name + "です" + "");
	}

	public static Result id(Integer id) {
		response().setHeader("hoge", "hoge");
		response().setContentType("text/html;charset=utf8");
		return ok("<h1>idは " + id + "です" + "</h1>");
	}

	public static Result notFoundTest() {
		return notFound("<h1>Page not found</h1>").as("text/html");
	}

	public static Result sessionTest() {
		String user = session("user");
		if (StringUtils.isNotEmpty(user)) {
			return ok(user);
		}
		return unauthorized("unauthorized");
	}

	public static Result sessionSet(String user) {
		session("user", user);
		return ok("set user " + user);
	}

	public static Result sessionDelete() {
		session().remove("user");
		return ok("delete user");
	}

	public static Result flashTest() {
		flash("succsss", "flash success");
		return redirect("/");
	}

	public static Result bodyTest() {
		RequestBody body = request().body();
		String bodyTest = body.asText();
		if ("test".equals(bodyTest)) {
			return ok("test");
		}
		return ok();
	}

	public static Result chunkTest() {
		// Prepare a chunked text stream
		Chunks<String> chunks = new StringChunks() {

			// Called when the stream is ready
			public void onReady(Chunks.Out<String> out) {
				out.write("<script>console.log('kiki')</script>");
				out.write("<script>console.log('foo')</script>");
				out.write("<script>console.log('bar')</script>");
				out.close();
			}
		};

		Comet comet = new Comet("alert") {
			public void onConnected() {
				sendMessage("kiki");
				sendMessage("foo");
				sendMessage("bar");
				close();
			}
		};

		response().setContentType("text/html");
		return ok(comet);
	}

	public static WebSocket<String> socketTest() {
		return new WebSocket<String>() {

			// WebSocket のハンドシェイクが完了すると呼ばれます。
			public void onReady(WebSocket.In<String> in,
					WebSocket.Out<String> out) {

				// ソケットで受け取ったイベント毎に
				in.onMessage(new Callback<String>() {
					public void invoke(String event) {

						// Log events to the console
						System.out.println(event);

					}
				});

				// ソケットが閉じた時
				in.onClose(new Callback0() {
					public void invoke() {

						System.out.println("Disconnected");

					}
				});

				// 単一の `Hello!` というメッセージを送る
				out.write("Hello!");

			}

		};
	}
}