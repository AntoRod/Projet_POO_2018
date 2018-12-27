package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.lang.reflect.Field;
import javax.swing.*;

import datas.Vcalendar;
import datas.Vcard;
import file_management.*;

public class Graphical_Interface extends JFrame{

	private static final long serialVersionUID = 2273727847380004349L;
	
	//Les containers/Panels par défaut
	private Container interface_Container;
	private JPanel interface_Panel;
	private GridBagLayout interface_Layout;
	private GridBagConstraints interface_Constraints;
	
	//Le menu (JMenuBar) de l'interface
	JMenuBar menuBar;
	JMenu file_Menu;
	JMenu help_Menu;
	
	JMenuItem openFile;
	JMenuItem closeFile;
	JMenuItem saveFile;
	JMenuItem saveAsFile;
	JMenuItem exit;

	
	
	
	Vcard_Management vcard_management;
	Vcalendar_Management vcalendar_management;
	
	public Graphical_Interface() {
		this("Graphical Interface");
	}
	public Graphical_Interface(String name) {
		super(name);
		
		initComponents();
		initMenu();
		addMenu();
		addComponents();
		
		
		
		/*
		 JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		JCheckBoxMenuItem cbMenuItem;
		

		initMapComponents();
		initMapPanel();
		addMapPanel();
		initSideMap();
		
		
		menuBar = new JMenuBar();
		menu = new JMenu("test menu");
		menu.getAccessibleContext().setAccessibleDescription("description");
		menuBar.add(menu);
		menuItem = new JMenuItem("A text-only menu item",KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menu.add(menuItem);
		
		this.setJMenuBar(menuBar);
		 * */
		
		
		this.pack();
	}
	
	private void initMenu() {
		menuBar = new JMenuBar();
		file_Menu = new JMenu("Fichier");
		help_Menu = new JMenu("Aide");
	}
	private void addMenu() {
		/*PARTIE MENU FILE*/
		openFile = new JMenuItem("Ouvrir un Fichier");
		closeFile = new JMenuItem("Fermer");
		saveFile = new JMenuItem("Enregistrer");
		saveAsFile = new JMenuItem("Enregistrer sous...");
		
		openFile.addActionListener(new Open_File_Action());
		
		
		exit = new JMenuItem("Quitter (Echap)");
		exit.addActionListener(new Exit_Action());
		exit.setToolTipText("Appuyez sur ECHAP");
		
		file_Menu.add(openFile);
		file_Menu.add(closeFile);
		file_Menu.add(saveFile);
		file_Menu.add(saveAsFile);
		file_Menu.add(exit);
		
		/*FIN PARTIE MENU FILE*/
		/*PARTIE MENU HELP*/
		
		
		
		
		/*FIN PARTIE MENU HELP*/
		menuBar.add(file_Menu);
		menuBar.add(help_Menu);
		interface_Panel.setFocusable(true);
		interface_Panel.requestFocus();
		this.setJMenuBar(menuBar);
	}
	
	private void initComponents() {
		//Les attributs par défaut du panel
		interface_Container = this.getContentPane();
		interface_Panel = new JPanel();
		interface_Layout = new GridBagLayout();
		interface_Constraints = new GridBagConstraints();
		interface_Panel.setLayout(interface_Layout);
		interface_Panel.setPreferredSize(new Dimension(700,500));
		
	}
	
	private void addComponents() {
		
		
		
		
		interface_Panel.addKeyListener(new Simple_Key_Shortcut());
		interface_Container.add(interface_Panel);
	}
	
	//Ajustement de la fenetre (pack, visible, close operation etc...)
	public void pack() {
		//Permet de pack tous les éléments ensemble
		super.pack();
		//Permet de rendre visible/invisible l'interface
		this.setVisible(true);
		//Défini l'opération de fermeture par défaut
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Permet de définir si la taille de l'interface peut être modifiée
		this.setResizable(false);
		//Permet de définir la position relative par rapport à une autre fenetre
		this.setLocationRelativeTo(null);
	}
	
	
	
	
	public void initVCFPanel(String path) {
		//Ici, la Vcard est créée, qu'elle soit sérialisée ou non à la base (dans le constructeur)
		vcard_management = new Vcard_Management(path);
		Vcard vcard = vcard_management.get_vcard();
		//On prend tout les attributs de la class pou générer automatiquement l'interface en fonction des attributs non vides
		Field[] declaredFields = vcard.getClass().getDeclaredFields();
		//On boucle les fields pour ensuite pouvoir créer les champs automatiquement
		int compteur = 0;
		 for (Field field : declaredFields){
			 //On rend le champ accessible (OBLIGATOIRE !!)
             field.setAccessible(true);
             try {
            	 //Si le field est non vide (on y accède graçe au setAccessible)
				if(field.get(vcard) != null && field.getName() != "serialVersionUID") {
					//On génère le champs classique lié à celui-ci
//					System.out.println(field.get(vcard));
					
					//Inset (écart entre les composants
					Insets insets = new Insets(5,5,5,5);
					//La police (surtout pour la taille de l'écriture)
					Font font = new Font("Classic", 1, 15);
					//Les label + le textfield à générer
					String labelString = Settings.getVcardMap().get(field.getName());
					JLabel fieldLabel = new JLabel(labelString);
					fieldLabel.setFont(font);
					JLabel twoDots = new JLabel(" : ");
					twoDots.setFont(font);
					JTextField fieldText = new JTextField(field.get(vcard).toString());
					fieldText.setPreferredSize(new Dimension(400,20));
					fieldText.setFont(font);
					//Les contraintes
					interface_Constraints.insets = insets;
					interface_Constraints.gridy = compteur;
					interface_Constraints.gridx = 0;
					compteur++;
					//On ajoute les composants
					interface_Panel.add(fieldLabel, interface_Constraints);
					interface_Constraints.gridx = 1;
					interface_Panel.add(twoDots, interface_Constraints);
					interface_Constraints.gridx = 2;
					interface_Panel.add(fieldText, interface_Constraints);
				 }
			} catch (IllegalArgumentException e) {e.printStackTrace();} 
             catch (IllegalAccessException e) {e.printStackTrace();}
		 }
		 //On repack le tout pour update les ajouts
		 this.pack();

	}
	
	
	public void initICSPanel(String path) {
		//Ici, le Vcalendar est créé, qu'il soit sérialisé ou non à la base (dans le constructeur)
		vcalendar_management = new Vcalendar_Management(path);
		Vcalendar vcalendar = vcalendar_management.get_vcalendar();
		//On prend tout les attributs de la class pou générer automatiquement l'interface en fonction des attributs non vides
		Field[] declaredFields = vcalendar.getClass().getDeclaredFields();
		//On boucle les fields pour ensuite pouvoir créer les champs automatiquement
		int compteur = 0;
		 for (Field field : declaredFields){
			 //On rend le champ accessible (OBLIGATOIRE !!)
             field.setAccessible(true);
             try {
            	 //Si le field est non vide (on y accède graçe au setAccessible)
				if(field.get(vcalendar) != null && field.getName() != "serialVersionUID") {
					//On génère le champs classique lié à celui-ci
//					System.out.println(field.get(vcalendar));
					
					//Inset (écart entre les composants
					Insets insets = new Insets(5,5,5,5);
					//La police (surtout pour la taille de l'écriture)
					Font font = new Font("Classic", 1, 15);
					//Les label + le textfield à générer
					String labelString = Settings.getVcalendarMap().get(field.getName());
					System.out.println(labelString);
					JLabel fieldLabel = new JLabel(labelString);
					fieldLabel.setFont(font);
					JLabel twoDots = new JLabel(" : ");
					twoDots.setFont(font);
					JTextField fieldText = new JTextField(field.get(vcalendar).toString());
					fieldText.setPreferredSize(new Dimension(400,20));
					fieldText.setFont(font);
					//Les contraintes
					interface_Constraints.insets = insets;
					interface_Constraints.gridy = compteur;
					interface_Constraints.gridx = 0;
					compteur++;
					//On ajoute les composants
					interface_Panel.add(fieldLabel, interface_Constraints);
					interface_Constraints.gridx = 1;
					interface_Panel.add(twoDots, interface_Constraints);
					interface_Constraints.gridx = 2;
					interface_Panel.add(fieldText, interface_Constraints);
				 }
			} catch (IllegalArgumentException e) {e.printStackTrace();} 
             catch (IllegalAccessException e) {e.printStackTrace();}
		 }
		 //On repack le tout pour update les ajouts
		 this.pack();

	}
	/*CLASSES*/
	
	
	//Action qui permet d'ouvrir un fichier dans la fenêtre
	class Open_File_Action implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			
			//On crée un JFile Chooser qui commence à la racinde du .jar
			JFileChooser fileChooser = new JFileChooser("./");
			//On le lie à un JTextField invisible qui nous sert de String
			JTextField ghostField = new JTextField();
			//On affiche la fenêtre de séléction de fichier
			fileChooser.showOpenDialog(ghostField);
			//On récupère le nom du fichier dans un String
			String fileName = null;
			try {
				fileName = fileChooser.getSelectedFile().getCanonicalPath();
			} catch (IOException e) {e.printStackTrace();}
			//Si le fichier est sérialisé, on doit l'analyser avant de créer l'interface
			if("ser".equals(Directory_Management.getFileExtension(fileName))) {
				//On doit détecter la class du fichier
				Object object = Directory_Management.readSerialisedObject(fileName);
				//Si le fichier désérailisé est de type Vcard, on le traite en Vcard
				if(object.getClass().equals(vcard_management.get_vcard().getClass())) {
					initVCFPanel(fileName);
				}
				else if(object.getClass().equals(vcalendar_management.get_vcalendar().getClass())) {
					initICSPanel(fileName);
				}
			}
			//Si le fichier est en VCF, on crée l'interface VCF
			if("vcf".equals(Directory_Management.getFileExtension(fileName))) {
				initVCFPanel(fileName);
			}
			else if ("ics".equals(Directory_Management.getFileExtension(fileName))) {
				initICSPanel(fileName);
			}

			
		}
	}
	
	//Action qui permet de quitter le logiciel
	class Exit_Action implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}
	
	//Raccourcis à une touche du panel
	class Simple_Key_Shortcut implements KeyListener {

	
		public void keyPressed(KeyEvent arg0) {
			//On appuie sur Control
			if(arg0.isControlDown()) {
				//Si on appui sur S, on prépare la sauvagerde
				if(arg0.getKeyCode() == KeyEvent.VK_S) {
					//Si on appui aussi sur Shift, on sauvegarde dans un nouveau fichier ?
					if(arg0.isShiftDown()) saveAsFile.doClick();
					//Sinon on sauvegarde par dessus le fichier actuel
					else saveFile.doClick();
				}
			}
			//NE RIEN FAIRE
		}
		public void keyReleased(KeyEvent arg0) {
			if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE) exit.doClick();
			else if(arg0.getKeyCode() == KeyEvent.VK_ENTER) saveFile.doClick();
			else if(arg0.getKeyCode() == KeyEvent.VK_F1) saveAsFile.doClick(); //ESSAYER AVEC CTRL ET TOUT

		}
		public void keyTyped(KeyEvent arg0) {
			//NE RIEN FAIRE
		}
		
	}

}
