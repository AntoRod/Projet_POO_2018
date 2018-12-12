package file_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import datas.*;

public class Vcard_Management {

	/*	CETTE CLASS CONTIENT LES METHODES RELATIVE A LA GESTION DES VCARDS
	 * 		- CREER UNE VCAR A PARTIR D'UN FICHIER VCF
	 * 		- SERIALISER UNE VCARD
	 * 		- DESERIALISR UNE VCARD
	 * 		- EXPORTER UNE VCARD:
	 * 			- EN FORMAT HTML
	 * 			- EN FORMAT SER (serialiser une Vcard)
	 * 			- EN FORMAT autre ??
	 */
										//La Vcard à traiter
	private Vcard						_vcard;
	
	
	public Vcard_Management(Vcard vcard) {
		_vcard = vcard;
	}
	
	//Méthode qui permet d'analyser un fichier pour le convertir en Vcard
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
		//On ne se sert pas du troisème et du quatrième élément
	}
	//Méthode qui compare le nom et le prénom avec le nom entier (juste au cas ou)
	private void compareNames(String string) {
		//On enlève "FN:" de la ligne
		string = string.replace("FN:", "");
		//On sépare les informations à l'aide de la méthode split
		String[] split = string.split(" ");
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
		//On enlève "FN:" de la ligne
		string = string.replace("TEL:", "");
		//On sépare les informations à l'aide de la méthode split
		String[] split = string.split(":");
		if(phone == "WORK") _vcard.set_workPhone(split[1]);
		if(phone == "HOME") _vcard.set_homePhone(split[1]);
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
		if(type == "WORK") _vcard.set_workAdress(adress);
		if(type == "HOME") _vcard.set_homeAdress(adress);
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
		_vcard.set_mail(string);
//		System.out.println(string);
	}
	
	
	/*FIN AUTRES METHODES*/
}
