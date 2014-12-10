package controllers;

import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	public static Result index() {
		Chunks<String> chunks = new StringChunks() {

			// Called when the stream is ready
			@Override
			public void onReady(Chunks.Out<String> out) {
				out.onDisconnected(new F.Callback0() {

					@Override
					public void invoke() throws Throwable {
						System.out.println("disconnect invoked");
					}
				});
				out.write("ok\n");
				System.out.println("ready");
				try {
					Thread.sleep(10000);
					out.write("10 seconds later\n");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		try {
			Thread.sleep(5000);
			System.out.println("5 seconds later");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ok(chunks);
	}


}
