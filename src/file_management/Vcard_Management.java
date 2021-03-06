package file_management;

import java.io.*;
import datas.*;
import exceptions.*;
import gui.Settings;

public class Vcard_Management {

	/*	CETTE CLASS CONTIENT LES METHODES RELATIVE A LA GESTION DES VCARDS
	 * 		- CREER UNE VCARD A PARTIR D'UN FICHIER VCF (version 3.0)
	 * 		- SERIALISER UNE VCARD
	 * 		- DESERIALISR UNE VCARD (pour l'interface graphique)
	 * 		- EXPORTER UNE VCARD:
	 * 			- EN FORMAT HTML
	 * 			- EN FORMAT SER (serialiser une Vcard)
	 * 			- EN FORMAT autre ??
	 */
										//La Vcard � traiter
	private Vcard						_vcard;
										//Si la carte contient une information relative au nom (pr�nom, nom, 
	private Boolean						_hasNameInfo = false;
										//Si la carte contient une information relative � l'adresse (ville, rue, num�ro etc...)
	private Boolean						_hasAdressInfo = false;
										//Si la carte contient une information relative � l'adresse de son travail (ville, rue, num�ro etc...)
	private Boolean						_hasCompanyAdressInfo = false;
	
	
	/**
	 * Initializes a newly created Vcard_Management
	 * @param fileName The name of the file from which we extract datas to convert in Vcard
	 */
	public Vcard_Management(String fileName) {
		//Si on lit un fichier s�rialis�, on le d�s�rialise
		if(fileName.endsWith(".ser")) this.readSerializedVcard(fileName);
		else {
			//Sinon on le traite comme un fichier classique
			_vcard = new Vcard();
			analyzeFile(fileName);
		}
		//SI GUI, OBLIGATOIRE !
		Settings.setVcardNameMap();

	}
	
	/**
	 * 
	 * @return _vcard The Vcard we treat here.
	 */
	public Vcard get_vcard() {
		return _vcard;
	}
	/**
	 * Reset the Vcard to a newly created one	 */
	public void nullifyVcard() {
		_vcard = new Vcard();
	}
	/**
	 * 
	 * @param fileName The file in which we serialize the Vcalendar.
	 * @throws NoArgumentException The exception we throw if the Vcard is empty.
	 * @throws BadArgumentException The exception we throw if the file is nt a .ser file.
	 */
	//M�thode qui permet de s�rialiser une Vcard
	//essayer avec une Vcard vide ?
	public void serializeVcard(String fileName) throws NoArgumentException, BadArgumentException{
		if(_vcard == null) {throw new NoArgumentException("Vcard is empty, can't serialize nothing.");}
		//Sinon on serialise la Vcard
		else {
			//Si le fichier est bien de type ".ser" (fichier s�rialis�)
			if(fileName.endsWith(".ser")) {
				try {
					//On cr�e notre OutputSteam
					ObjectOutputStream vcardOutput = new ObjectOutputStream(new FileOutputStream(fileName));
					//On �crit la Vcard avec la m�thode writeObject
					vcardOutput.writeObject(_vcard);
					//On oublie pas de fermer le stream
					vcardOutput.flush();
					vcardOutput.close();
				} catch (IOException e) {e.printStackTrace();}
			}
			//On lance l'exception BadArgument
			else {
				//On rep�re l'exception pour l'afficher dans le message d'erreur
				String[] split = fileName.split("\\.");
				String extension = split[split.length-1];
				throw new BadArgumentException("Can't serialize Vcard in a ."+extension+" File.\n");}
		}
	}
	/**
	 * 
	 * @param fileName The name of the file from which we take the serialized Vcard.
	 */
	//M�thode qui permet de d�serialiser une Vcard
	public void readSerializedVcard(String fileName) {
		try {
			//On cr�e notre inputStream
			ObjectInputStream inputVcard = new ObjectInputStream(new FileInputStream(fileName));
			//On remet la Vcard � jour avec les nouvelles informations
			_vcard = (Vcard) inputVcard.readObject();
			//On oublie pas de fermer le stream
			inputVcard.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();} 
				catch (ClassNotFoundException e) {e.printStackTrace();}
	}
	/**
	 * 
	 * @return Return a valide HTML 5 Code of the Vcard datas (only the data, not the page core)
	 */
	//M�thode qui permet de convertir les donn�es de la Vcard en un code HTML 5 valide (ne cr�e pas la page, seulement le code de la Vcard)
	public String convertVcardToHTMLCode() {
		
		//On cr�e le string puis on ajoute les �l�ments 1 par 1, suivant si ils existent ou non
		String 								HTMLCode = "";
											HTMLCode += "<div class='vcard'>"																+"\n";
		if(_hasNameInfo)					HTMLCode += "	<p class='fn'>"				+_vcard.get_firstName()+" "+_vcard.get_name()		+"</p>\n";

		if(_vcard.get_homePhone() != null)	HTMLCode+= "	<p class='tel'>"			+_vcard.get_homePhone()								+"</p>\n";
		if(_vcard.get_mail() != null)		HTMLCode+= "	<p class='email'>"			+_vcard.get_mail()									+"</p>\n";
		if(_hasAdressInfo) {
											HTMLCode+= "	<ul class='adr'>"																+"\n";
											HTMLCode+= "		<li class='type'>"		+"home"												+"</li>\n";
											HTMLCode+=			convertAdressToHTML(_vcard.get_homeAdress());
											
											HTMLCode+= "	</ul>"																			+"\n";
		}
		if(_vcard.get_company() != null)	HTMLCode+= "	<p class='org'>"			+_vcard.get_company()								+"</p>\n";
		if(_vcard.get_title() != null)		HTMLCode+= "	<p class='title'>"			+_vcard.get_title()									+"</p>\n";
		if(_vcard.get_workPhone() != null)	HTMLCode+= "	<p class='tel'>"			+_vcard.get_workPhone()								+"</p>\n";
		
		if(_hasCompanyAdressInfo) {
											HTMLCode+= "	<ul class='adr'>"																+"\n";
											HTMLCode+= "		<li class='type'>"		+"work"												+"</li>\n";
											HTMLCode+=			convertAdressToHTML(_vcard.get_workAdress());
											HTMLCode+= "	</ul>"																			+"\n";
		}
		if(_vcard.get_picture() != null)	HTMLCode+= "	<p class='photo'>"			+_vcard.get_picture()								+"</p>\n";
											HTMLCode+= "</div>\n";
		//On retourne le string g�n�r�
		return HTMLCode;
	}
	/**
	 * 
	 * @return Return a valide HTML 5 Page of the Vcalendar.
	 */
	//Methode qui permet de convertir les donn�es les donn�es de la Vcard en une page HTML 5 valide (ne cr�e pas la page, seulement le code de la plage compl�te)
	public String convertVcardToHTMLPage() {
		String 								HTMLPage = "";
											HTMLPage+= "<!DOCTYPE HTML>"						+"\n"
													+  "<html lang='fr'>"						+"\n"
													+  "	<head>"								+"\n"
													+  "		<title>Vcard g�n�r�e</title>"	+"\n"
													+  "	</head>"							+"\n"
													+  "	<body>"								+"\n"
													+  convertVcardToHTMLCode()
													+  "	</body>"							+"\n"
													+  "</html>";
		//On retourne la page g�n�r�e
		return HTMLPage;
	}

	/**
	 * 
	 * @param fileName The name of the file we want to analyze and take datas from, to convert into Vcalendar.
	 */
	//M�thode qui permet d'analyser un fichier pour le convertir en Vcard
	public void analyzeFile(String fileName) {
		analyzeFile(new File(fileName));
	}
	/**
	 * 
	 * @param file The file we want to analyze and take datas from, to convert into Vcalendar.
	 */
	public void analyzeFile(File file) {
		//On importe le fichier dans un Reader et on traite l'exception si il n'existe pas
		try {
			if(!file.exists()) throw new FileNotFoundException("Can't analyse "+file+" cause the file doesn't exists.");
			else {
				BufferedReader cardReader = new BufferedReader(new FileReader(file));
				//On lit le fichier ligne par ligne pour traiter chaque informations
				String cardLine = cardReader.readLine();
				while(cardLine != null) {
					//On extrait le nom et le pr�nom de la ligne
					if(cardLine.startsWith("N")) extractName(cardLine);
					//On compare le nom et le pr�nom avec le nom entier pour v�rifier que celui-ci soit le bon
					if(cardLine.startsWith("FN")) compareNames(cardLine);
					//On extrait l'entreprise de la ligne
					if(cardLine.startsWith("ORG")) extractCompany(cardLine);
					//On extrait le titre de la ligne
					if(cardLine.startsWith("TITLE")) extractTitle(cardLine);
					//On extrait le lien de la photo de la personne
					if(cardLine.startsWith("PHOTO")) extractPicture(cardLine);
					//On extrait les num�ros de t�l�phone des lignes
					if(cardLine.startsWith("TEL")) {
						if(cardLine.contains("WORK")) extractPhone(cardLine, "WORK");
						if(cardLine.contains("HOME")) extractPhone(cardLine, "HOME");
					}
					//On extrait les adresses des lignes
					if(cardLine.startsWith("ADR")) {
						if(cardLine.contains("WORK")) extractAdress(cardLine, "WORK");
						if(cardLine.contains("HOME")) extractAdress(cardLine, "HOME");
					}
					//On extrait le mail de la ligne
					if(cardLine.startsWith("EMAIL")) extractMail(cardLine);
					cardLine = cardReader.readLine();
					
				}
				cardReader.close();
			}

		} catch (FileNotFoundException e) {System.out.println(e.getMessage());}
		catch (IOException e) {e.printStackTrace();}
		
	}
	//M�thode permettant d'extraire le nom et le pr�nom du fichier
	private void extractName(String string) {
		//On enl�ve "N:" de la ligne
		string = string.replace("N:", "");
		//On s�pare les informations � l'aide de la m�thode split
		String[] split = string.split(";");
		//Le premier �l�ment du tableau est le nom de famille
		_vcard.set_name(split[0]);
		//Le second �l�ment du tableau est le pr�nom
		_vcard.set_firstName(split[1]);
		//On ne se sert pas du trois�me et du quatri�me �l�ment POUR LE MOMENT (ensuite on prendra le pr�fix honorifique)
	}
	//M�thode qui compare le nom et le pr�nom avec le nom entier (juste au cas ou)
	private void compareNames(String string) {
		//On enl�ve "FN:" de la ligne
		string = string.replace("FN:", "");
		//On s�pare les informations � l'aide de la m�thode split
		String[] split = string.split(" ");
		//On consid�re � partir d'ici que la personne poss�de une information relative au nom
		if(!_hasNameInfo) _hasNameInfo = true;
		//Si les noms ne correspondent pas, on les remplace
		if(!(split[0] == _vcard.get_firstName()) || !(split[1] == _vcard.get_name())) {
			_vcard.set_name(split[1]);
			_vcard.set_firstName(split[0]);
		}
	}
	//M�thode permettant d'extraire l'entreprise dans laquelle la personne travaille
	private void extractCompany(String string) {
		//On enl�ve "FN:" de la ligne
		string = string.replace("ORG:", "");
		//Suivant la version, on doit enlever le ";" de fin
		if(string.endsWith(";")) string = string.substring(0, string.length()-1);
		_vcard.set_company(string);
//		System.out.println(string);
	}
	//M�thode permettant d'exrtraire le statut que la personne poss�de dans l'entreprise
	private void extractTitle(String string) {
		//On enl�ve "TITLE:" de la ligne
		string = string.replace("TITLE:", "");
		_vcard.set_title(string);
//		System.out.println(string);
	}
	//M�thode permettant d'extraire le lien de la photo de la personne
	private void extractPicture(String string) {
		//On enl�ve "PHOTO:" de la ligne
		string = string.replace("PHOTO:", "");
		//On rep�re le d�but de l'URL
		int debut = string.indexOf("h");
		string = string.substring(debut, string.length());
		_vcard.set_picture(string);
//		System.out.println(string);
	}
	//M�thode permettant d'extraire les num�ros de t�l�phone de la personne
	private void extractPhone(String string, String phone) {
		//On enl�ve "TEL:" de la ligne
		string = string.replace("TEL:", "");
		//On s�pare les informations � l'aide de la m�thode split
		String[] split = string.split(":");
		if(phone == "WORK") _vcard.set_workPhone(split[1]);
		if(phone == "HOME") _vcard.set_homePhone(split[1]);
//		System.out.println(split[1]);
	}
	//M�thode permettant d'extraire les adresses de la personne
	private void extractAdress(String string, String type) {
		//On s�pare les informations � l'aide de la m�thode split
		String[] split = string.split(";");
		//On sait que l'adresse commence � partir de l'�l�ment n-5 du tableau (n �tant la fin)
		String adress = "";
		for(int i=split.length-5;i<split.length;i++) {
			adress+=split[i]+",";
		}
		int fin = adress.lastIndexOf(",");
		adress = adress.substring(0, fin);
		if(type == "WORK") {
			//On consid�re � partir d'ici que la personne poss�de une information relative � l'adresse de son entreprise
			if(!_hasCompanyAdressInfo) _hasCompanyAdressInfo = true;
			_vcard.set_workAdress(adress);
		}
		if(type == "HOME") {
			//On consid�re � partir d'ici que la personne poss�de une information relative � son adresse personnelle
			if(!_hasAdressInfo) _hasAdressInfo = true;
			_vcard.set_homeAdress(adress);
		}
//		System.out.println(adress);
	}

	//M�thode permettant d'extraire l'�mail de la personne
	private void extractMail(String string) {
		//On enl�ve "EMAIL:" de la ligne
		string = string.replace("EMAIL:", "");
		_vcard.set_mail(string);
//		System.out.println(string);		
	}

	
	//Methode qui permet de prendre l'adresse compl�te et de la split par rapport aux virgules (le s�parateur utilis� dans l'attribut de la Vcard)
	private String convertAdressToHTML(String adress) {
		//On split l'adresse
		String[] split = adress.split(",");
		//On cr�e la variable qui contiendra tout le code HTML
		String splitted = "";
		splitted+= "		<li class='street-adress'>"		+split[0]		+"</li>\n";
		splitted+= "		<li class='locality'>"			+split[1]		+"</li>\n";
		splitted+= "		<li class='region'>"			+split[2]		+"</li>\n";	
		splitted+= "		<li class='postal-code'>"		+split[3]		+"</li>\n";
		splitted+= "		<li class='country'>"			+split[4]		+"</li>\n";
		return splitted;
	}
	
	/*FIN AUTRES METHODES*/
}
