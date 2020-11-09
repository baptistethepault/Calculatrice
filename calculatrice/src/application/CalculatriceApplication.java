package application;

import controller.CalculatriceController;
import model.CalculatriceModel;
import view.CalculatriceView;

public class CalculatriceApplication {

	public static void main(String[] args) {
		// Instanciation du modèle de la calculatrice
		CalculatriceModel model = new CalculatriceModel();
		// Instanciation de la vue de la calculatrice
		CalculatriceView view = new CalculatriceView();
		// Instanciation du controleur de la calculatrice
		@SuppressWarnings("unused")
		CalculatriceController controller = new CalculatriceController(model, view);
	}

}
