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
				System.out.println("ready");
				out.onDisconnected(new F.Callback0() {

					@Override
					public void invoke() throws Throwable {
						System.out.println("disconnect invoked");
					}
				});
				out.write("ok\n");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				out.write("10 seconds later\n");
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
