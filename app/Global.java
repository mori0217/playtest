import play.GlobalSettings;
import play.Logger;
import controllers.Application;

public class Global extends GlobalSettings {

	public void onStart(Application app) {
		Logger.info("Application has started");
	}

	public void onStop(Application app) {
		Logger.info("Application shutdown...");
	}

}
