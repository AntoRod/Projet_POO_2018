package file_management;

import java.io.*;
import datas.*;
import exceptions.*;

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
										//La Vcard à traiter
	private Vcard						_vcard;
										//Si la carte contient une information relative au nom (prénom, nom, 
	private Boolean						_hasNameInfo = false;
										//Si la carte contient une information relative à l'adresse (ville, rue, numéro etc...)
	private Boolean						_hasAdressInfo = false;
										//Si la carte contient une information relative à l'adresse de son travail (ville, rue, numéro etc...)
	private Boolean						_hasCompanyAdressInfo = false;
	
	public Vcard_Management(String fileName) {
		_vcard = new Vcard();
		analyzeFile(fileName);
	}
	
	public Vcard get_vcard() {
		return _vcard;
	}
	
	public void nullifyVcard() {
		_vcard = new Vcard();
	}
	
	//Méthode qui permet de sérialiser une Vcard
	//essayer avec une Vcard vide ?
	public void serializeVcard(String fileName) throws NoArgumentException, BadArgumentException{
		if(_vcard == null) {throw new NoArgumentException("Vcard is empty, can't serialize nothing.");}
		//Sinon on serialise la Vcard
		else {
			//Si le fichier est bien de type ".ser" (fichier sérialisé)
			if(fileName.endsWith(".ser")) {
				try {
					//On crée notre OutputSteam
					ObjectOutputStream vcardOutput = new ObjectOutputStream(new FileOutputStream(fileName));
					//On écrit la Vcard avec la méthode writeObject
					vcardOutput.writeObject(_vcard);
					//On oublie pas de fermer le stream
					vcardOutput.flush();
					vcardOutput.close();
				} catch (IOException e) {e.printStackTrace();}
			}
			//On lance l'exception BadArgument
			else {
				//On repère l'exception pour l'afficher dans le message d'erreur
				String[] split = fileName.split("\\.");
				String extension = split[split.length-1];
				throw new BadArgumentException("Can't serialize Vcard in a ."+extension+" File.\n");}
		}
	}
	
	//Méthode qui permet de déserialiser une Vcard
	public void readSerializedVcard(String fileName) {
		try {
			//On crée notre inputStream
			ObjectInputStream inputVcard = new ObjectInputStream(new FileInputStream(fileName));
			//On remet la Vcard à jour avec les nouvelles informations
			_vcard = (Vcard) inputVcard.readObject();
			//On oublie pas de fermer le stream
			inputVcard.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();} 
				catch (ClassNotFoundException e) {e.printStackTrace();}
	}
	
	//Méthode qui permet de convertir les données de la Vcard en un code HTML 5 valide (ne crée pas la page, seulement le code de la Vcard)
	public String convertVcardToHTMLCode() {
		
		//On crée le string puis on ajoute les éléments 1 par 1, suivant si ils existent ou non
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
		//On retourne le string généré
		return HTMLCode;
	}
	//Methode qui permet de convertir les données les données de la Vcard en une page HTML 5 valide (ne crée pas la page, seulement le code de la plage complète)
	public String convertVcardToHTMLPage() {
		String 								HTMLPage = "";
											HTMLPage+= "<!DOCTYPE HTML>"						+"\n"
													+  "<html lang='fr'>"						+"\n"
													+  "	<head>"								+"\n"
													+  "		<title>Vcard générée</title>"	+"\n"
													+  "	</head>"							+"\n"
													+  "	<body>"								+"\n"
													+  convertVcardToHTMLCode()
													+  "	</body>"							+"\n"
													+  "</html>";
		//On retourne la page générée
		return HTMLPage;
	}

	
	//Méthode qui permet d'analyser un fichier pour le convertir en Vcard
	public void analyzeFile(String fileName) {
		analyzeFile(new File(fileName));
	}
	public void analyzeFile(File file) {
		//On importe le fichier dans un Reader et on traite l'exception si il n'existe pas
		try {
			if(!file.exists()) throw new FileNotFoundException("Can't analyse "+file+" cause the file doesn't exists.");
			else {
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
			}

		} catch (FileNotFoundException e) {System.out.println(e.getMessage());}
		catch (IOException e) {e.printStackTrace();}
		
	}
	//Méthode permettant d'extraire la version du fichier
	private void extractVersion(String string) {
		//On enlève "VERSION:" de la ligne
		string = string.replace("VERSION:", "");
		//On set la version
		_vcard.set_version(string);
//		System.out.println(string);
	}
	//Méthode permettant d'extraire le nom et le prénom du fichier
	private void extractName(String string) {
		//On enlève "N:" de la ligne
		string = string.replace("N:", "");
		//On sépare les informations à l'aide de la méthode split
		String[] split = string.split(";");
		//Le premier élément du tableau est le nom de famille
		_vcard.set_name(split[0]);
		//Le second élément du tableau est le prénom
		_vcard.set_firstName(split[1]);
		//On ne se sert pas du troisème et du quatrième élément POUR LE MOMENT (ensuite on prendra le préfix honorifique)
	}
	//Méthode qui compare le nom et le prénom avec le nom entier (juste au cas ou)
	private void compareNames(String string) {
		//On enlève "FN:" de la ligne
		string = string.replace("FN:", "");
		//On sépare les informations à l'aide de la méthode split
		String[] split = string.split(" ");
		//On considère à partir d'ici que la personne possède une information relative au nom
		if(!_hasNameInfo) _hasNameInfo = true;
		//Si les noms ne correspondent pas, on les remplace
		if(!(split[0] == _vcard.get_firstName()) || !(split[1] == _vcard.get_name())) {
			_vcard.set_name(split[1]);
			_vcard.set_firstName(split[0]);
		}
	}
	//Méthode permettant d'extraire l'entreprise dans laquelle la personne travaille
	private void extractCompany(String string) {
		//On enlève "FN:" de la ligne
		string = string.replace("ORG:", "");
		//Suivant la version, on doit enlever le ";" de fin
		if(string.endsWith(";")) string = string.substring(0, string.length()-1);
		_vcard.set_company(string);
//		System.out.println(string);
	}
	//Méthode permettant d'exrtraire le statut que la personne possède dans l'entreprise
	private void extractTitle(String string) {
		//On enlève "TITLE:" de la ligne
		string = string.replace("TITLE:", "");
		_vcard.set_title(string);
//		System.out.println(string);
	}
	//Méthode permettant d'extraire le lien de la photo de la personne
	private void extractPicture(String string) {
		//On enlève "PHOTO:" de la ligne
		string = string.replace("PHOTO:", "");
		//On repère le début de l'URL
		int debut = string.indexOf("h");
		string = string.substring(debut, string.length());
		_vcard.set_picture(string);
//		System.out.println(string);
	}
	//Méthode permettant d'extraire les numéros de téléphone de la personne
	private void extractPhone(String string, String phone) {
		//On enlève "TEL:" de la ligne
		string = string.replace("TEL:", "");
		//On sépare les informations à l'aide de la méthode split
		String[] split = string.split(":");
		if(phone == "WORK") _vcard.set_workPhone(split[1]);
		if(phone == "HOME") _vcard.set_homePhone(split[1]);
//		System.out.println(split[1]);
	}
	//Méthode permettant d'extraire les adresses de la personne
	private void extractAdress(String string, String type) {
		//On sépare les informations à l'aide de la méthode split
		String[] split = string.split(";");
		//On sait que l'adresse commence à partir de l'élément n-5 du tableau (n étant la fin)
		String adress = "";
		for(int i=split.length-5;i<split.length;i++) {
			adress+=split[i]+",";
		}
		int fin = adress.lastIndexOf(",");
		adress = adress.substring(0, fin);
		if(type == "WORK") {
			//On considère à partir d'ici que la personne possède une information relative à l'adresse de son entreprise
			if(!_hasCompanyAdressInfo) _hasCompanyAdressInfo = true;
			_vcard.set_workAdress(adress);
		}
		if(type == "HOME") {
			//On considère à partir d'ici que la personne possède une information relative à son adresse personnelle
			if(!_hasAdressInfo) _hasAdressInfo = true;
			_vcard.set_homeAdress(adress);
		}
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
		
		if(type =="WORK") _vcard.set_labelWorkAdress(label);
		if(type =="HOME") _vcard.set_labelHomeAdress(label);
//		System.out.println(label);
	}
	//Méthode permettant d'extraire l'émail de la personne
	private void extractMail(String string) {
		//On enlève "EMAIL:" de la ligne
		string = string.replace("EMAIL:", "");
		_vcard.set_mail(string);
//		System.out.println(string);		
	}
	//Méthode qui permet d'extraire la date de dernière modification de la Vcard
	private void extractLastModified(String string) {
		//On enlève "REV:" de la ligne
		string = string.replace("REV:", "");
		_vcard.set_lastUpdated(string);
//		System.out.println(string);
	}
	
	//Methode qui permet de prendre l'adresse complète et de la split par rapport aux virgules (le séparateur utilisé dans l'attribut de la Vcard)
	private String convertAdressToHTML(String adress) {
		//On split l'adresse
		String[] split = adress.split(",");
		//On crée la variable qui contiendra tout le code HTML
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
