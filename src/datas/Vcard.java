package datas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Vcard {
	
	/*	La classe contenant les informations des Vcard à traiter
	 *	Pour la date de dernière modification: on analyse son format pendant la modification de celle-ci (seulement si besoin de modifier la date)
	 *
	 *
	 *	CREATION DE LA VCARD: FONCTIONNEL
	 *	EXTRACTION DES DONNEES DU FICHIER DANS LA VCARD: FONCTIONNEL + EN COURS (le reste des cas possible d'informations)
	 *	TOSTRING MINIMAL NON AUTOMATIQUE: EN COURS
	 *	AMELIORER LE TOSTRING EN FONCTION DES ATTRIBUTS DE MANIERE AUTOMATIQUE: WIP
	 *	SEPARATION DES VCARDS: EN COURS
	 */
												
												//La version de la Vcard
	private String								_version;
												//Le nom de la personne
	private String								_name;
												//le prénom de la personne
	private String								_firstName;
												//Son numéro de téléphone de maison
	private String								_homePhone;
												//Son adresse e-mail
	private String								_mail;
												//Son adresse perso
	private String								_homeAdress;
												//Le label de son adresse perso
	private String								_labelHomeAdress;
												//Le nom de son entreprise (s'il est dans une entreprise)
	private String								_company;
												//Son numéro de téléphone de travail
	private String								_workPhone;
												//Son adresse de travail
	private String								_workAdress;
												//Le label de son adresse de travail
	private String								_labelWorkAdress;
												//Son titre (fonction dans l'entreprise)
	private String								_title;
												//Le lien de sa photo (s'il en possède une)
	private String								_picture;
												//La date de dernière modification de la Vcard
	private String								_lastUpdated;
	
	
	public Vcard() {
		
	}

	/*GETTERS*/
	/**
	 * @return the _name
	 */
	public String get_name() {
		return _name;
	}
	/**
	 * @return the _version
	 */
	public String get_version() {
		return _version;
	}

	/**
	 * @return the _firstName
	 */
	public String get_firstName() {
		return _firstName;
	}


	/**
	 * @return the _homePhone
	 */
	public String get_homePhone() {
		return _homePhone;
	}


	/**
	 * @return the _workPhone
	 */
	public String get_workPhone() {
		return _workPhone;
	}


	/**
	 * @return the _title
	 */
	public String get_title() {
		return _title;
	}


	/**
	 * @return the _picture
	 */
	public String get_picture() {
		return _picture;
	}


	/**
	 * @return the _company
	 */
	public String get_company() {
		return _company;
	}


	/**
	 * @return the _workAdress
	 */
	public String get_workAdress() {
		return _workAdress;
	}


	/**
	 * @return the _homeAdress
	 */
	public String get_homeAdress() {
		return _homeAdress;
	}


	/**
	 * @return the _labelWorkAdress
	 */
	public String get_labelWorkAdress() {
		return _labelWorkAdress;
	}


	/**
	 * @return the _labelHomeAdress
	 */
	public String get_labelHomeAdress() {
		return _labelHomeAdress;
	}


	/**
	 * @return the _mail
	 */
	public String get_mail() {
		return _mail;
	}


	/**
	 * @return the _lastUpdated
	 */
	public String get_lastUpdated() {
		return _lastUpdated;
	}
	/*FIN GETTERS*/
	/*SETTERS*/
	/**
	 * @param _version the _version to set
	 */
	public void set_version(String _version) {
		this._version = _version;
	}
	/**
	 * @param _name the _name to set
	 */
	public void set_name(String _name) {
		this._name = _name;
	}


	/**
	 * @param _firstName the _firstName to set
	 */
	public void set_firstName(String _firstName) {
		this._firstName = _firstName;
	}


	/**
	 * @param _homePhone the _homePhone to set
	 */
	public void set_homePhone(String _homePhone) {
		this._homePhone = _homePhone;
	}


	/**
	 * @param _workPhone the _workPhone to set
	 */
	public void set_workPhone(String _workPhone) {
		this._workPhone = _workPhone;
	}


	/**
	 * @param _title the _title to set
	 */
	public void set_title(String _title) {
		this._title = _title;
	}


	/**
	 * @param _picture the _picture to set
	 */
	public void set_picture(String _picture) {
		this._picture = _picture;
	}


	/**
	 * @param _company the _company to set
	 */
	public void set_company(String _company) {
		this._company = _company;
	}


	/**
	 * @param _workAdress the _workAdress to set
	 */
	public void set_workAdress(String _workAdress) {
		this._workAdress = _workAdress;
	}


	/**
	 * @param _homeAdress the _homeAdress to set
	 */
	public void set_homeAdress(String _homeAdress) {
		this._homeAdress = _homeAdress;
	}


	/**
	 * @param _labelWorkAdress the _labelWorkAdress to set
	 */
	public void set_labelWorkAdress(String _labelWorkAdress) {
		this._labelWorkAdress = _labelWorkAdress;
	}


	/**
	 * @param _labelHomeAdress the _labelHomeAdress to set
	 */
	public void set_labelHomeAdress(String _labelHomeAdress) {
		this._labelHomeAdress = _labelHomeAdress;
	}


	/**
	 * @param _mail the _mail to set
	 */
	public void set_mail(String _mail) {
		this._mail = _mail;
	}


	/**
	 * @param _lastUpdated the _lastUpdated to set
	 */
	public void set_lastUpdated(String _lastUpdated) {
		this._lastUpdated = _lastUpdated;
	}
	/*FIN SETTERS*/

	/*TOSTRING*/
	public String toString() {
		
		String string ="";
		if(_version != null) 					string+="Version:		"+get_version()+"\n";
		if(_name != null) 						string+="Name:			"+get_name()+"\n";
		if(_firstName != null)					string+="First Name:		"+get_firstName()+"\n";
		if(_homePhone != null)					string+="Home Phone:		"+get_homePhone()+"\n";
		if(_mail != null)						string+="Mail:			"+get_mail()+"\n";
		if(_homeAdress != null)					string+="Home:			"+get_homeAdress()+"\n";
		if(_labelHomeAdress != null)			string+=" - Label		"+get_labelHomeAdress()+"\n";
		if(_company != null)					string+="Company:		"+get_company()+"\n";
		if(_workPhone != null)					string+="Work Phone:		"+get_workPhone()+"\n";
		if(_workAdress != null)					string+="Work Adress:		"+get_workAdress()+"\n";
		if(_labelWorkAdress != null)			string+=" - Label:		"+get_labelWorkAdress()+"\n";
		if(_title != null)						string+="Title:			"+get_title()+"\n";
		if(_picture != null)					string+="Picture:		"+get_picture()+"\n";
		if(_lastUpdated != null)				string+="Last Modified:	"+get_lastUpdated()+"\n";
		
		return string;		
	}
	/*FIN TOSTRING*/
	
	/*AUTRES METHODES*/
	//Méthode qui permet d'analyser un fichier pour le convertir en Vcard
	public void analyzeFile(String fileName) {
		analyzeFile(new File(fileName));
	}
	public void analyzeFile(File file) {
		//On importe le fichier dans un Reader et on traite l'exception si il n'existe pas
		try {
			BufferedReader cardReader = new BufferedReader(new FileReader(file));
			//On lit le fichier ligne par ligne pour traiter chaque informations
			String cardLine = cardReader.readLine();
			while(cardLine != null) {
				//On extrait la version de la ligne
				if(cardLine.startsWith("VERSION")) extractVersion(cardLine);
				//On extrait le nom et le prénom de la ligne
				if(cardLine.startsWith("N")) extractName(cardLine);
				//On compare le nom et le prénom avec le nom entier pour vérifier que celui-ci soit le bon
				if(cardLine.startsWith("FN")) compareNames(cardLine);
				//On extrait l'entreprise de la ligne
				if(cardLine.startsWith("ORG")) extractCompany(cardLine);
				//On extrait le titre de la ligne
				if(cardLine.startsWith("TITLE")) extractTitle(cardLine);
				//On extrait le lien de la photo de la personne
				if(cardLine.startsWith("PHOTO")) extractPicture(cardLine);
				//On extrait les numéros de téléphone des lignes
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
		} catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		
	}
	//Méthode permettant d'extraire la version du fichier
	private void extractVersion(String string) {
		//On enlève "VERSION:" de la ligne
		string = string.replace("VERSION:", "");
		//On set la version
		set_version(string);
//		System.out.println(string);
	}
	//Méthode permettant d'extraire le nom et le prénom du fichier
	private void extractName(String string) {
		//On enlève "N:" de la ligne
		string = string.replace("N:", "");
		//On sépare les informations à l'aide de la méthode split
		String[] split = string.split(";");
		
		//Le premier élément du tableau est le nom de famille
		set_name(split[0]);
		//Le second élément du tableau est le prénom
		set_firstName(split[1]);
		//On ne se sert pas du troisème et du quatrième élément
	}
	//Méthode qui compare le nom et le prénom avec le nom entier (juste au cas ou)
	private void compareNames(String string) {
		//On enlève "FN:" de la ligne
		string = string.replace("FN:", "");
		//On sépare les informations à l'aide de la méthode split
		String[] split = string.split(" ");
		//Si les noms ne correspondent pas, on les remplace
		if(!(split[0] == get_firstName()) || !(split[1] == get_name())) {
			set_name(split[1]);
			set_firstName(split[0]);
		}
	}
	//Méthode permettant d'extraire l'entreprise dans laquelle la personne travaille
	private void extractCompany(String string) {
		//On enlève "FN:" de la ligne
		string = string.replace("ORG:", "");
		//Suivant la version, on doit enlever le ";" de fin
		if(string.endsWith(";")) string = string.substring(0, string.length()-1);
		set_company(string);
//		System.out.println(string);
	}
	//Méthode permettant d'exrtraire le statut que la personne possède dans l'entreprise
	private void extractTitle(String string) {
		//On enlève "TITLE:" de la ligne
		string = string.replace("TITLE:", "");
		set_title(string);
//		System.out.println(string);
	}
	//Méthode permettant d'extraire le lien de la photo de la personne
	private void extractPicture(String string) {
		//On enlève "PHOTO:" de la ligne
		string = string.replace("PHOTO:", "");
		//On repère le début de l'URL
		int debut = string.indexOf("h");
		string = string.substring(debut, string.length());
		set_picture(string);
//		System.out.println(string);
	}
	//Méthode permettant d'extraire les numéros de téléphone de la personne
	private void extractPhone(String string, String phone) {
		//On enlève "FN:" de la ligne
		string = string.replace("TEL:", "");
		//On sépare les informations à l'aide de la méthode split
		String[] split = string.split(":");
		if(phone == "WORK") set_workPhone(split[1]);
		if(phone == "HOME") set_homePhone(split[1]);
//		System.out.println(split[1]);
	}
	//Méthode permettant d'extraire les adresses de la personne
	private void extractAdress(String string, String type) {
		//On enlève "FN:" de la ligne
		string = string.replace("TEL:", "");
		//On sépare les informations à l'aide de la méthode split
		String[] split = string.split(";");
		//On sait que l'adresse commence à partir de l'élément n-5 du tableau (n étant la fin)
		String adress = "";
		for(int i=split.length-5;i<split.length;i++) {
			adress+=split[i]+",";
		}
		int fin = adress.lastIndexOf(",");
		adress = adress.substring(0, fin);
		if(type == "WORK") set_homeAdress(adress);
		if(type == "HOME") set_workAdress(adress);
//		System.out.println(adress);
	}
	//Méthode permettant d'extraire les labels des adresses
	private void extractLabel(String string, String type) {
		//On sépare les informations à l'aide de la méthode split
		//La ligne ne contenant qu'un seul ":", juste avant le label, on peut les séparer par rapport à ça pour l'obtenir
		String[] split = string.split(":");
		String label = split[1];
		//On modifie légèrement le label pour le rendre plus facile à lire
		label = label.replace("\\n", ",");
		label = label.replace("\\", "");
		
		if(type =="WORK") set_labelWorkAdress(label);
		if(type =="HOME") set_labelHomeAdress(label);
//		System.out.println(label);
	}
	//Méthode permettant d'extraire l'émail de la personne
	private void extractMail(String string) {
		//On enlève "EMAIL:" de la ligne
		string = string.replace("EMAIL:", "");
		set_mail(string);
//		System.out.println(string);		
	}
	//Méthode qui permet d'extraire la date de dernière modification de la Vcard
	private void extractLastModified(String string) {
		//On enlève "REV:" de la ligne
		string = string.replace("REV:", "");
		set_mail(string);
//		System.out.println(string);
	}
	
	/*FIN AUTRES METHODES*/
	
	

}
