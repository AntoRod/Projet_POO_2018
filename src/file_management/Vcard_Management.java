package file_management;

import java.io.*;

import datas.*;

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
	
	
	public Vcard_Management() {
		_vcard = new Vcard();
	}
	
	public Vcard get_vcard() {
		return _vcard;
	}
	
	public void nullifyVcard() {
		_vcard = new Vcard();
	}
	
	//M�thode qui permet de s�rialiser une Vcard
	//essayer avec une Vcard vide ?
	public void serializeVcard(String fileName) {
		if(_vcard == null) {/*UNE ERREUR*/}
		//Sinon on serialise la Vcard
		else {
			//Si le fichier est bien de type ".ser" (fichier s�rialis�)
			//A TRAITER DANS LA PARTIE ARGUMENT PLUT�T ??
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
			else {/*UNE ERREUR*/}
		}
	}
	
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
	
	//M�thode qui permet de convertir les donn�es de la Vcard en un code HTML 5 valide (ne cr�e pas la page, seulement le code de la Vcard)
	public String convertVcardToHTMLCode() {
		
		String 															HTMLCode = "<div class='vcard'>\n";
		if(_vcard.get_firstName() != null && _vcard.get_name() != null)	HTMLCode+= "	<span class='fn'>"						+_vcard.get_firstName()+" "+_vcard.get_name()+"</span>\n";
																		HTMLCode+= "	<span class='n'>\n";
		if(_vcard.get_firstName() != null)								HTMLCode+= "		<span class='given-name'>"			+_vcard.get_firstName()						+"</span>\n";
		if(_vcard.get_name() != null)									HTMLCode+= "		<span class='family-name'>"			+_vcard.get_name()							+"</span>\n";
																		HTMLCode+= "	</span>\n";
																		//AUTRE SPAN COMPANY
		if(_vcard.get_company() != null)								HTMLCode+= "	<span class='org'>"						+_vcard.getClass()							+"</span>\n";
		if(_vcard.get_workPhone() != null)								HTMLCode+= "	<span class=''";
																		HTMLCode+= "</div>";
		System.out.println(HTMLCode);
		
		return HTMLCode;
		
	}
	/*
	 Version:
	Name: OK
	First Name: OK
	Home Phone: OK
	Mail: 
	Home: 
	 - Label
	Company: OK
	Work Phone:
	Work Adress:
	 - Label:
	Title:
	Picture:
	 */
	
	
	//M�thode qui permet d'analyser un fichier pour le convertir en Vcard
	public void analyzeFile(String fileName) {
		analyzeFile(new File(fileName));
	}
	public void analyzeFile(File file) {
		//On importe le fichier dans un Reader et on traite l'exception si il n'existe pas
		try {
			if(!file.exists()) throw new FileNotFoundException("Can't analyse "+file+" cause the file doesn't exists.");
			BufferedReader cardReader = new BufferedReader(new FileReader(file));
			//On lit le fichier ligne par ligne pour traiter chaque informations
			String cardLine = cardReader.readLine();
			while(cardLine != null) {
				//On extrait la version de la ligne
				if(cardLine.startsWith("VERSION")) extractVersion(cardLine);
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
				//On extrait les labels des lignes
				if(cardLine.startsWith("LABEL")) {
					if(cardLine.contains("WORK")) extractLabel(cardLine, "WORK");
					if(cardLine.contains("HOME")) extractLabel(cardLine, "HOME");
				}
				//On extrait le mail de la ligne
				if(cardLine.startsWith("EMAIL")) extractMail(cardLine);
				//On extrait le REV de la ligne
				if(cardLine.startsWith("REV")) extractLastModified(cardLine);
				
				cardLine = cardReader.readLine();
				
			}
			cardReader.close();
		} catch (FileNotFoundException e) {System.out.println(e.getMessage());}
		catch (IOException e) {e.printStackTrace();}
		
	}
	//M�thode permettant d'extraire la version du fichier
	private void extractVersion(String string) {
		//On enl�ve "VERSION:" de la ligne
		string = string.replace("VERSION:", "");
		//On set la version
		_vcard.set_version(string);
//		System.out.println(string);
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
		//On ne se sert pas du trois�me et du quatri�me �l�ment
	}
	//M�thode qui compare le nom et le pr�nom avec le nom entier (juste au cas ou)
	private void compareNames(String string) {
		//On enl�ve "FN:" de la ligne
		string = string.replace("FN:", "");
		//On s�pare les informations � l'aide de la m�thode split
		String[] split = string.split(" ");
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
		//On enl�ve "FN:" de la ligne
		string = string.replace("TEL:", "");
		//On s�pare les informations � l'aide de la m�thode split
		String[] split = string.split(":");
		if(phone == "WORK") _vcard.set_workPhone(split[1]);
		if(phone == "HOME") _vcard.set_homePhone(split[1]);
//		System.out.println(split[1]);
	}
	//M�thode permettant d'extraire les adresses de la personne
	private void extractAdress(String string, String type) {
		//On enl�ve "FN:" de la ligne
		string = string.replace("TEL:", "");
		//On s�pare les informations � l'aide de la m�thode split
		String[] split = string.split(";");
		//On sait que l'adresse commence � partir de l'�l�ment n-5 du tableau (n �tant la fin)
		String adress = "";
		for(int i=split.length-5;i<split.length;i++) {
			adress+=split[i]+",";
		}
		int fin = adress.lastIndexOf(",");
		adress = adress.substring(0, fin);
		if(type == "WORK") _vcard.set_workAdress(adress);
		if(type == "HOME") _vcard.set_homeAdress(adress);
//		System.out.println(adress);
	}
	//M�thode permettant d'extraire les labels des adresses
	private void extractLabel(String string, String type) {
		//On s�pare les informations � l'aide de la m�thode split
		//La ligne ne contenant qu'un seul ":", juste avant le label, on peut les s�parer par rapport � �a pour l'obtenir
		String[] split = string.split(":");
		String label = split[1];
		//On modifie l�g�rement le label pour le rendre plus facile � lire
		label = label.replace("\\n", ",");
		label = label.replace("\\", "");
		
		if(type =="WORK") _vcard.set_labelWorkAdress(label);
		if(type =="HOME") _vcard.set_labelHomeAdress(label);
//		System.out.println(label);
	}
	//M�thode permettant d'extraire l'�mail de la personne
	private void extractMail(String string) {
		//On enl�ve "EMAIL:" de la ligne
		string = string.replace("EMAIL:", "");
		_vcard.set_mail(string);
//		System.out.println(string);		
	}
	//M�thode qui permet d'extraire la date de derni�re modification de la Vcard
	private void extractLastModified(String string) {
		//On enl�ve "REV:" de la ligne
		string = string.replace("REV:", "");
		_vcard.set_mail(string);
//		System.out.println(string);
	}
	
	
	/*FIN AUTRES METHODES*/
}
