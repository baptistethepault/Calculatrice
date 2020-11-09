package controller;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import error.MyException;
import model.CalculatriceModel;
import view.CalculatriceView;

public class CalculatriceController {

	protected CalculatriceModel model;
	protected CalculatriceView view;
	
	public CalculatriceController(CalculatriceModel model, CalculatriceView view) {
		this.model = model;
		this.view = view;
		addActionListeners();
	}
	
	public void addActionListeners() {
		// Récupère le bouton reset et lui attribue une action
		view.getResetButton().addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(final ActionEvent e) {
		    	reset();
		    }
		});
		
		// Récupère le bouton compute et lui attribue une action
		view.getComputeButton().addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(final ActionEvent e) {
		    	compute();
		    }
		});
		
		// Création d'un listener de key AWT pour catch les events keyboard ENTER et DELETE sur toute la GUI
		// Meilleure solution que d'ajouter un ActionListener au JTextField input (qui fonctionne comme un bouton)
		// ou un KeyListener à la view et faire un requestFocus() dessus pour capter les events...
		AWTEventListener awtEventListener = new AWTEventListener() {
		    @Override
		    public void eventDispatched(final AWTEvent event) {
				if (event instanceof KeyEvent) {
				    final KeyEvent ke = (KeyEvent) event;
				    if (ke.getID() == KeyEvent.KEY_PRESSED) {
						switch (ke.getKeyCode()) {
							// Appui sur ENTER
							case KeyEvent.VK_ENTER:
								compute();
							    break;
							// Appui sur DELETE
							case KeyEvent.VK_DELETE:
								reset();
							    break;
							default:
							    break;
						}
				    } 
				}
		    }
		};
		
		// Ajout du AWTEventListener au toolkit
		Toolkit.getDefaultToolkit().addAWTEventListener(awtEventListener,
				AWTEvent.KEY_EVENT_MASK);
	}

	// Nettoie l'input et l'output de la vue
	public void reset() {
		view.reset();
	}

	// Calcule l'opération en input et l'affiche dans l'output
	public void compute() {
		String result;
		try {
			result = model.compute(view.getInputText());
			view.displayResult(result);
		} catch (MyException e) {
			view.displayResult(e.getMyMessage());
		}
	}

}
