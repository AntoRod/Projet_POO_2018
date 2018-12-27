package file_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import datas.*;
import exceptions.BadArgumentException;
import exceptions.NoArgumentException;
import gui.Settings;

public class Vcalendar_Management {

	/*
	 * CETTE CLASS CONTIENT LES METHODES RELATIVE A LA GESTION DES VCALENDAR - CREER
	 * UNE VCARD A PARTIR D'UN FICHIER ICS (version 2.0) - SERIALISER UN VCALENDAR -
	 * DESERIALISR UN VCALENDAR (pour l'interface graphique) - EXPORTER UN
	 * VCALENDAR: - EN FORMAT HTML - EN FORMAT SER (serialiser un Vcalendar) - EN
	 * FORMAT autre ??
	 */

	// Le vcalendar à traiter
	private Vcalendar _vcalendar;

	/**
	 * Initializes a newly created Vcalendar_Management
	 * @param fileName The name of the file from which we extract datas to convert in Vcalendar
	 */
	public Vcalendar_Management(String fileName) {
		//Si on lit un fichier sérialisé, on le désérialise
		if(fileName.endsWith(".ser")) readSerializedVcalendar(fileName);
		else {
			//Sinon on le traite comme un fichier classique
			_vcalendar = new Vcalendar();
			analyzeFile(fileName);
		}
		Settings.setVcalendarNameMap();
	}
	/**
	 * 
	 * @return _vcalendar The Vcalendar we treat here.
	 */
	public Vcalendar get_vcalendar() {
		return _vcalendar;
	}
	/**
	 * Reset the Vcalendar to a newly created one.
	 */
	public void nullifyVcalendar() {
		_vcalendar = new Vcalendar();
	}

	// Méthode qui permet de sérialiser un Vcalendar
	// essayer avec une Vcalendar vide ?
	/**
	 * 
	 * @param fileName The file in which we serialize the Vcalendar.
	 * @throws NoArgumentException The exception we throw if the Vcalendar is empty.
	 * @throws BadArgumentException The exception we throw if the file is nt a .ser file.
	 */
	public void serializeVcalendar(String fileName) throws NoArgumentException, BadArgumentException{
		if(_vcalendar == null) {throw new NoArgumentException("Vcalendar is empty, can't serialize nothing.");}
		//Sinon on serialise la Vcalendar
		else {
			//Si le fichier est bien de type ".ser" (fichier sérialisé)
			if(fileName.endsWith(".ser")) {
				try {
					//On crée notre OutputSteam
					ObjectOutputStream vcalendarOutput = new ObjectOutputStream(new FileOutputStream(fileName));
					//On écrit la Vcalendar avec la méthode writeObject
					vcalendarOutput.writeObject(_vcalendar);
					//On oublie pas de fermer le stream
					vcalendarOutput.flush();
					vcalendarOutput.close();
				} catch (IOException e) {e.printStackTrace();}
			}
			//On lance l'exception BadArgument
			else {
				//On repère l'exception pour l'afficher dans le message d'erreur
				String[] split = fileName.split("\\.");
				String extension = split[split.length-1];
				throw new BadArgumentException("Can't serialize Vcalendar in a ."+extension+" File.\n");}
		}
	}
	/**
	 * 
	 * @param fileName The name of the file from which we take the serialized Vcalendar.
	 */
	//Méthode qui permet de déserialiser un Vcalendar
	public void readSerializedVcalendar(String fileName) {
		try {
			//On crée notre inputStream
			ObjectInputStream inputVcalendar = new ObjectInputStream(new FileInputStream(fileName));
			//On remet la Vcalendar à jour avec les nouvelles informations
			_vcalendar = (Vcalendar) inputVcalendar.readObject();
			//On oublie pas de fermer le stream
			inputVcalendar.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();} 
				catch (ClassNotFoundException e) {e.printStackTrace();}
	}
	/**
	 * 
	 * @return Return a valide HTML 5 Code of the Vcalendar datas (only the data, not the page core)
	 */
	//Méthode qui permet de convertir les données du Vcalendar en un code HTML 5 valide (ne crée pas la page, seulement le code du Vcalendar)
	public String convertVcalendarToHTMLCode() {
		String 										HTMLCode = "";
													HTMLCode += "<div class='v-event'>"																+"\n";
		if(_vcalendar.get_dateBegin() != null)		HTMLCode += "	<p class='dt-start'>"				+_vcalendar.get_dateBegin()					+"</p>\n";
		if(_vcalendar.get_dateEnd() != null)		HTMLCode += "	<p class='dt-end'>"					+_vcalendar.get_dateEnd()					+"</p>\n";
		if(_vcalendar.get_summary() != null)		HTMLCode += "	<p class='p-summary'>"				+_vcalendar.get_summary()					+"</p>\n";
		if(_vcalendar.get_categories() != null)		HTMLCode += convertCategoriesToHTMLCode();
		if(_vcalendar.get_transparency() != null)	HTMLCode += "	<p class='p-transp'>"				+_vcalendar.get_transparency()				+"</p>\n";
		if(_vcalendar.get_location() != null)		HTMLCode += "	<p class='p-location'>"				+_vcalendar.get_location()					+"</p>\n";
		if(_vcalendar.get_description() != null)	HTMLCode += "	<p class='p-description'>"			+_vcalendar.get_dateBegin()					+"</p>\n";
												HTMLCode += "</div>\n";;
		return HTMLCode;
	}
	/**
	 * 
	 * @return Return a valide HTML 5 Page of the Vcalendar.
	 */
	//Methode qui permet de convertir les données les données du Vcalendar en une page HTML 5 valide (ne crée pas la page, seulement le code de la plage complète)
	public String convertVcalendarToHTMLPage() {
		String 								HTMLPage = "";
											HTMLPage+= "<!DOCTYPE HTML>"							+"\n"
													+  "<html lang='fr'>"							+"\n"
													+  "	<head>"									+"\n"
													+  "		<title>Vcalendar générée</title>"	+"\n"
													+  "	</head>"								+"\n"
													+  "	<body>"									+"\n"
													+  convertVcalendarToHTMLCode()
													+  "	</body>"								+"\n"
													+  "</html>";
		//On retourne la page générée
		return HTMLPage;
	}
	
	//Méthode qui permet de convertir les catégories en code HTML 5
	private String convertCategoriesToHTMLCode() {
		String string = "";
		//On récupère les catégories
		String categories = _vcalendar.get_categories();
		//On les split
		String[] split = categories.split(",");
		//On les ajoute au code HTML
		for(int i=0;i<split.length;i++) {
			string+= "	<p class='category'>"			+split[i]		+"</p>\n";
		}
		//On retourn le code HTML généré
		return string;
	}
	
	/**
	 * 
	 * @param fileName The name of the file we want to analyze and take datas from, to convert into Vcalendar.
	 */
	// Méthode qui permet d'analyser un fichier pour le convertir en Vcalendar
	public void analyzeFile(String fileName) {
		analyzeFile(new File(fileName));
	}
	/**
	 * 
	 * @param file The file we want to analyze and take datas from, to convert into Vcalendar.
	 */
	public void analyzeFile(File file) {
		try {
			// On importe le fichier dans un Reader et on traite l'exception si il n'existe
			// pas
			if (!file.exists())
				throw new FileNotFoundException("Can't analyse " + file + " cause the file doesn't exists.");
			else {
				BufferedReader calendarReader = new BufferedReader(new FileReader(file));
				// On lit le fichier ligne par ligne pour traiter chaque informations
				String calendarLine = calendarReader.readLine();
				while (calendarLine != null) {
					// On extrait la date de début de l'event
					if (calendarLine.startsWith("DTSTART"))
						extractDateBegin(calendarLine);
					// On extrait la date de fin de l'event
					if (calendarLine.startsWith("DTEND"))
						extractDateEnd(calendarLine);
					// On extrait le résumé de l'event
					if (calendarLine.startsWith("SUMMARY"))
						extractSummary(calendarLine);
					// On extrait les catégories de l'event
					if (calendarLine.startsWith("CATEGORIES"))
						extractCategories(calendarLine);
					// On extrait la transparence de l'event
					if (calendarLine.startsWith("TRANSP"))
						extractTransparency(calendarLine);
					// On extrait la localisation de l'event
					if (calendarLine.startsWith("LOCATION"))
						extractLocation(calendarLine);
					// On extrait la description de l'event
					if (calendarLine.startsWith("DESCRIPTION"))
						extractDescription(calendarLine);
					// On passe à la ligne suivante
					calendarLine = calendarReader.readLine();
				}
				calendarReader.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Méthode qui permet d'extraire la date de début de l'event
	private void extractDateBegin(String string) {
		// On enlève "DTSTART:" de la ligne
		string = string.replace("DTSTART:", "");
		// On set la date de début
		_vcalendar.set_dateBegin(string);
	}

	// Méthode qui permet d'extarire la date de fin de l'event
	private void extractDateEnd(String string) {
		// On enlève "DTEND:" de la ligne
		string = string.replace("DTEND:", "");
		// On set la date de fin
		_vcalendar.set_dateEnd(string);
	}

	// Méthode qui permet d'extraire le résumé de l'event
	private void extractSummary(String string) {
		// On enlève "SUMMARY:" de la ligne
		string = string.replace("SUMMARY:", "");
		// On set le summary
		_vcalendar.set_summary(string);
	}

	// Méthode qui permet d'extraire les catégories de l'event
	private void extractCategories(String string) {
		// On enlève "CATEGORIES:" de la ligne
		string = string.replace("CATEGORIES:", "");
		// On set les catégories (on ne sépare pas les catégories en un tableau car on
		// utilisera la ligne plus tard dans le GUI, permet de n'utiliser donc qu'une
		// seule ligne au lieu de X)
		_vcalendar.set_categories(string);
	}

	// Méthode qui permet d'extraire la transparence de l'event
	private void extractTransparency(String string) {
		// On enlève "TRANSP:" de la ligne
		string = string.replace("TRANSP:", "");
		// On set la trasparence
		_vcalendar.set_transparency(string);
	}

	// Méthode qui permet d'extraire la localisation de l'event
	private void extractLocation(String string) {
		// On enlève "LOCATION:" de la ligne
		string = string.replace("LOCATION:", "");
		// On enlève les
		string = string.replace("\\", "");
		// On set la location
		_vcalendar.set_location(string);
	}

	// Méthode qui permet d'extraire la description de l'event
	private void extractDescription(String string) {
		// On enlève "DESCRIPTION:" de la ligne
		string = string.replace("DESCRIPTION:", "");
		// On enlève les \ (mais on garde les \n et autre)
		string = string.replace("\\,", ",");
		// On set la description
		_vcalendar.set_description(string);
	}

}
