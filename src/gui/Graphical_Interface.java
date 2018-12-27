package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import javax.swing.*;

import datas.Vcalendar;
import datas.Vcard;
import exceptions.BadArgumentException;
import exceptions.NoArgumentException;
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
	//Les items du menu
	JMenuItem openFile;
	JMenuItem closeFile;
	JMenuItem saveFile;
	JMenuItem saveAsFile;
	JMenuItem exit;

	//Inset (écart entre les composants)
	Insets insets;
	//La police (surtout pour la taille de l'écriture)
	Font font;
	
	//Les class à traiter
	Vcard_Management vcard_management;
	Vcalendar_Management vcalendar_management;
	//On garde en mémoire le fichier qu'on a importé (plus facile)
	File importedFile;
	
	
	public Graphical_Interface() {
		this("Graphical Interface");
	}
	public Graphical_Interface(String name) {
		super(name);
		
		initComponents();
		initMenu();
		addMenu();
		addComponents();
		
		this.pack();
	}
	
	private void initMenu() {
		menuBar = new JMenuBar();
		file_Menu = new JMenu("Fichier");
	}
	private void addMenu() {
		/*PARTIE MENU FILE*/
		openFile = new JMenuItem("Ouvrir un Fichier");
		closeFile = new JMenuItem("Fermer");
		saveFile = new JMenuItem("Enregistrer");
		saveAsFile = new JMenuItem("Enregistrer sous...");
		
		openFile.addActionListener(new Open_File_Action());
		closeFile.addActionListener(new Close_Action());
		saveFile.addActionListener(new Save_Action());
		saveAsFile.addActionListener(new SaveAs_Action());
		
		exit = new JMenuItem("Quitter (Echap)");
		exit.addActionListener(new Exit_Action());
		exit.setToolTipText("Appuyez sur ECHAP");
		
		file_Menu.add(openFile);
		file_Menu.add(closeFile);
		file_Menu.add(saveFile);
		file_Menu.add(saveAsFile);
		file_Menu.add(exit);
		file_Menu.setFont(font);
		/*FIN PARTIE MENU FILE*/

		
		menuBar.add(file_Menu);
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
		
		//Inset (écart entre les composants)
		insets = new Insets(5,5,5,5);
		//La police (surtout pour la taille de l'écriture)
		font = new Font("Classic", 0, 15);
		interface_Constraints.insets = insets;
		
	}
	private void addComponents() {
		interface_Panel.addKeyListener(new Key_Shortcut());
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
					//Les label + le textfield à générer
					String labelString = Settings.getVcardMap().get(field.getName());
					JLabel fieldLabel = new JLabel(labelString);
					fieldLabel.setName(labelString);
					fieldLabel.setFont(font);
					JLabel twoDots = new JLabel(" : ");
					twoDots.setFont(font);
					JTextField fieldText = new JTextField(field.get(vcard).toString());
					fieldText.setPreferredSize(new Dimension(400,20));
					fieldText.setFont(font);
					compteur++;
					interface_Constraints.gridx = 0;
					interface_Constraints.gridy = compteur;
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
					
					//Les label + le textfield à générer
					String labelString = Settings.getVcalendarMap().get(field.getName());
					JLabel fieldLabel = new JLabel(labelString);
					fieldLabel.setName(labelString);
					fieldLabel.setFont(font);
					JLabel twoDots = new JLabel(" : ");
					twoDots.setFont(font);
					JTextField fieldText = new JTextField(field.get(vcalendar).toString());
					fieldText.setPreferredSize(new Dimension(400,20));
					fieldText.setFont(font);
					compteur++;
					interface_Constraints.gridx = 0;
					interface_Constraints.gridy = compteur;
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
			interface_Panel.removeAll();
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
				if(object.getClass().equals(new Vcard().getClass())) {
					initVCFPanel(fileName);
				}
				else if(object.getClass().equals(new Vcalendar().getClass())) {
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
			//On sauvegarde le fichier importé en mémoire
			importedFile = new File(fileName);
			
		}
	}
	
	//Bouton qui permet de sauvegarder un fichier
	//compliqué ><
	class Save_Action implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//2 etapes:
			//Analyser le fichier en entrée, pour soit le convertir en .ser, soir juste le modifier
			//Récupérer les text des JTextFields pour les mettre à la place des infos de la Vcard
			//Problème: Fields générés automatiquement, pas de simple remplacement, obligé d'analyser le tout
			if(importedFile != null) {
				try {
					String type = null;
					//PARTIE SAUVEGARDE
					String filePath = importedFile.getCanonicalPath();
					if(importedFile.getName().endsWith(".ser")) {
					//On detecte son type de class, puis on le serialise à nouveau
					Object object = Directory_Management.readSerialisedObject(filePath);
						//Si l'objet est de type Vcard, on le serialise en Vcard
						if(object.getClass().equals(new Vcard().getClass())) {
							filePath = filePath.replace(".vcf", ".ser");
							type = "vcf";
						}
						//Si il est de type Vcalendar, on le serialise en Vcalendar
						else if(object.getClass().equals(new Vcalendar().getClass())) {
							filePath = filePath.replace(".ics", ".ser");
							type = "ics";
						}
					}	
					//Si c'est un fichier VCF, on le serialise simplement
					if(importedFile.getName().endsWith(".vcf")) {
						filePath = filePath.replace(".vcf", ".ser");
						type = "vcf";
					}

					//Pareil si c'est un fichier ICS
					if(importedFile.getName().endsWith(".ics")) {
						filePath = filePath.replace(".ics", ".ser");
						type = "ics";
					}
					Field[] declaredFields = null;
					//une fois qu'on a le type de fichier, on va pouvoir faire comme il faut
					if(type == "vcf") {
						declaredFields = new Vcard().getClass().getDeclaredFields();
					}
					else if(type == "ics") {
						declaredFields = new Vcalendar().getClass().getDeclaredFields();
					}

					if(declaredFields != null) {
						 for (Field field : declaredFields){
							 field.setAccessible(true);
							 if(type == "vcf") {
									for(int i=0;i<interface_Panel.getComponentCount()-1;i++) {
										//Si le nom du label est différend de null, on l'analyse
										if(interface_Panel.getComponent(i).getName() != null) {
											JLabel labelField = (JLabel) interface_Panel.getComponent(i);
											JTextField newField = (JTextField) interface_Panel.getComponent(i+2);
											if(labelField.getName().equals(Settings.getVcardMap().get("_name"))) {
												vcard_management.get_vcard().set_name(newField.getText());
											}
											if(labelField.getName().equals(Settings.getVcardMap().get("_firstName"))) {
												vcard_management.get_vcard().set_firstName(newField.getText());
											}
											if(labelField.getName().equals(Settings.getVcardMap().get("_homePhone"))) {
												vcard_management.get_vcard().set_homePhone(newField.getText());
											}
											if(labelField.getName().equals(Settings.getVcardMap().get("_mail"))) {
												vcard_management.get_vcard().set_mail(newField.getText());
											}
											if(labelField.getName().equals(Settings.getVcardMap().get("_homeAdress"))) {
												vcard_management.get_vcard().set_homeAdress(newField.getText());
											}
											if(labelField.getName().equals(Settings.getVcardMap().get("_company"))) {
												vcard_management.get_vcard().set_company(newField.getText());
											}
											if(labelField.getName().equals(Settings.getVcardMap().get("_workPhone"))) {
												vcard_management.get_vcard().set_workPhone(newField.getText());
											}
											if(labelField.getName().equals(Settings.getVcardMap().get("_workAdress"))) {
												vcard_management.get_vcard().set_workAdress(newField.getText());
											}
											if(labelField.getName().equals(Settings.getVcardMap().get("_title"))) {
												vcard_management.get_vcard().set_title(newField.getText());
											}
											if(labelField.getName().equals(Settings.getVcardMap().get("_picture"))) {
												vcard_management.get_vcard().set_picture(newField.getText());
											}
										}
										//Vcard modifiée
//										System.out.println(vcard_management.get_vcard());
									}
									//Sortie de boucle FINALE
									vcard_management.serializeVcard(filePath);
							 }
							 else if(type == "ics") {
								 for(int i=0;i<interface_Panel.getComponentCount()-1;i++) {
										//Si le nom du label est différend de null, on l'analyse
										if(interface_Panel.getComponent(i).getName() != null) {
											JLabel labelField = (JLabel) interface_Panel.getComponent(i);
											JTextField newField = (JTextField) interface_Panel.getComponent(i+2);
											if(labelField.getName().equals(Settings.getVcardMap().get("_dateBegin"))) {
												vcalendar_management.get_vcalendar().set_dateBegin(newField.getText());
											}
											if(labelField.getName().equals(Settings.getVcardMap().get("_dateEnd"))) {
												vcalendar_management.get_vcalendar().set_dateEnd(newField.getText());
											}
											if(labelField.getName().equals(Settings.getVcardMap().get("_summary"))) {
												vcalendar_management.get_vcalendar().set_summary(newField.getText());
											}
											if(labelField.getName().equals(Settings.getVcardMap().get("_categories"))) {
												vcalendar_management.get_vcalendar().set_categories(newField.getText());
											}
											if(labelField.getName().equals(Settings.getVcardMap().get("_transparency"))) {
												vcalendar_management.get_vcalendar().set_transparency(newField.getText());
											}
											if(labelField.getName().equals(Settings.getVcardMap().get("_location"))) {
												vcalendar_management.get_vcalendar().set_location(newField.getText());
											}
											if(labelField.getName().equals(Settings.getVcardMap().get("_description"))) {
												vcalendar_management.get_vcalendar().set_description(newField.getText());
											}
										}
										//Vcard modifiée
//										System.out.println(vcard_management.get_vcard());
									}
								 //Sortie FINALE de la boucle
								 vcalendar_management.serializeVcalendar(filePath);
							 }
						 }
					}

					
					
					
					
				} catch (NoArgumentException e) {e.printStackTrace();}
					catch (BadArgumentException e) {e.printStackTrace();}
						catch (IOException e) {e.printStackTrace();} 
							catch (IllegalArgumentException e) {e.printStackTrace();} 
							//	catch (IllegalAccessException e) {e.printStackTrace();}

				
				/* PARTIE ABANDONNEE, on ne save qu'en serialisant
				else {
					//On ouvre le fichier, on remplace les textes et on le referme
					try {
						BufferedReader bufferedReader = new BufferedReader(new FileReader(importedFile));
						String readLine = bufferedReader.readLine();
						while(readLine != null) {
							
							//On doit savoir si la ligne contient ou non ce qui est écrit dans 
							System.out.println(readLine);
							//on énumère les composants pour trouver les JTextFields (vue qu'on a pas leur nom, car générés automatiquement)
							for(int i=0;i<interface_Panel.getComponentCount();i++) {
								//Si c'est un JTextField, alors on traite l'information
								if(interface_Panel.getComponent(i).getClass().equals(new JTextField().getClass())) {
									//On crée un JTextField temporaire
									JTextField field = (JTextField) interface_Panel.getComponent(i);
									System.out.println(field.getText());
								}
							}
							readLine = bufferedReader.readLine();
						}
					} catch (FileNotFoundException e) {e.printStackTrace();} 
					catch (IOException e) {e.printStackTrace();}
				}
				*/
			}

		}
	}
	
	//Méthode qui permet de sauvegarder un fichier dans un dossier spécifique
	class SaveAs_Action implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fileChooser = new JFileChooser("./");
			//On le lie à un JTextField invisible qui nous sert de String
			JTextField ghostField = new JTextField();
			//On affiche la fenêtre de séléction de fichier
			fileChooser.showOpenDialog(ghostField);
			//On récupère le nom du fichier dans un String
			try {
				String fileName = fileChooser.getSelectedFile().getCanonicalPath();
				if(fileName.endsWith(".ser")) {
					if(importedFile.getName().endsWith(".vcf")) {
						vcard_management.serializeVcard(fileName);
					}
					else if(importedFile.getName().endsWith(".ics")) {
						vcalendar_management.serializeVcalendar(fileName);
					}
					else if(importedFile.getName().endsWith(".ser")) {
						Object object = Directory_Management.readSerialisedObject(importedFile.getCanonicalPath());
						if(object.getClass().equals(new Vcard().getClass())) {
							vcard_management.serializeVcard(fileName);
						}
						else if(object.getClass().equals(new Vcalendar().getClass())) {
							vcalendar_management.serializeVcalendar(fileName);
						}
					}
				}
				else if(fileName.endsWith(".html")) {
					BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
					String HTMLPage = null;
					if(importedFile.getName().endsWith(".vcf")) {
						HTMLPage = vcard_management.convertVcardToHTMLPage();
					}
					else if(importedFile.getName().endsWith(".ics")) {
						HTMLPage = vcalendar_management.convertVcalendarToHTMLPage();
					}
					else if(importedFile.getName().endsWith(".ser")) {
						Object object = Directory_Management.readSerialisedObject(importedFile.getCanonicalPath());
						if(object.getClass().equals(new Vcard().getClass())) {
							vcard_management.readSerializedVcard(fileName);
							HTMLPage = vcard_management.convertVcardToHTMLPage();
						}
						else if(object.getClass().equals(new Vcalendar().getClass())) {
							vcalendar_management.readSerializedVcalendar(fileName);
							HTMLPage = vcalendar_management.convertVcalendarToHTMLPage();
						}
					}
					writer.append(HTMLPage);
					writer.flush();
					writer.close();
				}
				
			} catch (IOException e) {e.printStackTrace();}
				catch (NoArgumentException e) {e.printStackTrace();}
					catch (BadArgumentException e) {e.printStackTrace();}
			
		}
	}

	
	
	class Close_Action implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			interface_Container.removeAll();
			initComponents();
			addComponents();
			
			pack();
		}
	}
	
	//Action qui permet de quitter le logiciel
	class Exit_Action implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}
	
	//Raccourcis à une touche du panel
	class Key_Shortcut implements KeyListener {
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
